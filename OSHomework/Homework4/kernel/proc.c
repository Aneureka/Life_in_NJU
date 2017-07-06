
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                               proc.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "protect.h"
#include "tty.h"
#include "console.h"
#include "string.h"
#include "proc.h"
#include "global.h"
#include "proto.h"

/*======================================================================*
                              schedule
 *======================================================================*/
PUBLIC void schedule()
{
	PROCESS* p;
	int	 greatest_ticks = 0;

	while (!greatest_ticks) {
		for (p = proc_table; p < proc_table+NR_TASKS+NR_PROCS; p++) {
			if (p->ticks > greatest_ticks && p->sleep == 0) {
				greatest_ticks = p->ticks;
				p_proc_ready = p;
			}
		}

		if (!greatest_ticks) {
			for(p=proc_table;p<proc_table+NR_TASKS+NR_PROCS;p++) {
				p->ticks = p->priority;
			}
		}
	}
}

/*======================================================================*
                           sys_get_ticks
 *======================================================================*/
PUBLIC int sys_get_ticks()
{
	return ticks;
}
/*======================================================================*
                           sys_process_sleep

 *======================================================================*/
PUBLIC void sys_process_sleep(int milli_sec, PROCESS * p){
	p->sleep = milli_sec * HZ / 1000;
}

PUBLIC void sys_sem_p(int role, semaphore * s, PROCESS * p){
	s->value=s->value-1;

	if(s->value < 0){ 
		sys_process_sleep(100000, p);
		s->wait[-s->value - 1] = p;
		if(role == -1){
			printf("Barber is going to sleep\n");
		}

		schedule();
	}
}

PUBLIC void sys_sem_v(semaphore * s, PROCESS * p){
	s->value = s->value + 1;
	if(s->value <= 0){ 
		s->wait[0]->sleep = 0;
		for (int i = 0 ; i < -s->value ; i++){
			s->wait[i] = s->wait[i+1];
		}
	}
}

