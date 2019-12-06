"""
Description

Given a string ‘str’ of digits, find length of the longest substring of ‘str’, such that the length of the substring is 2k digits and sum of left k digits is equal to the sum of right k digits.


Input

输入第一行是测试用例的个数，后面每一行表示一个数字组成的字符串，例如："123123"


Output

输出找到的满足要求的最长子串的长度。例如，给定的例子长度应该是 6。每行对应一个用例的结果。
"""


def solve(s):
    n = len(s)
    sums = [0] * (n+1)
    for i in range(n+1):
        if i == 0:
            sums[i] = 0
        else:
            sums[i] = sums[i-1] + int(s[i-1])
    res = 0
    for i in range(1, n // 2 + 1):
        for j in range(0, n - 2 * i + 1):
            if sums[j+i] - sums[j] == sums[j+2*i] - sums[j+i]:
                res = max(res, 2*i)
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        s = input()
        solve(s)
