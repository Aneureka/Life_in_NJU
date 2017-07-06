# -*- coding: UTF-8 –*-

"""
A group of researchers are interested in the possible effects of distracting stimuli during eating,
such as an increase or decrease in the amount of food consumption.
To test this hypothesis, they monitored food in take for a group of 44 patients who were randomised into two equal groups.
The treatment group ate lunch while playing solitaire, and the control group ate lunch without any added distractions.
Patients in the treatment group ate 52.1 grams of biscuits, with a standard deviation of 45.1 grams,
and patients in the control group ate 27.1 grams of biscuits with a standard deviation of 26.4 grams.
Do these data provide convincing evidence that the average food intake is different for the patients in the treatment group?
Assume the conditions for inference are satisfied.
Null hypothesis is H0: u_t - u_c = 0, alpha is 0.05
"""


import numpy as np
from scipy import stats

class Solution():
	def solve(self):
		n1 = n2 = 22.0
		mean1, s1 = 52.1, 45.1
		mean2, s2 = 27.1, 26.4
		alpha = 0.05

		# calculate
		sw2 = ((n1-1)*(s1**2) + (n2-1)*(s2**2)) / (n1+n2-2)
		t = stats.t.ppf(1 - alpha/2, n1+n2-2)
		result = (mean1 - mean2) / (sw2 * (1/n1 + 1/n2))**0.5
		judge = True if abs(result) < t else False

		# The exact value of freedom degree is (n1+n2-2)/2, but I don't know why.
		return [round(n1+n2-2, 2), round(result, 2), judge]



print Solution().solve()