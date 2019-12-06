"""
字符串匹配问题
Description

Given a text txt[0..n-1] and a pattern pat[0..m-1], write a function search(char pat[], char txt[]) that prints all occurrences of pat[] in txt[]. You may assume that n > m.


Input

输入第一行是用例个数，后面一行表示一个用例；用例包括两部分，第一部分为给定文本，第二部分为搜索串，两部分使用","隔开。


Output

每一个用例输出一行，每行按照找到的位置先后顺序排列，使用空格隔开。
"""


def solve(sentence, word):
    res = []
    for i in range(len(sentence) - len(word) + 1):
        if sentence[i:i+len(word)] == word:
            res.append(i)
    print(" ".join([str(x) for x in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        sentence, word = input().split(",")
        solve(sentence, word)
