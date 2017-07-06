
/*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                              vsprintf.c
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                                    Forrest Yu, 2005
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

#include "type.h"
#include "const.h"
#include "string.h"

/*
 *  为更好地理解此函数的原理，可参考 printf 的注释部分。
 */

/*======================================================================*
                                vsprintf
 *======================================================================*/
void my_itoa(char* buffer, int num);
int vsprintf(char *buf, const char *fmt, va_list args)
{
	char*	p;
	char	tmp[256];
	va_list	p_next_arg = args;

	for (p=buf;*fmt;fmt++) {
		if (*fmt != '%') {
			*p++ = *fmt;
			continue;
		}

		fmt++;

		switch (*fmt) {
		case 'x':
			itoa(tmp, *((int*)p_next_arg));
			strcpy(p, tmp);
			p_next_arg += 4;
			p += strlen(tmp);
			break;
		case 's':
			break;
		case 'd':
			my_itoa(tmp, *((int*)p_next_arg));
			strcpy(p, tmp);
			p_next_arg += 4;
			p += strlen(tmp);
			break;
		default:
			break;
		}
	}

	return (p - buf);
}



void my_itoa(char* buffer, int num){
    // 先计算有多少位
    int count = 0;
    int temp = num;
    while(temp > 0){
        temp /= 10;
        count++;
    }
    temp = num;
    // 转化成字符串
    buffer[count] = '\0';
    for (int i = 0; i < count; ++i) {
        int bit = temp % 10;
        temp /= 10;
        buffer[count-1-i] = (char)(bit + 48);
    }
}
