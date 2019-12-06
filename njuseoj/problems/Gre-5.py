"""
路上的球
Description

There are two parallel roads, each containing N and M buckets, respectively. Each bucket may contain some balls. The buckets on both roads are kept in such a way that they are sorted according to the number of balls in them. Geek starts from the end of the road which has the bucket with a lower number of balls(i.e. if buckets are sorted in increasing order, then geek will start from the left side of the road). The geek can change the road only at the point of intersection(which means, buckets with the same number of balls on two roads). Now you need to help Geek to collect the maximum number of balls.


Input

The first line of input contains T denoting the number of test cases. The first line of each test case contains two integers N and M, denoting the number of buckets on road1 and road2 respectively. 2nd line of each test case contains N integers, number of balls in buckets on the first road. 3rd line of each test case contains M integers, number of balls in buckets on the second road.

Constraints:1<= T <= 1000，1<= N <= 10^3，1<= M <=10^3，0<= A[i],B[i]<=10^6


Output

For each test case output a single line containing the maximum possible balls that Geek can collect.
"""


def reverse_list(l):
    for i in range(l):
        l[i], l[n-1-i] = l[n-1-i], l[i]


def solve(buckets1, buckets2):
    m, n = len(buckets1), len(buckets2)
    if m > 0 and buckets1[0] > buckets1[-1]:
        reverse_list(buckets1)
    if n > 0 and buckets2[0] > buckets2[-1]:
        reverse_list(buckets2)
    sums1, sums2 = [], []
    for i in range(m):
        sums1.append(buckets1[i] + (0 if i == 0 else sums1[i-1]))
    for i in range(n):
        sums2.append(buckets2[i] + (0 if i == 0 else sums2[i-1]))
    intersects = []
    p, q = 0, 0
    while p < n and q < n:
        if buckets1[p] < buckets2[q]:
            p += 1
        elif buckets1[p] > buckets2[q]:
            q += 1
        else:
            intersects.append((p, q))
            p += 1
            q += 1
    res = 0
    if len(intersects) == 0:
        res = max(sums1[m-1], sums2[n-1])
    else:
        for i in range(len(intersects)):
            sum1 = sums1[intersects[i][0]] - (0 if i == 0 else sums1[intersects[i-1][0]])
            sum2 = sums2[intersects[i][1]] - (0 if i == 0 else sums2[intersects[i-1][1]])
            res += max(sum1, sum2)
        res += max(sums1[m-1] - sums1[intersects[-1][0]], sums2[n-1] - sums2[intersects[-1][1]])
    print(res)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, m = [int(x) for x in input().split()]
        buckets1 = [int(x) for x in input().split()]
        buckets2 = [int(x) for x in input().split()]
        solve(buckets1, buckets2)


