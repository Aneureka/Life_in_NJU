"""
无限递归字符串查询
Description

Consider a string A = "12345". An infinite string s is built by performing infinite steps on A recursively. In i-th step, A is concatenated with ‘$’ i times followed by reverse of A. A=A|$...$|reverse(A), where | denotes concatenation.

Constraints:1<=Q<=10^5, 1<=POS<=10^12


Input

输入第一行为查询次数，后面为每次查询的具体字符位置。


Output

输出每一次查询位置上的字符。
"""


def solve(index):
    cur_len = 5
    step = 0
    while index > cur_len:
        step += 1
        cur_len = 2 * cur_len + step
    while cur_len > 5:
        next_len = (cur_len - step) // 2
        if next_len < index <= next_len + step:
            print("$")
            return
        elif index <= next_len:
            cur_len = next_len
        else:
            index = cur_len - index + 1
            cur_len = next_len
        step -= 1

    print(index)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        index = int(input())
        solve(index)
