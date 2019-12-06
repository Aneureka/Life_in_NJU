"""
点的凸包
Description

Convex Hull of a set of points, in 2D plane, is a convex polygon with minimum area such that each point lies either on the boundary of polygon or inside it. Now given a set of points the task is to find the convex hull of points.


Input

The first line of input contains an integer T denoting the no of test cases. Then T test cases follow. Each test case contains an integer N denoting the no of points. Then in the next line are N*2 space separated values denoting the points ie x and y.Constraints:1<=T<=100,1<=N<=100,1<=x,y<=1000


Output

For each test case in a new line print the points x and y of the convex hull separated by a space in sorted order (increasing by x) where every pair is separated from the other by a ','. If no convex hull is possible print -1.
"""


def orientation(p, q, r):
    return (q[1] - p[1]) * (r[0] - q[0]) - (q[0] - p[0]) * (r[1] - q[1])


def solve(points):
    points.sort(key=lambda p: (p[0], p[1]))
    hull = []
    for p in points:
        while len(hull) >= 2 and orientation(hull[-2], hull[-1], p) > 0:
            hull.pop()
        hull.append(p)
    hull.pop()
    for p in reversed(points):
        while len(hull) >= 2 and orientation(hull[-2], hull[-1], p) > 0:
            hull.pop()
        hull.append(p)
    hull = list(set(hull))
    hull.sort(key=lambda p: (p[0], p[1]))
    print(", ".join("%d %d" % (p[0], p[1]) for p in hull))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        nums = [int(x) for x in input().split()]
        points = list(zip(nums[::2], nums[1::2]))
        solve(points)
