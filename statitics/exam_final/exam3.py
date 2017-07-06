#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''

import numpy as np
from scipy import stats

class Solution():
	def solve(self):
		alpha = 0.05
		yr10_r = [6, 17.3, 104.3, 101, 120.4, 91.9, 30, 41.9, 23.9, 101.2, 67.7, 48.7, 39.9, 49, 136.6, 117.6, 52.7, 64.9, 101.3, 83.5, 2.1, 58.6, 94.6, 62.4, 41.8, 0.2, 74.2, 40.1, 12.7, 27.8, 51.5]
		yr14_r = [52041, 207793, 176469, 88880, 96190, 130672, 57246, 65987, 172867, 110665, 82021, 41483, 76043, 40756, 81118, 106123, 96222, 21173, 65589, 33045, 1798, 494415, 52040, 70603, 102842, 930, 69103, 72148, 71839, 92369, 74216]

		yr10 = np.array(yr10_r, np.float64) * 10000.0
		yr14 = np.array(yr14_r, np.float64)

		diff = yr14 - yr10

		n = diff.size
		mean = diff.mean()
		s = (diff.var() * n / (n-1))**0.5
		result = mean / s * (n**0.5)
		t = stats.t.ppf(1 - alpha, n-1)
		print result, t

		judge = "YES" if result >= t else "NO"

		return [result, judge]


print Solution().solve()
