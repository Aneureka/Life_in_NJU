"""
棋盘覆盖问题
Description

棋盘覆盖问题：给定一个大小为2^n2^n个小方格的棋盘，其中有一个位置已经被填充，现在要用一个L型（22个小方格组成的大方格中去掉其中一个小方格）形状去覆盖剩下的小方格。求出覆盖方案，即哪些坐标下的小方格使用同一个L型格子覆盖。注意：坐标从0开始。左上方的第一个格子坐标为(0,0)，第一行第二个坐标为(0,1)，第二行第一个为(1,0)，以此类推。


Input

输入第一行为测试用例个数，后面每一个用例有两行，第一行为n值和特殊的格子的坐标（用空格隔开），第二行为需要查找其属于同一个L型格子的格子坐标。


Output

输出每一行为一个用例的解，先按照行值从小到大、再按照列值从小到大的顺序输出每一个用例的两个坐标；用逗号隔开。
"""


# find which block [0-3] the point in
def find_block(n, x, y):
    c = 2**(n-1)
    b = 0 if y < c else 1
    if x >= c:
        b += 2
    return b


def fill_board(n, bx, by, sx, sy, board, cur):
    """
    :param n: 2**n is the size
    :param bx, by: (bx, by) is the base coordinate, in that (bx+sx, by+sy) is the true coordinate of special point
    :param sx, sy: (sx, sy) is the relative coordinate of special point
    :param board: the original board
    :param cur: number to fill the board
    :return:
    """
    # block number of special point
    sb = find_block(n, sx, sy)
    # center of the board
    c = 2 ** (n-1)
    # next probable special points
    next_prob_sp = [(c - 1, c - 1), (c - 1, c), (c, c - 1), (c, c)]
    next_prob_sp.pop(sb)
    # fill the board
    for x, y in next_prob_sp:
        board[bx+x][by+y] = cur
    cur += 1
    if n == 1:
        return cur
    next_prob_sp.insert(sb, (sx, sy))
    next_prob_bs = [(0, 0), (0, c), (c, 0), (c, c)]
    # fill board recursively
    for i, sp in enumerate(next_prob_sp):
        cur = fill_board(n-1,
                         next_prob_bs[i][0] + bx,
                         next_prob_bs[i][1] + by,
                         sp[0] - next_prob_bs[i][0],
                         sp[1] - next_prob_bs[i][1],
                         board,
                         cur+1)
    return cur


def solve(n, sx, sy, tx, ty):
    if tx == sx and ty == sy:
        print()
        return
    board = []
    for i in range(2 ** n):
        board.append([])
        for j in range(2 ** n):
            board[i].append(0)
    fill_board(n, 0, 0, sx, sy, board, 1)
    # find the points with the same count of target point
    ctn = board[tx][ty]
    res = []
    for i in range(max(0, tx - 1), min(2 ** n, tx + 2)):
        for j in range(max(0, ty - 1), min(2 ** n, ty + 2)):
            if i == tx and j == ty:
                continue
            if board[i][j] == ctn:
                res.append((i, j))
    print(",".join([" ".join([str(x) for x in p]) for p in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, sx, sy = [int(x) for x in input().split(" ")]
        tx, ty = [int(x) for x in input().split(" ")]
        solve(n, sx, sy, tx, ty)
