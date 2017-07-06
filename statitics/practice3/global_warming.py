# -*- coding: UTF-8 –*-

"""
Is there strong evidence of global warming?
Let's consider a small scale example, comparing how temperatures have changed is the US from 1968 to 2008.
The daily high temperature reading on January 1 was collected in 1968 and 2008 for 51 randomly selected locations in the continental US.
Then the difference between the two readings (temperature in 2008 - temperature in 1968) was calculated
for each of the 51 values was 1.1 degree with a standard deviation of 4.9 degrees.
We are interested in determining whether these data provide strong evidence of temperature warming in the continental US.
(1) Is there a relationship between the observations collected in 1968 and 2008? Or are the observations in the two groups independent? Explain
(2) What's the hypothesis for this research?
(3) Check the conditions required to complete this test.
(4) Calculate the freedom, test statistical value and give the conclusion. alpha is 0.05, coding this part
(5) What type of error might we have made?
(6) Based on the results of this hypothesis test, would you expect a confidence interval for the average difference between the temperature measurements from 1968 and 2008 to include 0? Explain

[degree-of-freedom-of-distribution, statistical values, conclusion],'degree-of-freedom-of-distribution' and 'statistical values' are accurate to the second decimal place, 'conclusion' is True, which means the H0 is accepted, or False
"""

import numpy as np
from scipy import stats


class Solution():
	def solve(self):
		n = 51
		mean = 1.1
		std = 4.9
		alpha = 0.05
		t = stats.t.ppf(1-alpha/2, n-1)
		print t

		# calculate
		result = mean / std * (n**0.5)

		judge = False if result >= t else True

		return [round(n-1, 2), round(result, 2), judge]

print Solution().solve()