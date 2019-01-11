#!/bin/sh

OBJS = server.c
TARGET = server
CC = gcc

all: server

server: ${OBJS}
	${CC} -o ${TARGET} ${OBJS} -pthread

clean:
	rm -f ${TARGET} core *.o *.core

