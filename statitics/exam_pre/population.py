#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''
# from log_api import log
import numpy as np
import pandas as pd
from scipy import stats

class Solution():
	def solve(self):

		url_old = "http://py.mooctest.net:8081/dataset/population/population_old.csv"
		url_total = "http://py.mooctest.net:8081/dataset/population/population_total.csv"
		popu_old = np.array(pd.read_csv(url_old, skiprows=range(3), names=range(16))[1].values, dtype=np.float64)
		popu_total = np.array(pd.read_csv(url_total, skiprows=range(5), names=range(17))[4].values, dtype=np.float64)

		data = popu_old / popu_total * 100

		n = data.size
		mean = data.mean()
		std = data.std()
		print std

		za = 1.65
		zb1 = 43.7730
		zb2 = 18.4927
		t = stats.t.ppf(0.95, n-1)
		print t

		mean_d = [mean - std / (n ** 0.5) * za, mean + std / (n ** 0.5) * za]
		mean_t = [mean - std / (n ** 0.5) * t, mean + std / (n ** 0.5) * t]
		var_d = [(n-1) * (std**2) / zb1, (n-1) * (std**2) / zb2]
		print mean_d, var_d
		print mean_t

		return [mean_t, var_d]


Solution().solve()
