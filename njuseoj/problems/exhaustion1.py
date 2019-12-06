"""
分配问题
Description

对给定的n个任务与n个人之间的成本矩阵完成成本最低的任务分配策略。


Input

输入：第一行为用例个数，之后为每一个用例；用例的第一行为任务个数，即n；用例的第二行为使用逗号隔开的人员完成任务的成本；每一个成本描述包括人员序号、任务序号和成本，使用空格隔开。人员序号和任务序号都是从1到n的整数，序号出现的次序没有固定规则。


Output

输出：每一个用例输出一行，从序号为1的人员开始，给出其分配的任务序号，使用空格隔开；使用逗号将多个解隔开。结果按照人员分配的任务序号大小排，第一个人员的任务序号大的放在前面，如果相同则看第二个人员的任务，以此类推。
"""


def assign_job(cost_list, index, path, res):
    if index >= len(cost_list):
        all_cost = sum(cost_list[i][p] for i, p in enumerate(path))
        res[all_cost] = ([] if all_cost not in res.keys() else res[all_cost]) + [[x+1 for x in path]]
        return
    n = len(cost_list[index])
    for i in range(n):
        if i not in path:
            path.append(i)
            assign_job(cost_list, index+1, path, res)
            path.pop()


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        costs = []
        for i in range(n):
            costs.append([0]*n)
        for person, job, cost in [[int(x) for x in desc.split(" ")] for desc in input().split(",")]:
            costs[person-1][job-1] = cost
        res = {}
        assign_job(costs, 0, [], res)
        minimum_cost = min(list(res))
        res_list = list(reversed(res[minimum_cost]))
        print(",".join([" ".join([str(x) for x in job_list]) for job_list in res_list]))
