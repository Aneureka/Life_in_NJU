"""
是否能通过考试
Description

小张想要通过明天的考试。他知道考题的分值分布，也知道考试中要拿到每一个题目需要耗费的时间。假设考试时长为h，共n个题目，需要拿到p分才能通过考试。现在已知每个考题的得分与耗时，请你判断小张能否通过合理安排时间，而通过考试，并给出通过考试的最短时间。


Input

输入第一行为测试用例个数.每一个用例有若干行，第一行为任务数量n、考试时常h、通过分数p，下面的n行是每一个题目的耗时和得分。所有数值用空格分开。


Output

对每一个用例输出一行，如果能够通过考试，则输出“YES”和消耗最短时间，用空格隔开。 否则，输出“NO”。
"""


def solve(questions, h, p):
    dp = [0] * h
    for i in range(h):
        if questions[0][0] <= i+1:
            dp[i] = questions[0][1]
    for i in range(1, n):
        for j in range(h-1, 0, -1):
            if j >= questions[i][0]:
                dp[j] = max(dp[j-questions[i][0]] + questions[i][1], dp[j])
    for i in range(h):
        if dp[i] >= p:
            print("YES", i+1)
            return
    print("NO")


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, h, p = [int(x) for x in input().split()]
        questions = []
        for i in range(n):
            questions.append([int(x) for x in input().split()])
        solve(questions, h, p)
