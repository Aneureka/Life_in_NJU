"""
拓扑排序解的个数
Description

给定有向无环图中所有边，计算图的拓扑排序解的个数。


Input

输入第一行为用例个数，后面每一行表示一个图中的所有边，边的起点和终点用空格隔开，边之间使用逗号隔开。


Output

输出拓扑排序解的个数。
"""


def compute_topo_count(graph, in_degree):
    # each time we choose the node with zero in-degree
    start_nodes = [node for node in list(
        in_degree.keys()) if in_degree[node] == 0]
    if len(start_nodes) == 0:
        return 1
    res = 0
    for start_node in start_nodes:
        in_degree[start_node] -= 1
        # remove this node and re-compute the in-degree of nodes it connected
        if start_node in graph.keys():
            for next_node in graph[start_node]:
                in_degree[next_node] -= 1
        res += compute_topo_count(graph, in_degree)
        # recover the scene
        if start_node in graph.keys():
            for next_node in graph[start_node]:
                in_degree[next_node] += 1
        in_degree[start_node] += 1
    return res


def solve(raw_edges):
    graph = {}
    nodes = []
    # construct the graph
    for raw_edge in raw_edges:
        u, v = raw_edge.split(" ")
        graph[u] = ([] if u not in graph.keys() else graph[u]) + [v]
        if u not in nodes:
            nodes.append(u)
        if v not in nodes:
            nodes.append(v)
    # compute in-degree of each node
    in_degree = {}
    for node in nodes:
        cnt = 0
        for value in graph.values():
            if node in value:
                cnt += 1
        in_degree[node] = cnt
    print(compute_topo_count(graph, in_degree))


if __name__ == '__main__':
    test_cnt = int(input())
    for t in range(test_cnt):
        raw_edges = input().split(",")
        solve(raw_edges)
