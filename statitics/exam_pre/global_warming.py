#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
# from log_api import log
import numpy as np
import pandas as pd
import scipy.stats as stats

class Solution():
	def solve(self):

		# year10 = [25.7, 25.6, 25.6, 22.4, 21.7, 23.3, 23, 22.2, 27.9, 27.2, 28.4, 27.5, 29.3, 29.4, 25.1, 26, 29.1,
		# 		  29.4, 29.9, 28.8, 28.1, 28.4, 25, 23.6, 20.6, 16.4, 24.7, 18.3, 15.4, 21.3, 22.9]
		# year14 = [27.3, 27.7, 27.9, 23.6, 20.6, 24.3, 22.6, 22.5, 31, 30.8, 31.3, 31.1, 29.6, 31.6, 28.6, 30.1, 30.6,
		# 		  32, 27.5, 27.7, 27.9, 30.5, 26.5, 23.1, 19.9, 15.9, 28.3, 21, 18.2, 24.4, 22.8]

		url10 = "http://py.mooctest.net:8081/dataset/temperature/temperature_2010.csv"
		url14 = "http://py.mooctest.net:8081/dataset/temperature/temperature_2014.csv"
		header = ["city", 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
		year10 = np.array(pd.read_csv(url10, skiprows=range(6), skipfooter=3, names=header)[7].values)
		year14 = np.array(pd.read_csv(url14, skiprows=range(5), skipfooter=3, names=header)[7].values)

		diff = year14 - year10

		mean = diff.mean()
		std = diff.std()
		n = float(diff.size)

		result = mean / std * (n**0.5)
		t = stats.t.ppf(1-0.05, n-1)

		return "YES" if result > t else "NO"

print Solution().solve()








