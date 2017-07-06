
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                               syscall.asm
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
;                                                     Forrest Yu, 2005
; ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

%include "sconst.inc"

INT_VECTOR_SYS_CALL equ 0x90
_NR_get_ticks       equ 0
_NR_disp_str2	    equ 1
_NR_sleep       equ 2
_NR_p           equ 3
_NR_v           equ 4

; 导出符号
global	get_ticks
global	disp_str2
global  process_sleep
global  sem_p
global  sem_v


bits 32
[section .text]

; ====================================================================
;                              get_ticks
; ====================================================================
get_ticks:
	mov	eax, _NR_get_ticks
	int	INT_VECTOR_SYS_CALL
	ret

; ====================================================================================
;                          void write(char* buf, int len);
; ====================================================================================
disp_str2:
        mov     eax, _NR_disp_str2
        mov     ebx, [esp + 4]
        mov     ecx, [esp + 8]
        int     INT_VECTOR_SYS_CALL
        ret

; ====================================================================================
;          void  process_sleep(int unused1 int milli_sec, PROCESS * p)
; ====================================================================================
process_sleep:
		mov     eax, _NR_sleep
        mov     ebx, [esp + 4]
        int     INT_VECTOR_SYS_CALL
        ret
		
; ====================================================================================
;              void  tem_p(int unused,  semaphore * s,  PROCESS * p)
; ====================================================================================
sem_p:
		mov     eax, _NR_p
        mov     ebx, [esp + 4]
        mov     ecx, [esp + 8]
        int     INT_VECTOR_SYS_CALL
        ret
; ====================================================================================
;              void  tem_p(int unused,  semaphore * s,  PROCESS * p)
; ====================================================================================
sem_v:
		mov     eax, _NR_v
        mov     ebx, [esp + 4]
        int     INT_VECTOR_SYS_CALL
        ret