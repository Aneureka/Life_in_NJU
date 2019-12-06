"""
整除查询
Description

Given an array of positive integers and many queries for divisibility. In every query Q[i], we are given an integer K , we need to count all elements in the array which are perfectly divisible by K.

Constraints:1<=T<=1001<=N,M<=1051<=A[i],Q[i]<=105


Input

The first line of input contains an integer T denoting the number of test cases. Then T test cases follow. Each test case consists of three lines. First line of each test case contains two integers N & M, second line contains N space separated array elements and third line contains M space separated queries.


Output

For each test case,In new line print the required count for each query Q[i].
"""


def solve(nums, queries):
    res = []
    for q in queries:
        res.append(len([num for num in nums if num//q == num/q]))
    print(" ".join([str(x) for x in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, m = [int(x) for x in input().split()]
        nums = [int(x) for x in input().split()]
        queries = [int(x) for x in input().split()]
        solve(nums, queries)
