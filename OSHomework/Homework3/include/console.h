
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			      console.h
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#ifndef _ORANGES_CONSOLE_H_
#define _ORANGES_CONSOLE_H_

#define SCR_UP	1	/* scroll forward */
#define SCR_DN	-1	/* scroll backward */

#define SCREEN_SIZE		(80 * 25)
#define SCREEN_WIDTH		80

#define DEFAULT_CHAR_COLOR	0x07	/* 0000 0111 黑底白字 */
#define SEARCH_COLOR 0x03  /* 按下ESC查找时的颜色：0000 0011 黑底青色 */
#define FOUND_COLOR 0x02   /* 找到的字符串的颜色 */

/* CONSOLE */
typedef struct s_console
{
	unsigned int	current_start_addr;	/* 当前显示到了什么位置	  */
	unsigned int	original_addr;		/* 当前控制台对应显存位置 */
	unsigned int	v_mem_limit;		/* 当前控制台占的显存大小 */
	unsigned int	cursor;			/* 当前光标位置 */
	unsigned int    pre_cursor[SCREEN_SIZE] /* 之前的光标位置 */ 
	char            content[SCREEN_SIZE];  /* 用于存储控制台的字符及其位置，模拟的控制台 */

}CONSOLE;



#endif /* _ORANGES_CONSOLE_H_ */
