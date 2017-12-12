/*
*********************************************************************************************************
*                                               uC/OS-II
*                                         The Real-Time Kernel
*
*                             (c) Copyright 1998-2004, Micrium, Weston, FL
*                                          All Rights Reserved
*
*
*                                            WIN32 Sample Code
*
* File : APP.C
* By   : Eric Shufro
*********************************************************************************************************
*/

#include <includes.h>

/*
*********************************************************************************************************
*                                                CONSTANTS
*********************************************************************************************************
*/

#define  TASK_STK_SIZE    128
#define  TASK_START_PRIO    5

/*
*********************************************************************************************************
*                                                VARIABLES
*********************************************************************************************************
*/

OS_STK AppStartTaskStk[TASK_STK_SIZE];
// edit by Hiki
OS_STK Task1Stk[TASK_STK_SIZE];
OS_STK Task2Stk[TASK_STK_SIZE];
OS_STK Task3Stk[TASK_STK_SIZE];
OS_STK Task4Stk[TASK_STK_SIZE];
OS_STK Task5Stk[TASK_STK_SIZE];

/*
*********************************************************************************************************
*                                            FUNCTION PROTOTYPES
*********************************************************************************************************
*/

static void AppStartTask(void *p_arg);

// edit by Hiki
// These tasks are for test of edf algorithm.
static void task1(void *p_arg);
static void task2(void *p_arg);
static void task3(void *p_arg);
static void task4(void *p_arg);
static void task5(void *p_arg);

// task id and priority 
#define TASK1_ID 1
#define TASK2_ID 2
#define TASK3_ID 3
#define TASK4_ID 4
#define TASK5_ID 5

#define TASK1_PRIORITY 1
#define TASK2_PRIORITY 2
#define TASK3_PRIORITY 3
#define TASK4_PRIORITY 4
#define TASK5_PRIORITY 5


// edit by Hiki
// EDF_DATA --- c_value, p_value, comp_time, ddl, start, end
EDF_TASK_DATA edfConfig[] =
{
	{1,3,1,4,0,0},
	{3,5,3,6,0,0},
	{1,4,1,4,0,0},
	{2,5,2,5,0,0},
	{2,10,2,10,0,0},
};

#if OS_VIEW_MODULE > 0
static  void  AppTerminalRx(INT8U rx_data);
#endif

/*
*********************************************************************************************************
*                                                _tmain()
*
* Description : This is the standard entry point for C++ WIN32 code.  
* Arguments   : none
*********************************************************************************************************
*/

void main(int argc, char *argv[])
{
	INT8U  err;


#if 0
    BSP_IntDisAll();                       /* For an embedded target, disable all interrupts until we are ready to accept them */
#endif

    OSInit();                              /* Initialize "uC/OS-II, The Real-Time Kernel"                                      */

    OSTaskCreateExt(AppStartTask,
                    (void *)0,
                    (OS_STK *)&AppStartTaskStk[TASK_STK_SIZE-1],
                    TASK_START_PRIO,
                    TASK_START_PRIO,
                    (OS_STK *)&AppStartTaskStk[0],
                    TASK_STK_SIZE,
                    (void *)0,
                    OS_TASK_OPT_STK_CHK | OS_TASK_OPT_STK_CLR);

    // edit by Hiki    
    // TASK 1
    OSTaskCreateExt((void(*)(void *)) task1,
					(void*) 0,
					(OS_STK*) &Task1Stk[TASK_STK_SIZE-1],
					(INT8U) TASK1_PRIORITY,
					(INT16U) TASK1_ID,
					(OS_STK*) &Task1Stk[0],
					(INT32U) TASK_STK_SIZE,
					(void*) &edfConfig[0],
					(INT16U) 0);

    // TASK 2
    OSTaskCreateExt((void(*)(void *))task2,
                    (void*) 0,
                    (OS_STK*) &Task2Stk[TASK_STK_SIZE-1],
                    (INT8U) TASK2_PRIORITY,
                    (INT16U) TASK2_ID,
                    (OS_STK*) &Task2Stk[0],
                    (INT32U) TASK_STK_SIZE,
                    (void*) &edfConfig[1],
                    (INT16U) 0);

    // // TASK 3
    // OSTaskCreateExt((void(*)(void *))task3,
    //                 (void*) 0,
    //                 (OS_STK*) &Task3Stk[TASK_STK_SIZE-1],
    //                 (INT8U) TASK3_PRIORITY,
    //                 (INT16U) TASK3_ID,
    //                 (OS_STK*) &Task3Stk[0],
    //                 (INT32U) TASK_STK_SIZE,
    //                 (void*) &edfConfig[2],
    //                 (INT16U) 0);

    // // TASK 4
    // OSTaskCreateExt((void(*)(void *))task4,
    //                 (void*) 0,
    //                 (OS_STK*) &Task4Stk[TASK_STK_SIZE-1],
    //                 (INT8U) TASK4_PRIORITY,
    //                 (INT16U) TASK4_ID,
    //                 (OS_STK*) &Task4Stk[0],
    //                 (INT32U) TASK_STK_SIZE,
    //                 (void*) &edfConfig[3],
    //                 (INT16U) 0);

    // // TASK 5
    // OSTaskCreateExt((void(*)(void *))task5,
    //                 (void*) 0,
    //                 (OS_STK*) &Task5Stk[TASK_STK_SIZE-1],
    //                 (INT8U) TASK5_PRIORITY,
    //                 (INT16U) TASK5_ID,
    //                 (OS_STK*) &Task5Stk[0],
    //                 (INT32U) TASK_STK_SIZE,
    //                 (void*) &edfConfig[4],
    //                 (INT16U) 0);


#if OS_TASK_NAME_SIZE > 11
    OSTaskNameSet(APP_TASK_START_PRIO, (INT8U *)"Start Task", &err);
#endif

#if OS_TASK_NAME_SIZE > 14
    OSTaskNameSet(OS_IDLE_PRIO, (INT8U *)"uC/OS-II Idle", &err);
#endif

#if (OS_TASK_NAME_SIZE > 14) && (OS_TASK_STAT_EN > 0)
    OSTaskNameSet(OS_STAT_PRIO, "uC/OS-II Stat", &err);
#endif

    OSStart();                             /* Start multitasking (i.e. give control to uC/OS-II)                               */
}
/*$PAGE*/
/*
*********************************************************************************************************
*                                          STARTUP TASK
*
* Description : This is an example of a startup task.  As mentioned in the book's text, you MUST
*               initialize the ticker only once multitasking has started.
* Arguments   : p_arg   is the argument passed to 'AppStartTask()' by 'OSTaskCreate()'.
* Notes       : 1) The first line of code is used to prevent a compiler warning because 'p_arg' is not
*                  used.  The compiler should not generate any code for this statement.
*               2) Interrupts are enabled once the task start because the I-bit of the CCR register was
*                  set to 0 by 'OSTaskCreate()'.
*********************************************************************************************************
*/

static void  AppStartTask (void *p_arg)
{
    p_arg = p_arg;

#if 0
    BSP_Init();                                  /* For embedded targets, initialize BSP functions                             */
#endif


#if OS_TASK_STAT_EN > 0
    OSStatInit();                                /* Determine CPU capacity                                                     */
#endif
    
    while (TRUE)                                 /* Task body, always written as an infinite loop.                             */
	{       		
		OS_Printf("Delay 1 second and print\n");  /* your code here. Create more tasks, etc.                                    */
        OSTimeDlyHMSM(0, 0, 1, 0);       
    }
}

// edit by Hiki
static void task1(void *p_arg)
{
    EDF_TASK_DATA* etd = (EDF_TASK_DATA*) OSTCBCur->OSTCBExtPtr;

    while (1) {
        while (etd->rc > 0){
            // do nothing
        }
    }
}

static void task2(void *p_arg)
{
    EDF_TASK_DATA* etd = (EDF_TASK_DATA*) OSTCBCur->OSTCBExtPtr;

    while (1) {
        while (etd->rc > 0){
            // do nothing
        }
    }
}

static void task3(void *p_arg)
{
    EDF_TASK_DATA* etd = (EDF_TASK_DATA*) OSTCBCur->OSTCBExtPtr;

    while (1) {
        while (etd->rc > 0){
            // do nothing
        }
    }
}

static void task4(void *p_arg)
{
    EDF_TASK_DATA* etd = (EDF_TASK_DATA*) OSTCBCur->OSTCBExtPtr;

    while (1) {
        while (etd->rc > 0){
            // do nothing
        }
    }
}

static void task5(void *p_arg)
{
    EDF_TASK_DATA* etd = (EDF_TASK_DATA*) OSTCBCur->OSTCBExtPtr;

    while (1) {
        while (etd->rc > 0){
            // do nothing
        }
    }
}