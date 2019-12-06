"""
距离问题
Description

In a given cartesian plane, there are N points. We need to find the Number of Pairs of points(A,B) such that

Point A and Point B do not coincide.
Manhattan Distance and the Euclidean Distance between the points should be equal.
Note : Pair of 2 points(A,B) is considered same as Pair of 2 points(B,A).

Manhattan Distance = |x2-x1|+|y2-y1|

Euclidean Distance = ((x2-x1)^2 + (y2-y1)^2)^0.5 where points are (x1,y1) and (x2,y2).

Constraints:1<=T <= 50, 1<=N <= 2*10 ^ 5, 0<=(|Xi|, |Yi|) <= 10^9


Input

First Line Consist of T - number of test cases. For each Test case:First Line consist of N , Number of points. Next line contains N pairs contains two integers Xi and Yi，i.e, X coordinate and the Y coordinate of a Point


Output

Print the number of pairs as asked above.
"""


def solve(points):
    x_freq = {}
    y_freq = {}
    cnt = 0
    for p in points:
        x_freq[p[0]] = (0 if p[0] not in x_freq.keys() else x_freq[p[0]]) + 1
        y_freq[p[1]] = (0 if p[1] not in y_freq.keys() else y_freq[p[1]]) + 1
    for v in x_freq.values():
        cnt += v * (v-1) / 2
    for v in y_freq.values():
        cnt += v * (v-1) / 2
    print(int(cnt))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        points = []
        for i in range(n):
            points.append([int(x) for x in input().split()])
        solve(points)
