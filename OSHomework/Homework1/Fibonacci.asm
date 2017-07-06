%include "io.inc"

section .data
msg1: db 'Please enter a series of number: ', 0h
msg2: db 'The fib numbers are below..', 0h
ctrf: db '', 0   ;换行
;定义颜色
color_red: db 1Bh, '[31;1m', 0
color_yellow: db 1Bh, '[33;1m', 0
color_blue: db  1Bh, '[34;1m', 0
color_default:  db  1Bh, '[37;0m', 0
inputs: dw 0   ;输入的数字序列
section .bss
sinput: resb 255
;保存大数
n_2: resw 600
n_1: resw 600
n  : resw 500

section .text
global CMAIN:
CMAIN:

;------------------------------------------------------
; 进行数据输入，将ASCII码转为数字，存放到inputs数字队列中
; edx将存储输入的数字的个数
; 每个数字占4个字节
;------------------------------------------------------

    ; 输出提示语
    mov eax, msg1
    call sprint

    ; 输入数字串，用空格隔开，换行符结束
    mov edx, 255
    mov ecx, sinput
    mov ebx, 0
    mov eax, 3
    int 80h

    ; 将输入的数字串转化为真正的数字
    mov eax, sinput
    mov esi, eax ; 将字符串保存到esi寄存器中
    mov eax, 0 ; 将eax初始化
    mov ecx, 0 ; ecx作为esi寄存器中字符串的地址索引
    mov edx, 0 ; edx用于计算输入的数字有多少个，存放在栈中
    mov edi, 0 
    push edx

multiply_loop:

    ;将ebx各位置为0，并将下一个检验字符传给ebx的低八位bl
    xor ebx, ebx	
    mov bl, [esi+ecx]

    ;若定位到的不是数字，则转到下一层判断
    cmp bl, 48
    jl next_judge

    cmp bl, 57
    jg next_judge
    
    ;核心转换部分
    sub bl, 48
    add eax, ebx
    mov edi, 10
    mul edi

    ;索引+1
    inc ecx

    jmp multiply_loop

next_judge:

    ;结果需要先除以10
    push edx
    xor edx, edx
    mov edi, 10
    div edi
    pop edx
    
    ;存放数据，由于是dword，需要先将edx*4，再还原
    pop edx
    shl edx, 2
    mov [inputs+edx], eax
    shr edx, 2
    push edx
	
    ;edx++		
    pop edx		
    inc ecx	
    inc edx
    push edx

    ;重新初始化eax
    mov eax, 0 

    ;如果这个字符是空格，继续下一个循环，如果是回车符则退出
    cmp bl, 32
    je multiply_loop



;------------------------------------------------------
; 将inputs里面的数字取出，计算其fib()值并输出
; edx存储输入的数字的个数
; 每个数字占4个字节
;------------------------------------------------------


    ;输出问候语
    mov eax, msg2
    call sprint

    ;循环输出各个数的fib值
    ;初始化
    mov ecx, 0
    
output:
    cmp ecx, edx
    je to_quit
    
    ;取出数据，由于是dword，需要先将ecx*4，再还原
    push ecx
    shl ecx, 2
    mov eax, dword [inputs+ecx]
    shr ecx, 2
    pop ecx
    
    ;选择颜色
    call select_color
    
    ;调用fib
    call get_fib2
    
    inc ecx
    jmp output
    
to_quit:
    call print_default
    call quit
    
;-----------------------------------------------------------------------------------------------------------------------------
;-----------------------------------------------------------方法--------------------------------------------------------------
;-----------------------------------------------------------------------------------------------------------------------------
    
;-------------
;获取字符串长度
;-------------
slen:
    push ebx
    mov ebx, eax

nextchar:
    cmp byte [eax], 0
    jz finished
    inc eax
    jmp nextchar

finished:
    sub eax, ebx
    pop ebx
    ret


;--------------------------------
;计算一个输入的数的斐波那契值(大数版本)
;--------------------------------

get_fib2:
	
    push ebx
    push ecx
    push edx
	
    ;如果是0或1,返回1
    cmp eax, 0
    je .is_zero_or_one
    cmp eax, 1
    je .is_zero_or_one

    ;初始化
    call mkmem2
    call mkmem1
    call mkmem
    mov [n_1+596], dword 1
    mov [n_2+596], dword 1
    

.continue:
    call addn2_n1
    ;call printn
    call n_2ton_1
    call nton_2
    dec eax
    cmp eax, 1
    jne .continue

    ;返回结果
    call printn
    pop edx
    pop ecx
    pop ebx
    ret
    
    

.is_zero_or_one:
    push eax
    mov eax, 1
    call iprintLF
    pop eax
    pop edx
    pop ecx
    pop ebx
    mov eax, 1
    ret



;----------
;打印字符串
;----------
sprint:
    ; maybe eax missed?
    push edx
    push ecx
    push ebx
    push eax
    call slen

    mov edx, eax
    pop eax
    
    mov ecx, eax
    mov ebx, 1
    mov eax, 4
    int 80h

    pop ebx
    pop ecx
    pop edx
    ret
    

;--------
;打印数字
;--------
iprint:
    push eax
    push ebx
    push ecx
    push edx
    push esi
    mov ecx, 0

divide_loop:
    inc ecx
    mov edx, 0
    mov esi, 10
    div esi
    add edx, 48
    push edx
    cmp eax, 0
    jnz divide_loop



print_loop:
    dec ecx
    mov eax, esp
    call sprint
    pop eax
    cmp ecx, 0
    jnz print_loop
	
    pop esi
    pop edx
    pop ecx
    pop ebx
    pop eax
    ret


;-------------
;打印数字并换行
;-------------
iprintLF:
    call iprint
    push ebx
    push eax
    mov eax, 0Ah
    push eax
    mov eax, esp
    call sprint
    pop eax
    pop eax
    pop ebx
    ret


;--------
;选择蓝色
;--------
print_blue:
	push eax
	mov eax, color_blue
	call sprint
	pop eax
	ret

;--------
;选择红色
;--------
print_red:
	push eax
	mov eax, color_red
	call sprint
	pop eax
	ret

;--------
;选择黄色
;--------
print_yellow:
	push eax
	mov eax, color_yellow
	call sprint
	pop eax
	ret

;---------
;选择标准色
;---------
print_default:
	push eax
	mov eax, color_default
	call sprint
	pop eax
	ret

;---------
;选择颜色
;---------
select_color:
	cmp ecx, 0
	je .red
	cmp ecx, 1
	je .blue
	cmp ecx, 2
	je .yellow
        cmp ecx, 3
	je .red
	cmp ecx, 4
	je .blue
	cmp ecx, 5
	je .yellow
        cmp ecx, 6
	je .red
	cmp ecx, 7
	je .blue
	cmp ecx, 8
	je .yellow
.red:
	call print_red
	ret

.blue:
	call print_blue
	ret
.yellow:
	call print_yellow
	ret




;-------------
;退出
;-------------
quit:
    mov ebx, 0
    mov eax, 1
    int 80h
    ret    
    
;-----------------------------------------------------------------------------------------
;----------------------------------------大数的方法----------------------------------------
;-----------------------------------------------------------------------------------------

;------------------------------
;将n_2和n_1相加，存到n里面
;------------------------------
addn2_n1:
    push eax
    push ebx
    push ecx
    push edx
   
    ;初始化
    xor ecx, ecx ;保存进位
    mov ecx, 0
    mov edx, 896
    call mkmem

.add:
    xor eax, eax
    xor ebx, ebx
    
    mov eax, [n_2+edx]
    mov ebx, [n_1+edx]
    add eax, ebx
    add eax, ecx
    
    cmp eax, 1000000000
    jg .carry
    
    mov [n+edx], eax
    mov ecx, 0
    jmp .next
    
.carry:
    sub eax, 1000000000
    mov [n+edx], eax
    mov ecx, 1
    
  
.next:
    sub edx, 4
    cmp edx, -4
    je .add_finished
    jmp .add


.add_finished: 
    pop edx
    pop ecx
    pop ebx
    pop eax
    ret


;------------------------------
;将n_2的值复制到n_1
;------------------------------
n_2ton_1:
    push eax
    push ecx
    mov ecx, 0
    call mkmem1
    
.to:
    push ecx
    shl ecx, 2
    mov eax, [n_2+ecx]
    mov [n_1+ecx], eax
    shr ecx, 2
    pop ecx
    
    inc ecx
    cmp ecx, 300
    je .to_finished
    jmp .to

.to_finished:
    pop ecx
    pop eax
    ret
    
    
;------------------------------
;将n的值复制到n_2
;------------------------------
nton_2:
    push eax
    push ecx
    mov ecx, 0
    call mkmem2
    
.to:
    push ecx
    shl ecx, 2
    mov eax, [n+ecx]
    mov [n_2+ecx], eax
    shr ecx, 2
    pop ecx
    
    inc ecx
    cmp ecx, 300
    je .to_finished
    jmp .to

.to_finished:
    pop ecx
    pop eax
    ret

;------------------------------
;print出n的值
;------------------------------
printn:
    push eax
    push ebx
    push ecx
    
    xor ebx, ebx ;flag
    mov ebx, 0
    mov ecx, 0
    
.print:
    ;将内存块特定区域打印出来
    push ecx
    shl ecx, 2
    mov eax, [n+ecx]
    shr ecx, 2
    pop ecx
    
    cmp eax, 0
    je .next
    
    ;增加0的个数
    push eax
    push ecx
    call cal_num_bit
    mov ecx, 9
    sub ecx, eax

.print_zero:    
    cmp ecx, 0
    je .print_num
    cmp ebx, 0
    je .print_num
    mov eax, 0
    call iprint
    dec ecx
    jmp .print_zero

.print_num:
    mov ebx, 1
    pop ecx
    pop eax        
       
    call iprint
    
.next:      
    inc ecx
    cmp ecx, 300
    je .print_finished
    jmp .print

.print_finished:
    push eax
    mov eax, ctrf
    call sprint
    pop eax
    pop ecx
    pop ebx
    pop eax
    ret

;--------------
;将n_2内存块置0
;--------------
mkmem2:
    push eax
    push ecx
    xor eax, eax
    mov ecx, 0

.mking
    ;将内存块特定区域置0
    push ecx
    shl ecx, 2
    mov [n_2+ecx], eax
    shr ecx, 2
    pop ecx
    
    inc ecx
    cmp ecx, 300
    je .mkfinished
    jmp .mking
    
.mkfinished
    pop ecx
    pop eax
    ret

;--------------
;将n_内存块置0
;--------------
mkmem1:
    push eax
    push ecx
    xor eax, eax
    mov ecx, 0

.mking
    
    ;将内存块特定区域置0
    push ecx
    shl ecx, 2
    mov [n_1+ecx], eax
    shr ecx, 2
    pop ecx
    
    inc ecx
    cmp ecx, 300
    je .mkfinished
    jmp .mking
    
.mkfinished
    pop ecx
    pop eax
    ret

;--------------
;将n内存块置0
;--------------
mkmem:
    push eax
    push ecx
    xor eax, eax
    mov ecx, 0

.mking
    
    ;将内存块特定区域置0
    push ecx
    shl ecx, 2
    mov [n+ecx], eax
    shr ecx, 2
    pop ecx
    
    inc ecx
    cmp ecx, 300
    je .mkfinished
    jmp .mking
    
.mkfinished
    pop ecx
    pop eax
    ret
    
    
;--------------
;计算位数
;--------------
cal_num_bit: 
    push ebx
    push ecx
    push edx
    push edi
    ;初始化
    xor ecx, ecx
    xor ebx, ebx
    xor edx, edx
    mov edi, eax
    mov ecx, 1

.divtenloop:
    xor edx, edx
    mov ebx, 10
    div ebx
    cmp eax, 0
    je .finished
    inc ecx
    jmp .divtenloop
    
.finished:
    mov eax, ecx
    pop edi
    pop edx
    pop ecx
    pop ebx
    ret
    
 