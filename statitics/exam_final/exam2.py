#-*- coding:utf-8 -*-
'''
log api example: log('output is: ' + str(output))
'''

import numpy as np
from scipy import stats

class Solution():
	def solve(self):
		adults_r = [1672749, 992504, 5759327, 2810549, 1948123, 3713575, 2209254, 3000940, 2040875, 6482159, 4611756, 4211557, 2880394, 3242397, 7688407, 7124733, 4391940, 4920101, 7852679, 3301266, 640690, 2107266, 6598501, 2404526, 3438510, 195270, 3012481, 2077912, 411514, 467146, 1608023]
		all_r = [1849475, 1127589, 7037620, 3477805, 2310941, 4252076, 2551123, 3465051, 2253525, 7577122, 5400348, 5312628, 3477491, 4251692, 9272503, 9224288, 5226904, 6096586, 9676589, 4362551, 826560, 2609882, 8161604, 3332265, 4467537, 265904, 3614887, 2623094, 535412, 611957, 2086576]
		atwork_r = [977387, 550787, 4004477, 1729275, 1285021, 2345361, 1429001, 1868107, 1251461, 4486706, 3280387, 2897236, 1950006, 2251205, 5694220, 5006277, 3035863, 3389354, 5525078, 2447946, 426823, 1374581, 4688003, 1676240, 2668421, 147821, 1996061, 1414319, 290469, 327094, 1133002]
		notwork_r = [43068, 31049, 90409, 72118, 35983, 92261, 51028, 71929, 57230, 104882, 105231, 65015, 55129, 54102, 109212, 147342, 90522, 109910, 215847, 82808, 23753, 49750, 113674, 46877, 41751, 1460, 62746, 48069, 6521, 7838, 30798]
		eco_r = [1020455, 581836, 4094886, 1801393, 1321004, 2437622, 1480029, 1940036, 1308691, 4591588, 3385618, 2962251, 2005135, 2305307, 5803432, 5153619, 3126385, 3499264, 5740925, 2530754, 450576, 1424331, 4801677, 1723117, 2710172, 149281, 2058807, 1462388, 296990, 334932, 1163800]

		adults = np.array(adults_r, dtype=np.float64)
		all = np.array(all_r, dtype=np.float64)
		atwork = np.array(atwork_r, dtype=np.float64)
		notwork = np.array(notwork_r, dtype=np.float64)
		eco = np.array(eco_r, dtype=np.float64)

		adult_per = adults * 100 / all
		atwork_per = atwork / eco
		notwork_per = notwork / eco

		n1, mean1, s1 = adult_per.size, adult_per.mean(), adult_per.std()
		n2, mean2, var2 = atwork_per.size, atwork_per.mean(), atwork_per.var()
		n3, mean3, var3 = notwork_per.size, notwork_per.mean(), notwork_per.var()

		alpha = 0.1
		t = stats.t.ppf(1 - alpha / 2, n1-1)
		f1 = stats.f.ppf(1 - alpha / 2, n2-1, n3-1)
		f2 = stats.f.ppf(alpha / 2, n2-1, n3-1)

		result1 = [mean1 - s1 / (n1**0.5) * t, mean1 + s1 / (n1**0.5) * t]
		result2 = [(var2 / var3) / f1, (var2 / var3) / f2]

		return [result1, result2]


print Solution().solve()


