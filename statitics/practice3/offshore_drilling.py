# -*- coding: UTF-8 –*-

"""
The table below summaries a data set that examines the response of a random sample of college graduates
 and non-graduate on the topic of oil drilling. Complete a chi-square test for test data to check
 whether there is a statistically significant difference in responses from college graduates and non-graduates.

College Grad?    Yes    No    Total
Support          154    132    286
Oppose           180    126    306
Do not know      104    131    235
Total            438    389    827

[degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False
"""

import numpy as np
from scipy import stats


class Solution:
	def solve(self):
		es = [154.0, 132.0, 180.0, 126.0, 104.0, 131.0]
		total1 = [286.0, 306.0, 235.0]
		total2 = [438.0, 389.0]
		total = 827.0
		ts = []
		for i in total1:
			for j in total2:
				ts.append(i * j / total)

		alpha = 0.05
		df = 2
		chi2 = stats.chi2.ppf(1-alpha, 2)

		esa = np.array(es, dtype=np.float64)
		tsa = np.array(ts, dtype=np.float64)

		result = ((esa - tsa)**2 / tsa).sum()
		judge = True if result <= chi2 else False

		return [round(df, 2), round(ressult, 2), judge]

"""
Another solution: 

class Solution():
	def solve(self):
		alpha = 0.05
		data = [[154, 132], [180, 126], [104, 131]]

		chi2 = stats.chi2_contingency(data)
		df = chi2[2]
		high = stats.chi2.ppf(1 - alpha, df)
		result = True if chi2[0] <= high else False

		return [round(df, 2), round(chi2[0], 2), result]

"""



Solution().solve()
