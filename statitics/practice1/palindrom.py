#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
from log_api import log

class Solution():
	def solve(self, A):
		#use isPalindrom function to check if the string is palindrome or not
		strs = A
		return [str for str in strs if self.isPalindrome(str)]

	def isPalindrome(self, str):
		for i in range(len(str)):
			if str[i] != str[-i - 1]:
				return False
		return True


