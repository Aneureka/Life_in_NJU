# coding=utf-8


def count(strs):
    return [str for str in strs if is_palindrome(str)]

def is_palindrome(str):
    for i in range(len(str)):
        if str[i] != str[-i-1]:
            return False
    return True


