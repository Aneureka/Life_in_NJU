"""
深度优先遍历
Description

按照给定的起始顶点深度优先遍历给定的无向图，尝试所有可能的遍历方式，打印遍历过程中出现的最大深度。


Input

输入第一行是用例个数，后面每个用例使用多行表示，用例的第一行是图中节点的个数n和起始点，用空格隔开，后面n+1行为图的邻接矩阵，其中第一行为节点名称。值之间使用空格隔开。


Output

输出深度优先遍历中遇到的最大深度。
"""


def dfs(graph, visited, cur_node):
    if visited[cur_node]:
        return 0
    visited[cur_node] = True
    res = 1
    for next_node in graph[cur_node]:
        res = max(1 + dfs(graph, visited, next_node), res)
    return res


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        n, start_node = input().split(" ")
        n = int(n)
        graph = {}
        nodes = input().split(" ")
        for node in nodes:
            graph[node] = []
        for i in range(n):
            row = input().split(" ")
            node = row[0]
            table = [int(x) for x in row[1:]]
            for idx, exist in enumerate(table):
                if exist == 1:
                    graph[node].append(nodes[idx])
        visited = {}
        for node in nodes:
            visited[node] = False
        res = dfs(graph, visited, start_node)
        print(res)
