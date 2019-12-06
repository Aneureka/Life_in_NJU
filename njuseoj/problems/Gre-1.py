"""
管道网络
Description

Every house in the colony has at most one pipe going into it and at most one pipe going out of it. Tanks and taps are to be installed in a manner such that every house with one outgoing pipe but no incoming pipe gets a tank installed on its roof and every house with only an incoming pipe and no outgoing pipe gets a tap. Find the efficient way for the construction of the network of pipes.


Input

The first line contains an integer T denoting the number of test cases. For each test case, the first line contains two integer n & p denoting the number of houses and number of pipes respectively. Next, p lines contain 3 integer inputs a, b & d, d denoting the diameter of the pipe from the house a to house b.Constraints:1<=T<=50，1<=n<=20，1<=p<=50，1<=a, b<=20，1<=d<=100


Output

For each test case, the output is the number of pairs of tanks and taps installed i.e n followed by n lines that contain three integers: house number of tank, house number of tap and the minimum diameter of pipe between them.
"""


import sys


def dfs(graph, visited, cur_node, path, res):
    visited[cur_node] = True
    if cur_node not in graph.keys():
        path[1] = cur_node
        res.append(tuple(path))
        return
    tmp_path = list(path)
    for next_node in graph[cur_node]:
        path = tmp_path
        v, w = next_node
        if visited[v]:
            continue
        path[2] = min(path[2], w)
        dfs(graph, visited, v, path, res)


def solve(edges):
    nodes = []
    graph = {}
    for edge in edges:
        u, v, w = edge
        if u not in graph.keys():
            graph[u] = [(v, w)]
        else:
            graph[u] += [(v, w)]
        if u not in nodes:
            nodes.append(u)
        if v not in nodes:
            nodes.append(v)
    # filter out nodes with no in-degree
    start_nodes = []
    for node in nodes:
        if node not in [x[0] for y in list(graph.values()) for x in y]:
            if len(graph[node]) == 1:
                start_nodes.append(node)
    res = []
    visited = {}
    for node in nodes:
        visited[node] = False
    for start_node in start_nodes:
        cur_res = []
        dfs(graph, dict(visited), start_node, (start_node, 0, sys.maxsize), cur_res)
        res += cur_res
    res = sorted(list(set(res)))
    print(len(res))
    print("\n".join(["%d %d %d" % (x[0], x[1], x[2]) for x in res]))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, p = [int(x) for x in input().split()]
        edges = []
        for i in range(p):
            edges.append([int(x) for x in input().split()])
        solve(edges)
