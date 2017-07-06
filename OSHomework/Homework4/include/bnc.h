
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                            barber_and_customers.h
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Hiki, 2017
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

PUBLIC int chair;
PUBLIC int waiting;
PUBLIC semaphore customer, barber, mutex;
PUBLIC int customerID;
PUBLIC int barberCustomerID;

// time
PUBLIC int barberTime;
PUBLIC int customer1ComeTime;
PUBLIC int customer2ComeTime;
PUBLIC int customer3ComeTime;

// 关于理发的方法
PUBLIC void cut_hair();
PUBLIC void get_haircut(int c);
PUBLIC void c_come(int c);
PUBLIC void c_leave(int c);
PUBLIC void print_waiting_num(int num);


