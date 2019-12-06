"""
素数和问题
Description

Given an even number ( greater than 2 ), return two prime numbers whose sum will be equal to given number. There are several combinations possible. Print only first such pair.

NOTE: A solution will always exist, read Goldbach’s conjecture.Also, solve the problem in linear time complexity, i.e., O(n).


Input

The first line contains T, the number of test cases. The following T lines consist of a number each, for which we'll find two prime numbers.Note: The number would always be an even number.


Output

For every test case print two prime numbers space separated, such that the smaller number appears first. Answer for each test case must be in a new line.
"""


def solve(num):
    primes = [True] * (num+1)
    for i in range(2, num+1):
        if primes[i]:
            for j in range(2*i, num+1, i):
                primes[j] = False
    for i in range(2, num // 2 + 1):
        if primes[i] and primes[num-i]:
            print("%d %d" % (i, num-i))
            break


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        num = int(input())
        solve(num)
