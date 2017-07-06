#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
from log_api import log

class Solution():
	def solve(self, A):
		#call function prime
		nums = A
		return [num for num in nums if self.prime(num)]

	#judge whether x is prime or not
	def prime(self, x):
		if x == 1:
			return False
		k = int(x ** 0.5)
		for j in range(2, k + 1):
			if x % j == 0:
				return False
		return True

