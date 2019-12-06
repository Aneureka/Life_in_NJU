"""
格子里的整数
Description

Given a square grid of size n, each cell of which contains integer cost which represents a cost to traverse through that cell, we need to find a path from top left cell to bottom right cell by which total cost incurred is minimum.

Note : It is assumed that negative cost cycles do not exist in input matrix.


Input

The first line of input will contain number of test cases T. Then T test cases follow . Each test case contains 2 lines. The first line of each test case contains an integer n denoting the size of the grid. Next line of each test contains a single line containing N*N space separated integers depecting cost of respective cell from (0,0) to (n,n).

Constraints:1<=T<=50，1<= n<= 50


Output

For each test case output a single integer depecting the minimum cost to reach the destination.
"""


def get_from_grid(grids, n, i, j):
    return grids[i*n+j]


def dfs(grids, n, visited, p, q, cur, res):
    cur += get_from_grid(grids, n, p, q)
    if p == n-1 and q == n-1:
        res.append(cur)
        return
    directions = [[-1, 0], [1, 0], [0, -1], [0, 1]]
    for d in directions:
        tmp_p = p + d[0]
        tmp_q = q + d[1]
        if 0 <= tmp_p < n and 0 <= tmp_q < n and not visited[tmp_p*n+tmp_q]:
            visited[tmp_p*n+tmp_q] = True
            dfs(grids, n, visited, tmp_p, tmp_q, cur, res)
            visited[tmp_p*n+tmp_q] = False


def solve(grids, n):
    visited = [False] * n**2
    visited[0] = True
    res = []
    dfs(grids, n, visited, 0, 0, 0, res)
    print(min(res))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        grids = [int(x) for x in input().split()]
        solve(grids, n)
