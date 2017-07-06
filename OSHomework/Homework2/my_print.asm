global myPrint

section .text

myPrint:
	mov ecx, [esp + 4]
	mov edx, [esp + 8]

print:
	mov eax, 4
	mov ebx, 1
	int 80h
	ret
