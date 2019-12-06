"""
和最大的连续降序字符
Description

Archana is very fond of strings. She likes to solve many questions related to strings. She comes across a problem which she is unable to solve. Help her to solve. The problem is as follows: Given is a string of length L. Her task is to find the longest string from the given string with characters arranged in descending order of their ASCII code and in arithmetic progression. She wants the common difference should be as low as possible(at least 1) and the characters of the string to be of higher ASCII value.


Input

The first line of input contains an integer T denoting the number of test cases. Each test contains a string s of lengthL.

1<= T <= 100

3<= L <=1000

A<=s[i]<=Z

The string contains minimum three different characters.


Output

For each test case print the longest string.Case 1:Two strings of maximum length are possible- “CBA” and “RPQ”. But he wants the string to be of higher ASCII value therefore, the output is “RPQ”.Case 2:The String of maximum length is “JGDA”.
"""


def solve(string):
    string = "".join(sorted(set(string), reverse=True))
    n = len(string)
    if len(string) < 3:
        print(string)
        return
    res = string[0] + string[1]
    for i in range(n-2):
        for j in range(i+1, n-1):
            diff = ord(string[i]) - ord(string[j])
            cur = string[i] + string[j]
            prev = j
            for k in range(j+1, n):
                if ord(string[prev]) - ord(string[k]) == diff:
                    prev = k
                    cur += string[k]
            if len(cur) > len(res):
                res = cur
    res = "".join(res)
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        string = input()
        solve(string)
