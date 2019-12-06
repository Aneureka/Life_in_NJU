"""
最小化初始点
Description

Given a grid with each cell consisting of positive, negative or no points i.e, zero points. We can move across a cell only if we have positive points ( > 0 ). Whenever we pass through a cell, points in that cell are added to our overall points. We need to find minimum initial points to reach cell (m-1, n-1) from (0, 0) by following these certain set of rules :

1.From a cell (i, j) we can move to (i+1, j) or (i, j+1).

2.We cannot move from (i, j) if your overall points at (i, j) is <= 0.

3.We have to reach at (n-1, m-1) with minimum positive points i.e., > 0.


Input

The first line contains an integer 'T' denoting the total number of test cases.In each test cases, the first line contains two integer 'R' and 'C' denoting the number of rows and column of array.
The second line contains the value of the array i.e the grid, in a single line separated by spaces in row major order.

Constraints:

1 ≤ T ≤ 30

1 ≤ R,C ≤ 10

-30 ≤ A[R][C] ≤ 30

Input: points[m][n] = { {-2, -3, 3},
{-5, -10, 1},
{10, 30, -5}
};


Output

Print the minimum initial points to reach the bottom right most cell in a separate line.

7

Explanation:
7 is the minimum value to reach destination with
positive throughout the path. Below is the path.

(0,0) -> (0,1) -> (0,2) -> (1, 2) -> (2, 2)

We start from (0, 0) with 7, we reach(0, 1)
with 5, (0, 2) with 2, (1, 2) with 5, (2, 2)with and finally we have 1 point (we needed
greater than 0 points at the end).
"""


def solve(points, r, c):
    dp = []
    for i in range(r):
        dp.append([0] * c)
    for i in range(r-1, -1, -1):
        for j in range(c-1, -1, -1):
            dp[i][j] = -points[i*r+j]
            tmp = 0
            if i < r-1:
                tmp = dp[i+1][j]
            if j < c-1:
                if i < r-1:
                    tmp = min(tmp, dp[i][j+1])
                else:
                    tmp = dp[i][j+1]
            dp[i][j] += tmp
            if dp[i][j] <= -points[i*r+j]:
                dp[i][j] += 1
            if dp[i][j] < 1:
                dp[i][j] = 1
    print(dp[0][0])


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        r, c = [int(x) for x in input().split()]
        points = [int(x) for x in input().split()]
        solve(points, r, c)
