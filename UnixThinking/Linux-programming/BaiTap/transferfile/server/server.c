#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>
#include <pthread.h>

#define BACKLOG 10
#define LENGTH 512
#define PORT 8888

void* connection_handler(void *);

int main(int argc, char const *argv[]) {

	int port = PORT, opt = 1;
	struct sockaddr_in address;
	int addrlen = sizeof(address);

	int server_socket = socket(AF_INET, SOCK_STREAM, 0);
	if (server_socket < 0) {
		printf("ERROR: server_socket failed.\n");
		exit(1);
	}

	if (setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, &opt,
			sizeof(opt))) {
		printf("ERROR: setsockopt failed.\n");
		exit(1);
	}

	address.sin_family = AF_INET;
	address.sin_addr.s_addr = INADDR_ANY;
	address.sin_port = htons(port);

	if (bind(server_socket, (struct sockaddr *) &address, sizeof(address))
			< 0) {
		printf("ERROR: bind server_socket (Errno: %d).\n", errno);
		exit(1);
	}

	if (listen(server_socket, BACKLOG) < 0) {
		printf("ERROR: server_socket listen failed (Errno: %d).\n", errno);
		close(server_socket);
		exit(1);
	}

	printf("SERVER IS NOW STARTING ON PORT %d...\n\n", port);

	while (1) {
		int new_socket = accept(server_socket, (struct sockaddr*) &address, (socklen_t*) &addrlen);
		if (new_socket < 0) {
			printf("ERROR: accept the client socket (Errno: %d).\n", errno);
			close(new_socket);
			continue;
		}

		struct sockaddr_in* pV4Addr = (struct sockaddr_in*)&address;
		struct in_addr ipAddr = pV4Addr->sin_addr;

		char client_addr[INET_ADDRSTRLEN];
		inet_ntop( AF_INET, &ipAddr, client_addr, INET_ADDRSTRLEN );

		printf("[Request from %s]\n", client_addr);

		pthread_t thread_id;

		if (pthread_create(&thread_id, NULL, &connection_handler, (void*)&new_socket) != 0) {
			perror("Could not create thread.\n");
			break;
		}
	}

	close(server_socket);
	printf("Server stopped.\n");

	return 0;
}

void* connection_handler(void* socket_desc) {

	int new_socket = *(int*)socket_desc;

	int success = 0;
	char file_name[LENGTH];

	while (1) {
		if (success == 1) {
			printf(" - Sent file to client.\n");
			break;
		} else if (success == -1) {
			printf(" - ERROR: sending file to client failed.\n");
			break;
		} else if (success == -2) {
			printf(" - ERROR: file not found.\n");
			break;
		}

		int bytes_read = recv(new_socket, file_name, sizeof(file_name), 0);

		if (bytes_read > 0) {
			printf(" - File request: \"%s\"\n", file_name);

			FILE* fp;
			int file_stat = 0;

			fp = fopen(file_name, "rb");

			if (fp == NULL) {
				file_stat = -1;
				send(new_socket, &file_stat, sizeof(file_stat), 0);
				success = -2;
				continue;
			} else {
				file_stat = 1;
				send(new_socket, &file_stat, sizeof(file_stat), 0);

				char buf[LENGTH];
				int numr, nums = 0;

				bzero(buf, LENGTH);

				while (feof(fp) == 0) {
					numr = fread(buf, 1, LENGTH, fp);
					if (numr != LENGTH) {
						if (ferror(fp) != 0) {
							success = -1;
							break;
						}
					}

					nums = send(new_socket, buf, numr, 0);

					if (nums != numr) {
						success = -1;
						break;
					}

					bzero(buf, LENGTH);
				}

				if (success != -1)
					success = 1;
			}
		} else {
			continue;
		}
	}

	close(new_socket);
	pthread_exit(NULL);
}

