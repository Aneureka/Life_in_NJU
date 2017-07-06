
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            main.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

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

/*======================================================================*
                            kernel_main
 *======================================================================*/
PUBLIC int kernel_main()
{
	disp_str("-----\"kernel_main\" begins-----\n");

	TASK*		p_task		= task_table;
	PROCESS*	p_proc		= proc_table;
	char*		p_task_stack	= task_stack + STACK_SIZE_TOTAL;
	u16		selector_ldt	= SELECTOR_LDT_FIRST;
	int i;
        u8              privilege;
        u8              rpl;
        int             eflags;
	for (i = 0; i < NR_TASKS+NR_PROCS; i++) {
                if (i < NR_TASKS) {     /* 任务 */
                        p_task    = task_table + i;
                        privilege = PRIVILEGE_TASK;
                        rpl       = RPL_TASK;
                        eflags    = 0x1202; /* IF=1, IOPL=1, bit 2 is always 1 */
                }
                else {                  /* 用户进程 */
                        p_task    = user_proc_table + (i - NR_TASKS);
                        privilege = PRIVILEGE_USER;
                        rpl       = RPL_USER;
                        eflags    = 0x202; /* IF=1, bit 2 is always 1 */
                }

		strcpy(p_proc->p_name, p_task->name);	// name of the process
		p_proc->pid = i;			// pid

		p_proc->ldt_sel = selector_ldt;

		memcpy(&p_proc->ldts[0], &gdt[SELECTOR_KERNEL_CS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[0].attr1 = DA_C | privilege << 5;
		memcpy(&p_proc->ldts[1], &gdt[SELECTOR_KERNEL_DS >> 3],
		       sizeof(DESCRIPTOR));
		p_proc->ldts[1].attr1 = DA_DRW | privilege << 5;
		p_proc->regs.cs	= (0 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ds	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.es	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.fs	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.ss	= (8 & SA_RPL_MASK & SA_TI_MASK) | SA_TIL | rpl;
		p_proc->regs.gs	= (SELECTOR_KERNEL_GS & SA_RPL_MASK) | rpl;

		p_proc->regs.eip = (u32)p_task->initial_eip;
		p_proc->regs.esp = (u32)p_task_stack;
		p_proc->regs.eflags = eflags;

		p_proc->nr_tty = 0;

		p_task_stack -= p_task->stacksize;
		p_proc++;
		p_task++;
		selector_ldt += 1 << 3;
	}

	proc_table[0].ticks = proc_table[0].priority = 15;
	proc_table[1].ticks = proc_table[1].priority =  5;
	proc_table[2].ticks = proc_table[2].priority =  5;
	proc_table[3].ticks = proc_table[3].priority =  5;
	proc_table[4].ticks = proc_table[4].priority =  5;
	proc_table[5].ticks = proc_table[5].priority =  5;

	for (int i = 0; i < NR_TASKS+NR_PROCS; ++i)
	{
		proc_table[0].sleep = 0;
	}

	// init bnc
	chair = 2;
	waiting = 0;
	barberTime = 30000;
	customer1ComeTime = 20000;
	customer2ComeTime = 40000;
	customer3ComeTime = 50000;	

	// init semaphore
	barber.value = 0;
	customer.value = 0;
	mutex.value = 1;

	// init id
	customerID = 0;
	barberCustomerID = 0;
	waiting = 0;


	// end edit
	k_reenter = 0;
	ticks = 0;

	p_proc_ready	= proc_table;

	init_clock();
    init_keyboard();

	restart();

	while(1){}
}

/*======================================================================*
                               TestA
 *======================================================================*/
void TestA()
{
	int i = 0;
	while (1) {	
		milli_delay(200000);
	}

}

/*======================================================================*
                               Barber
 *======================================================================*/
void Barber()
{
	int i = 1;

	while(1){

	 	sem_p(-1,  &customer);  
	 	sem_p(0 , &mutex);      
	 	waiting--;
	 	
	 	sem_v(&barber) ;

	 	sem_v(&mutex);
	 	cut_hair();

     
}
}

/*======================================================================*
                               Customer1
 *======================================================================*/
void Customer1()
{
	int i = 2;
	int cid;
	while(1){
		milli_delay(customer1ComeTime);  

		sem_p(0 , &mutex);                         
		cid = ++customerID; 
		c_come(cid);            
		
		if(waiting >= chair) {
			c_leave(cid);
			sem_v(&mutex);                   
			continue;
		}else{
			waiting++;
			print_waiting_num(waiting);
			sem_v(&customer);                             
			sem_v(&mutex);
			
			sem_p(cid , &barber);                
			get_haircut(cid);
			
		}
	

	}
}
/*======================================================================*
                               Customer2
 *======================================================================*/
void Customer2()
{	
	int i = 3;
	int cid;
	while(1){
		milli_delay(customer2ComeTime);  

		sem_p(0 , &mutex);                         
		cid = ++customerID; 
		c_come(cid);            
		
		if(waiting >= chair) {
			c_leave(cid);
			sem_v(&mutex);                   
			continue;
		}else{
			waiting++;
			print_waiting_num(waiting);
			sem_v(&customer);                             
			sem_v(&mutex);
			
			sem_p(cid , &barber);                
			get_haircut(cid);
			
		}
	

	}
}
/*======================================================================*
                               Customer3
 *======================================================================*/
void Customer3()
{
	int i = 4;
	int cid;
	while(1){
		milli_delay(customer3ComeTime);  

		sem_p(0, &mutex);                         
		cid = ++customerID; 
		c_come(cid);            
		
		if(waiting >= chair) {
			c_leave(cid);
			sem_v(&mutex);                   
			continue;
		}else{
			waiting++;
			print_waiting_num(waiting);
			sem_v(&customer);                             
			sem_v(&mutex);
			
			sem_p(cid, &barber);                
			get_haircut(cid);
			
		}
	

	}
}

