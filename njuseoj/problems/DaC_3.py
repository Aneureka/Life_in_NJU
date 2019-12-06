"""
数学公式
Description

Implement pow(A, B) % C.In other words, given A, B and C, find (A^B)%C


Input

The first line of input consists number of the test cases. The following T lines consist of 3 numbers each separated by a space and in the following order:A B C'A' being the base number, 'B' the exponent (power to the base number) and 'C' the modular.Constraints:1 ≤ T ≤ 70,1 ≤ A ≤ 10^5,1 ≤ B ≤ 10^5,1 ≤ C ≤ 10^5


Output

In each separate line print the modular exponent of the given numbers in the test case.
"""


def solve(a, b, c):
    res = 1
    for i in range(b):
        res *= a % c
        res %= c
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        a, b, c = [int(x) for x in input().split()]
        solve(a, b, c)
