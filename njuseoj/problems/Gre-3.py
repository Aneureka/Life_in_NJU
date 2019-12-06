"""
时间与收益
Description

Given a set of n jobs where each job i has a deadline and profit associated to it. Each job takes 1 unit of time to complete and only one job can be scheduled at a time. We earn the profit if and only if the job is completed by its deadline. The task is to find the maximum profit and the number of jobs done.


Input

The first line of input contains an integer T denoting the number of test cases.Each test case consist of an integer N denoting the number of jobs and the next line consist of Job id, Deadline and the Profit associated to that Job.

Constraints:1<=T<=100，1<=N<=100，1<=Deadline<=100，1<=Profit<=500


Output

Output the number of jobs done and the maximum profit.
"""


def solve(jobs):
    ddls = set([j[1] for j in jobs])
    cnt = 0
    profit = 0
    for ddl in range(max(ddls), 0, -1):
        cur_jobs = [j for j in jobs if j[1] >= ddl]
        if len(cur_jobs) > 0:
            max_profit = 0
            selected_job = None
            for job in cur_jobs:
                if job[2] > max_profit:
                    max_profit = job[2]
                    selected_job = job
            cnt += 1
            profit += max_profit
            jobs.remove(selected_job)
    print(cnt, profit)


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n = int(input())
        raw_inputs = [int(x) for x in input().split()]
        jobs = []
        for i in range(n):
            jobs.append((raw_inputs[3*i], raw_inputs[3*i+1], raw_inputs[3*i+2]))
        solve(jobs)
