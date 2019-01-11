#!/bin/sh

OBJS = client.c
TARGET = client
CC = gcc

all: client

client: ${OBJS}
	${CC} -o ${TARGET} ${OBJS}

clean:
	rm -f ${TARGET} core *.o *.core

