#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <arpa/inet.h>

#define LENGTH 512

int main(int argc, char const *argv[]) {

	if(argc != 3) {
		printf("ERROR: Wrong number of arguments.\n");
		exit(1);
	}

	if(strcmp(argv[1], "get") != 0) {
		printf("ERROR: Method \"%s\" is undenifed. Try with \"get\".\n", argv[1]);
		exit(1);
	}

	char method[10], path[256];

	strcpy(method, argv[1]);
	strcpy(path, argv[2]);

	char* server_addr = strtok(path, ":");
	char* port = strtok(NULL, "/");
	char* file_path = strtok(NULL, "");

	char file_name[256];
	strcpy(file_name, file_path);

	for (int i = 0; i < strlen(file_name); i++) {
		if (file_name[i] == '/') {
			file_name[i] = '-';
		}
	}

	int socketfd = 0;
	struct sockaddr_in address;

	socketfd = socket(AF_INET, SOCK_STREAM, 0);

	if(socketfd < 0) {
		printf("ERROR: create socket failed.\n");
		exit(1);
	}

	memset(&address, 0, sizeof(address));

	address.sin_family = AF_INET;
	address.sin_port = htons((int) strtol(port, NULL, 10));

	printf("Connecting to %s:%s...\n", server_addr, port);

	if (inet_pton(AF_INET, server_addr, &address.sin_addr) < 0) {
		printf("ERROR: address not supported.\n");
		exit(1);
	}

	if (connect(socketfd, (struct sockaddr*)&address, sizeof(address)) < 0) {
		printf("ERROR: connect to server failed.\n");
		close(socketfd);
		exit(1);
	}

	int success = 0;

	send(socketfd, file_path, 256, 0);

	while (success == 0) {
		int file_stat = 0;
		int bytes_read = recv(socketfd, &file_stat, sizeof(file_stat), 0);

		if (bytes_read > 0) {
			if(file_stat == -1) {
				printf("ERROR: File \"%s\" not found.\n", file_path);
				success = 1;
			}
			else if (file_stat == 1) {

				FILE* fp = fopen(file_name, "ab");

				int numr, numw;

				char buf[LENGTH];

				bzero(buf, LENGTH);

				while(1) {
					numr = recv(socketfd, buf, LENGTH, 0);

					if(numr == -1) {
						printf("ERROR: numr == -1\n");
						success = -1;
						break;
					} else if (numr == 0) {
						printf("Downloaded \"%s\"!\n", file_path);
						success = 1;
						break;
					}

					numw = fwrite(buf, 1, numr, fp);

					if(numr != numw) {
						printf("ERROR: numr != numw\n");
						success = -1;
						break;
					}
				};

				if (fp != NULL) {
					fclose(fp);
				}
			}
		} else {
			continue;
		}
	}

	close(socketfd);
	printf("Finished.\n");
	return 0;
}
