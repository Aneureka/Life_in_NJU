"""
漆狗屋
Description

Dilpreet wants to paint his dog- Buzo's home that has n boards with different lengths[A1, A2,..., An]. He hired k painters for this work and each painter takes 1 unit time to paint 1 unit of the board.The problem is to find the minimum time to get this job done under the constraints that any painter will only paint continuous sections of boards, say board {2, 3, 4} or only board {1} or nothing but not board {2, 4, 5}.

Constraints:1<=T<=100,1<=k<=30,1<=n<=50,1<=A[i]<=500


Input

The first line consists of a single integer T, the number of test cases. For each test case, the first line contains an integer k denoting the number of painters and integer n denoting the number of boards. Next line contains n- space separated integers denoting the size of boards.


Output

For each test case, the output is an integer displaying the minimum time for painting that house.
"""


def solve(boards, k):
    n = len(boards)
    dp = []
    for i in range(k+1):
        dp.append([0] * (n+1))
    for i in range(1, n+1):
        dp[1][i] = sum(boards[:i])
    for i in range(1, k+1):
        dp[i][1] = boards[0]
    for i in range(2, k+1):
        for j in range(2, n+1):
            min_avg = sum(boards)
            for p in range(1, j+1):
                min_avg = min(min_avg, max(dp[i-1][p], sum(boards[p:j])))
            dp[i][j] = min_avg
    print(dp[k][n])


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        k, n = [int(x) for x in input().split()]
        boards = [int(x) for x in input().split()]
        solve(boards, k)
