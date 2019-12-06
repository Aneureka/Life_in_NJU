"""
棋盘覆盖问题
Description

棋盘覆盖问题：给定一个大小为2^n2^n个小方格的棋盘，其中有一个位置已经被填充，现在要用一个L型（22个小方格组成的大方格中去掉其中一个小方格）形状去覆盖剩下的小方格。求出覆盖方案，即哪些坐标下的小方格使用同一个L型格子覆盖。注意：坐标从0开始。左上方的第一个格子坐标为(0,0)，第一行第二个坐标为(0,1)，第二行第一个为(1,0)，以此类推。


Input

输入第一行为测试用例个数，后面每一个用例有两行，第一行为n值和特殊的格子的坐标（用空格隔开），第二行为需要查找其属于同一个L型格子的格子坐标。


Output

输出每一行为一个用例的解，先按照行值从小到大、再按照列值从小到大的顺序输出每一个用例的两个坐标；用逗号隔开。
"""


def find_block(n, tx, ty):
    c = 2**(n-1)
    b = 0 if ty < c else 1
    if tx >= c:
        b += 2
    return b


def search(n, sx, sy, tx, ty):
    if n == 1:
        return set([(x-tx, y-ty) for (x, y) in list({(0, 0), (0, 1), (1, 0), (1, 1)} - {(sx, sy), (tx, ty)})])
    sb = find_block(n, sx, sy)
    tb = find_block(n, tx, ty)
    c = 2**(n-1)
    next_prob_sp = [(c-1, c-1), (c-1, c), (c, c-1), (c, c)]
    next_prob_sp.pop(sb)
    if (tx, ty) in next_prob_sp:
        return set([(x-tx, y-ty) for (x, y) in list(set(next_prob_sp) - {(tx, ty)})])
    if sb == tb:
        return search(n-1, sx % c, sy % c, tx % c, ty % c)
    else:
        return search(n-1, next_prob_sp[tb][0] % c, next_prob_sp[tb][1] % c, tx % c, ty % c)


def solve(n, sx, sy, tx, ty):
    if tx == sx and ty == sy or tx < 0 or tx >= 2 ** n or ty < 0 or ty >= 2 ** n:
        print()
        return
    relative_points = sorted(list(search(n, sx, sy, tx, ty)))
    points = [(tx + dx, ty + dy) for (dx, dy) in relative_points]
    print(",".join([" ".join([str(x) for x in p]) for p in points]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, sx, sy = [int(x) for x in input().split(" ")]
        tx, ty = [int(x) for x in input().split(" ")]
        solve(n, sx, sy, tx, ty)
