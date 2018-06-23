# coding: utf-8

from collections import deque

from automaton.util import eliminate_slash

EP = "ep"  # the value of epsilon-edge


class Node(object):

    def __init__(self):
        self.state_id = Graph.alloc_state()
        self.edge_in = []
        self.edge_out = []

    def add_edge_in(self, edge):
        if edge not in self.edge_in:
            self.edge_in.append(edge)

    def add_edge_out(self, edge):
        if edge not in self.edge_out:
            self.edge_out.append(edge)

    def is_terminal(self):
        return len(self.edge_out) == 0

    @staticmethod
    def has_terminal(nodes):
        for node in nodes:
            if node.is_terminal():
                return True
        return False

    def next_epsilon_nodes(self):
        return self.next_nodes_by_value(EP)

    def next_nodes_by_value(self, value):
        return [edge.node_to for edge in self.edge_out if edge.value == value]

    def e_closure(self):
        ctn = []  # container to save the nodes in closure
        queue = deque()  # queue to help
        queue.append(self)
        while len(queue) > 0:
            tmp_node = queue.popleft()
            if tmp_node not in ctn:
                ctn.append(tmp_node)
            queue.extend(tmp_node.next_epsilon_nodes())
        return ctn

    @staticmethod
    def e_closure_of_T(nodes):
        ctn = []
        for node in nodes:
            ctn.extend(node.e_closure())
        return list(set(ctn))

    @staticmethod
    def move(nodes, value):
        ctn = []
        for node in nodes:
            ctn.extend(node.next_nodes_by_value(value))
        return set(ctn)

    @staticmethod
    def get_states_of_T(nodes):
        return set([x.state_id for x in nodes])


class Edge(object):

    def __init__(self, value, node_from, node_to):
        self.value = value
        self.node_from = node_from
        self.node_to = node_to

    def get_value(self):
        return self.value

    def set_value(self, value):
        self.value = value

    def set_node_to(self, node):
        self.node_to = node

    @staticmethod
    def build_edge(value, node_from, node_to):
        edge = Edge(value, node_from, node_to)
        node_from.add_edge_out(edge)
        node_to.add_edge_in(edge)

    @staticmethod
    def build_epsilon_edge(node_from, node_to):
        Edge.build_edge(EP, node_from, node_to)


class Graph(object):

    state_count = 1

    def __init__(self, start, end):
        self.node_start = start
        self.node_end = end

    @staticmethod
    def alloc_state():
        res = Graph.state_count
        Graph.state_count += 1
        return res

    @staticmethod
    def _graph(value):
        start = Node()
        end = Node()
        Edge.build_edge(value, start, end)
        epsilon = Graph(start, end)
        return epsilon

    @staticmethod
    def epsilon_graph():
        return Graph._graph(EP)

    @staticmethod
    def simple_graph(value):
        return Graph._graph(eliminate_slash(value))

    def connect(self, other):
        Edge.build_edge(EP, self.node_end, other.node_start)
        self.node_end = other.node_end

    def parallel_connect(self, other):
        start = Node()
        end = Node()
        # connect the nodes
        Edge.build_epsilon_edge(start, self.node_start)
        Edge.build_epsilon_edge(start, other.node_start)
        Edge.build_epsilon_edge(self.node_end, end)
        Edge.build_epsilon_edge(other.node_end, end)
        # reset the node_start and node_end
        self.node_start = start
        self.node_end = end

    def pow(self):
        start = Node()
        end = Node()
        # connect the nodes
        Edge.build_epsilon_edge(start, self.node_start)
        Edge.build_epsilon_edge(self.node_end, end)
        # create the loop
        Edge.build_epsilon_edge(self.node_end, self.node_start)
        Edge.build_epsilon_edge(start, end)
        # reset the node_start and node_end
        self.node_start = start
        self.node_end = end

    def print(self):
        visited_edges = set()
        queue = deque()
        queue.append(self.node_start)
        while len(queue) > 0:
            tmp_node = queue.popleft()
            for edge in tmp_node.edge_out:
                if edge not in visited_edges:
                    node_to = edge.node_to
                    print("[" + str(tmp_node.state_id) + "] -> " + str(edge.value) + " -> [" + str(
                        node_to.state_id) + "]")
                    visited_edges.add(edge)
                    queue.append(node_to)
