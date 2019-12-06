"""
时间分隔
Description

Given arrival and departure times of all trains that reach a railway station. Your task is to find the minimum number of platforms required for the railway station so that no train waits.

Note: Consider that all the trains arrive on the same day and leave on the same day. Also, arrival and departure times must not be same for a train.


Input

The first line of input contains T, the number of test cases. For each test case, first line will contain an integer N, the number of trains. Next two lines will consist of N space separated time intervals denoting arrival and departure times respectively.

Note: Time intervals are in the 24-hourformat(hhmm), preceding zeros are insignificant. 200 means 2:00.
Consider the example for better understanding of input.

Constraints:1 <= T <= 100，1 <= N <= 1000，1 <= A[i] < D[i] <= 2359


Output

For each test case, print the minimum number of platforms required for the trains to arrive and depart safely.
"""


def intersect(a1, d1, a2, d2):
    if d1 < a2 or d2 < a1:
        return a1, d1
    return max(a1, a2), min(d1, d2)


def solve(arrives, departures):
    n = len(arrives)
    res = 0
    for i in range(n-1):
        cnt = 1
        a = arrives[i]
        d = departures[i]
        for j in range(i+1, n):
            next_a, next_d = intersect(a, d, arrives[j], departures[j])
            if next_a != a or next_d != d:
                cnt += 1
                a = next_a
                d = next_d
        res = max(res, cnt)
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        arrives = [int(x) for x in input().split()]
        departures = [int(x) for x in input().split()]
        solve(arrives, departures)
