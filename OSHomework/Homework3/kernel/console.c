
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			      console.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

/*
	回车键: 把光标移到第一列
	换行键: 把光标前进到下一行
*/


#include "type.h"
#include "const.h"
#include "protect.h"
#include "string.h"
#include "proc.h"
#include "tty.h"
#include "console.h"
#include "global.h"
#include "keyboard.h"
#include "proto.h"


PRIVATE void set_cursor(unsigned int position);
PRIVATE void set_video_start_addr(u32 addr);
PRIVATE void flush(CONSOLE* p_con);
PRIVATE int pop_last_cursor(CONSOLE* p_con);
PRIVATE void push_new_cursor(CONSOLE* p_con, unsigned int new_cursor);
PRIVATE int is_input_empty(CONSOLE* p_con);

PUBLIC int search_status;  // 搜索状态
PRIVATE char search_str[SCREEN_SIZE];  // 用于搜索的字符串
PRIVATE int search_origin_cursor; // 搜索前的光标位置
PRIVATE void init_search(CONSOLE* p_con);
PRIVATE void exit_search(CONSOLE* p_con);
PRIVATE void exec_search(CONSOLE* p_con);
PRIVATE void add_to_search_str(char ch);
PRIVATE void set_color(int begin, int end);
PRIVATE int get_search_str_length();
PRIVATE int is_space(CONSOLE* p_con, int cursor);
PRIVATE void add_to_content(CONSOLE* p_con, unsigned int new_cursor, char ch);
PRIVATE void remove_from_content(CONSOLE* p_con, unsigned int cursor);

/*======================================================================*
			   init_screen
 *======================================================================*/
PUBLIC void init_screen(TTY* p_tty)
{
	int nr_tty = p_tty - tty_table;
	p_tty->p_console = console_table + nr_tty;

	int v_mem_size = V_MEM_SIZE >> 1;	/* 显存总大小 (in WORD) */

	int con_v_mem_size                   = v_mem_size / NR_CONSOLES;
	p_tty->p_console->original_addr      = nr_tty * con_v_mem_size;
	p_tty->p_console->v_mem_limit        = con_v_mem_size;
	p_tty->p_console->current_start_addr = p_tty->p_console->original_addr;

	/* 默认光标位置在最开始处 */
	p_tty->p_console->cursor = p_tty->p_console->original_addr;
	// 初始化之前的光标
	for(int i = 0; i < SCREEN_SIZE; i++){
		p_tty->p_console->pre_cursor[i] = -1;
	}
	// 初始化控制台的内容
	for (int i = 0; i < SCREEN_SIZE; i++){
		p_tty->p_console->content[i] == '\0';
	}

	// 搜索状态为0
	search_status = 0;
	// init_search(p_tty->p_console);

	if (nr_tty == 0) {
		/* 第一个控制台沿用原来的光标位置 */
		p_tty->p_console->cursor = disp_pos / 2;
		disp_pos = 0;
	}
	else {
		out_char(p_tty->p_console, nr_tty + '0');
		out_char(p_tty->p_console, '#');
	}

	set_cursor(p_tty->p_console->cursor);
}

/*======================================================================*
			   clear_screen
 *======================================================================*/

PUBLIC void clear_screen(TTY* p_tty) {

	// 如果用户的输入不为空，则清空
	while(!is_input_empty(p_tty->p_console)){
		out_char(p_tty->p_console, '\b');
	}
	// 再将其他置为0
	for (int i = 0; i < p_tty->p_console->cursor; i++){
		*((u8*)(V_MEM_BASE + 2*i)) = ' ';
		*((u8*)(V_MEM_BASE + 2*i+1)) = DEFAULT_CHAR_COLOR;
	}
	// 光标移动到最开始的地方
	p_tty->p_console->cursor = p_tty->p_console->original_addr;
	set_cursor(p_tty->p_console->cursor);
}


/*======================================================================*
			   is_current_console
*======================================================================*/
PUBLIC int is_current_console(CONSOLE* p_con)
{
	return (p_con == &console_table[nr_current_console]);
}


/*======================================================================*
			   out_char
 *======================================================================*/


PUBLIC void out_char(CONSOLE* p_con, char ch)
{
	// 显存指针
	u8* p_vmem = (u8*)(V_MEM_BASE + p_con->cursor * 2);

	/* search_status=0：正常模式，任何的字符都可以输入
	 * search_status=1：查找模式，输入ESC进入，输入查找字符，此时应注意按下ESC直接会调到第0个状态，即输入的字符必须删除  / 此时屏蔽清空
	 * search_status=2：匹配模式，输入ENTER进入，匹配那些一样的字符串，并操作显存改变颜色 / 屏蔽除了ESC的所有输入
	 */


	switch(ch) {

	case '\n':
		if (search_status == 0){
			// 如果当前控制台的光标小于控制台所占显存减去一行
			if (p_con->cursor < p_con->original_addr +
			    p_con->v_mem_limit - SCREEN_WIDTH) {
				// 保存当前光标
				push_new_cursor(p_con, p_con->cursor);
				// 增加控制台内容
				add_to_content(p_con, p_con->cursor, ch);
				// 光标移动到下一行的开头处
				p_con->cursor = p_con->original_addr + SCREEN_WIDTH * 
					((p_con->cursor - p_con->original_addr) /
					 SCREEN_WIDTH + 1);
			}
		}
		else if (search_status == 1){
			// 执行搜索
			exec_search(p_con);
			search_status++;
		} 
		
		break;

	case '\b':
		// 注意在查找模式下不要连原来的字符都被删除了
		if (search_status != 2){
			if ((search_status == 0 && p_con->cursor > p_con->original_addr) || (search_status == 1 && p_con->cursor > search_origin_cursor)) {
				// 取出最后的光标
				unsigned int last_cursor = pop_last_cursor(p_con);
				// 从控制台内容中删除
				remove_from_content(p_con, p_con->cursor);
				// 计算最后两个光标的差值
				int distance = p_con->cursor - last_cursor;
				// 重新定位光标
				p_con->cursor = last_cursor;
				// 将两光标之间的字符初始化
				for(int i = 1; i <= distance; i++){
					*(p_vmem-2*i+1) = DEFAULT_CHAR_COLOR;
					*(p_vmem-2*i) = ' ';
				}
			}
		}
		break;

	case '\t':
		if (search_status == 0){
			// 光标移动到下一个对齐的地方，即加上 4 - 当前位置模4
			// int totab = 4 - (p_con->cursor - p_con->original_addr) % 4;
			if (p_con->cursor < p_con->original_addr + p_con->v_mem_limit - (4 - (p_con->cursor - p_con->original_addr) % 4)){
				// 保存当前光标
				push_new_cursor(p_con, p_con->cursor);
				// 增加控制台内容
				add_to_content(p_con, p_con->cursor, ch);
				// 游标移动到下一个对齐的地方
				p_con->cursor = p_con->cursor + 4 - (p_con->cursor - p_con->original_addr) % 4;
			}
		}
		break;

	case '\e':
		if (search_status == 0){
			// 初始化查找状态
			init_search(p_con);
			search_status++;
		}
		else {
			// 退出查找状态
			search_status = 0;
			exit_search(p_con);
		}
		break;

	default:
		if (search_status != 2) {
			if (p_con->cursor < p_con->original_addr + p_con->v_mem_limit - 1) {
				// 保存当前光标
				push_new_cursor(p_con, p_con->cursor);
				// 增加控制台内容
				add_to_content(p_con, p_con->cursor, ch);
				// 如果在查找模式下，需要将新的字符加到查找字符串里面
				if (search_status == 1)
					add_to_search_str(ch);
				// 显存增加新的字符
				*p_vmem++ = ch;
				*p_vmem++ = (search_status == 0 ? DEFAULT_CHAR_COLOR : SEARCH_COLOR);
				p_con->cursor++;
			}
		}
		
		break;
	}

	while (p_con->cursor >= p_con->current_start_addr + SCREEN_SIZE) {
		scroll_screen(p_con, SCR_DN);
	}

	flush(p_con);
	
	
}

/*======================================================================*
                           flush
*======================================================================*/
PRIVATE void flush(CONSOLE* p_con)
{
        set_cursor(p_con->cursor);
        set_video_start_addr(p_con->current_start_addr);
}

/*======================================================================*
			    set_cursor
 *======================================================================*/
PRIVATE void set_cursor(unsigned int position)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, CURSOR_H);
	out_byte(CRTC_DATA_REG, (position >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, CURSOR_L);
	out_byte(CRTC_DATA_REG, position & 0xFF);
	enable_int();
}

/*======================================================================*
			  set_video_start_addr
 *======================================================================*/
PRIVATE void set_video_start_addr(u32 addr)
{
	disable_int();
	out_byte(CRTC_ADDR_REG, START_ADDR_H);
	out_byte(CRTC_DATA_REG, (addr >> 8) & 0xFF);
	out_byte(CRTC_ADDR_REG, START_ADDR_L);
	out_byte(CRTC_DATA_REG, addr & 0xFF);
	enable_int();
}



/*======================================================================*
			   select_console
 *======================================================================*/
PUBLIC void select_console(int nr_console)	/* 0 ~ (NR_CONSOLES - 1) */
{
	if ((nr_console < 0) || (nr_console >= NR_CONSOLES)) {
		return;
	}

	nr_current_console = nr_console;

	set_cursor(console_table[nr_console].cursor);
	set_video_start_addr(console_table[nr_console].current_start_addr);
}

/*======================================================================*
			   scroll_screen
 *----------------------------------------------------------------------*
 滚屏.
 *----------------------------------------------------------------------*
 direction:
	SCR_UP	: 向上滚屏
	SCR_DN	: 向下滚屏
	其它	: 不做处理
 *======================================================================*/
PUBLIC void scroll_screen(CONSOLE* p_con, int direction)
{
	if (direction == SCR_UP) {
		if (p_con->current_start_addr > p_con->original_addr) {
			p_con->current_start_addr -= SCREEN_WIDTH;
		}
	}
	else if (direction == SCR_DN) {
		if (p_con->current_start_addr + SCREEN_SIZE <
		    p_con->original_addr + p_con->v_mem_limit) {
			p_con->current_start_addr += SCREEN_WIDTH;
		}
	}
	else{
	}

	set_video_start_addr(p_con->current_start_addr);
	set_cursor(p_con->cursor);
}


/*======================================================================*
			  清除相关函数，维护一个位置数组pre_cursor
 *======================================================================*/
PRIVATE int pop_last_cursor(CONSOLE* p_con){
	unsigned int last_cursor = 0;
	for(int i = -1; i < SCREEN_SIZE-1; i++) {
		if (p_con->pre_cursor[i+1] == -1){
			last_cursor = p_con->pre_cursor[i];
			p_con->pre_cursor[i] = -1;
			break;
		}
	}	
	return last_cursor;
}


PRIVATE void push_new_cursor(CONSOLE* p_con, unsigned int new_cursor){
	for(int i = 0; i < SCREEN_SIZE; i++) {
		if (p_con->pre_cursor[i] == -1){
			p_con->pre_cursor[i] = new_cursor;
			break;
		}
	}	
}


PRIVATE void add_to_content(CONSOLE* p_con, unsigned int new_cursor, char ch){
	p_con->content[new_cursor] = ch;
}

PRIVATE void remove_from_content(CONSOLE* p_con, unsigned int cursor){
	p_con->content[cursor] = '\0';
}


/*======================================================================*
			  检查屏幕是否为空
 *======================================================================*/

PRIVATE int is_input_empty(CONSOLE* p_con) {
	if (p_con->pre_cursor[0] == -1)
		return 1;
	else
		return 0;
}



/*======================================================================*
			   搜索模式相关函数
 *======================================================================*/

PRIVATE void init_search(CONSOLE* p_con){
	search_origin_cursor = p_con->cursor;
	for (int i = 0; i < SCREEN_SIZE; ++i){
		search_str[i] = '\0';
	}
}

PRIVATE void exit_search(CONSOLE* p_con){
	// 先删除查找的字符串
	while (p_con->cursor > search_origin_cursor) {
		out_char(p_con, '\b');
	}
	// 再将原先的显存改到原来的颜色
	for (int i = 0; i < p_con->cursor; i++){
		*((u8*)(V_MEM_BASE + 2*i+1)) = DEFAULT_CHAR_COLOR;
	}
	// 重新初始化搜索状态
	search_origin_cursor = 0;
	for (int i = 0; i < SCREEN_SIZE; ++i){
		search_str[i] = '\0';
	}
}

PRIVATE void exec_search(CONSOLE* p_con){
	// 定义一个匹配起始位置
	int match_pos = 0;
	// 获取搜索字符串长度
	int sslen = get_search_str_length();
	// 遍历需要搜索的字符串
	for (int i = 0; i < search_origin_cursor; i++) {
		for (int j = 0; j < sslen; j++) {
			char ch = *((u8*)(V_MEM_BASE + 2*(i+j)));
			// 开始匹配
			if (ch == search_str[j] && i+j < search_origin_cursor){
				if (ch == ' '){
					if (!is_space(p_con, i+j)){
						match_pos = 0;
						break;
					}
				}

				if (j == 0) 
					match_pos = i;
				if (j == sslen - 1) {
					set_color(match_pos, i+j);
					i = i + j;
				}
			} else {
				match_pos = 0;
				break;
			}
		}

	}
}


PRIVATE int is_space(CONSOLE* p_con, int cursor) {
	// // 先从pre_cursor表中查找到当前cursor的位置和下一个位置
	// int next_cursor = 0;
	// for (int i = 0; i < SCREEN_SIZE; ++i){
	// 	if (p_con->pre_cursor[i] == cursor){
	// 		next_cursor = p_con->pre_cursor[i+1];
	// 	}
	// }

	// if (next_cursor == -1 || next_cursor - cursor == 1) 
	// 	return 1;
	// else
	// 	return 0;
	return p_con->content[cursor] == ' ' ? 1 : 0;


}



PRIVATE void set_color(int begin, int end){

	for (int i = begin; i <= end; i++){
		*((u8*)(V_MEM_BASE + 2*i+1)) = FOUND_COLOR;
	}

}

PRIVATE void add_to_search_str(char ch){
	for (int i = 0; i < SCREEN_SIZE; i++){
		if (search_str[i] == '\0'){
			search_str[i] = ch;
			break;
		}
	}
}

PRIVATE int get_search_str_length() {

	int result = 0;

	for (int i = 0; i < SCREEN_SIZE; ++i){
		if (search_str[i] != '\0')
			result++;
		else
			break;
	}

	return result;
}


