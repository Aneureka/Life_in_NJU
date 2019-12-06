"""
有9个因数的数
Description

Find the count of numbers less than N having exactly 9 divisors

1<=T<=1000,1<=N<=10^12


Input

First Line of Input contains the number of testcases. Only Line of each testcase contains the number of members N in the rival gang.


Output

Print the desired output.
"""

import math


def solve(n):
    res = 0
    sqrt_n = int(math.sqrt(n))
    primes = [0] * (sqrt_n+1)
    for i in range(sqrt_n+1):
        primes[i] = i
    for i in range(2, int(math.sqrt(sqrt_n)) + 1):
        if primes[i] == i:
            for j in range(i**2, sqrt_n+1, i):
                if primes[j] == j:
                    primes[j] = i
    for i in range(2, sqrt_n+1):
        # p is the min prime of i
        p = primes[i]
        # q is another factor of i, but may be not prime
        q = primes[i // primes[i]]
        # if q is prime, then p * q == i should be true
        if p * q == i and q != 1 and p != q:
            res += 1
        elif primes[i] == i:
            # i**8 should not be exceeded
            if i**8 <= n:
                res += 1
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        solve(n)
