"""
Searching_4
Description

Given n Magnets which are placed linearly, with each magnet to be considered as of point object. Each magnet suffers force from its left sided magnets such that they repel it to the right and vice versa. All forces are repulsive. The force being equal to the distance (1/d , d being the distance). Now given the positions of the magnets, the task to find all the points along the linear line where net force is ZERO.

Note: Distance have to be calculated with precision of 0.0000000000001.

Constraints:1<=T<=100,1<=N<=100,1<=M[]<=1000


Input

The first line of input contains an integer T denoting the no of test cases. Then T test cases follow. The second line of each test case contains an integer N. Then in the next line are N space separated values of the array M[], denoting the positions of the magnet.


Output

For each test case in a new line print the space separated points having net force zero till precised two decimal places.
"""


def solve(positions):
    n = len(positions)
    distances = [0] * n
    for i in range(1, n):
        distances[i] = distances[i-1] + positions[i] - positions[i-1]
    res = []
    for i in range(n-1):
        p = 0
        q = positions[i+1] - positions[i]
        mid = p + (q - p) / 2
        found = False
        while q - p > 0.0000000000001:
            mid = p + (q - p) / 2
            left = 0
            right = 0
            for j in range(i+1):
                left += 1 / (distances[i] - distances[j] + mid)
            for j in range(i+1, n):
                right += 1 / (distances[j] - distances[i+1] + positions[i+1] - positions[i] - mid)
            if left < right:
                q = mid
            elif left > right:
                p = mid
            else:
                found = True
                res.append(positions[i] + mid)
                break
        if not found:
            res.append(positions[i] + mid)
    print(" ".join(["%.2f" % x for x in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        positions = [int(x) for x in input().split()]
        solve(positions)
