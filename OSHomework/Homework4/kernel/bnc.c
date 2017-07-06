#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "proto.h"
#include "bnc.h"



void cut_hair(){
	printf("start barbering for customer\n");
	milli_delay(barberTime);
    printf("barbering over , customer leaves\n");
}


void get_haircut(int c){

	printf("customer%d get haircut...\n", c);

}


void c_come(int c){

	printf("customer%d come in...\n", c);

}

void c_leave(int c){

	printf("not enough chair for customer to wait , customer%d leave...\n", c);
}

void print_waiting_num(int num){

	printf("there are %d people waiting...\n", num);

}


