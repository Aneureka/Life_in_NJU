"""
牛的繁殖问题
Description

Cows in the FooLand city are interesting animals. One of their specialties is related to producing offsprings. A cow in FooLand produces its first calve (female calf) at the age of two years and proceeds to produce other calves (one female calf a year).

Now the farmer Harold wants to know how many animals would he have at the end of N years, if we assume that none of the calves die, given that initially, he has only one female calf?

explanation:At the end of 1 year, he will have only 1 cow, at the end of 2 years he will have 2 animals (one parent cow C1 and other baby calf B1 which is the offspring of cow C1).At the end of 3 years, he will have 3 animals (one parent cow C1 and 2 female calves B1 and B2, C1 is the parent of B1 and B2).At the end of 4 years, he will have 5 animals (one parent cow C1, 3 offsprings of C1 i.e. B1, B2, B3 and one offspring of B1).


Input

The first line contains a single integer T denoting the number of test cases. Each line of the test case contains a single integer N as described in the problem.


Output

For each test case print in new line the number of animals expected at the end of N years modulo 10^9 + 7.
"""

res_map = {}


def fib(n):
    if n == 0:
        return 0
    if n == 1 or n == 2:
        return 1
    if n in res_map.keys():
        return res_map[n]
    if n % 2 != 0:
        k = (n+1) // 2
        res_map[n] = int((fib(k) * fib(k) + fib(k-1) * fib(k-1)) % int(1e9 + 7))
    else:
        k = n // 2
        res_map[n] = int((fib(k) * (fib(k-1) * 2 + fib(k))) % int(1e9 + 7))
    return res_map[n]


def solve(n):
    print(int(fib(n+1)))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        solve(n)
