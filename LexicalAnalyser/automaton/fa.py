# coding: utf-8

from automaton.graph import Graph, Node
from automaton.util import is_legal_char, get_alphas_of_list, eliminate_slash_of_list
from automaton.regexp import RegExp
from prettytable import PrettyTable


class NFA(object):
    def __init__(self, graph):
        self.graph = graph

    @staticmethod
    def from_re(reg_exp):
        gs = []  # graph stack
        for ch in reg_exp:
            if is_legal_char(ch):
                gs.append(Graph.simple_graph(ch))
            elif ch == '·':
                graph2 = gs.pop()
                graph1 = gs.pop()
                graph1.connect(graph2)
                gs.append(graph1)
            elif ch == '|':
                graph2 = gs.pop()
                graph1 = gs.pop()
                graph1.parallel_connect(graph2)
                gs.append(graph1)
            elif ch == '*':
                graph = gs.pop()
                graph.pow()
                gs.append(graph)
            else:
                raise Exception("Not supported character: " + str(ch))
        return NFA(gs.pop())


class DFA(object):
    def __init__(self, alphas, state_list, route_list):
        self.alphas = alphas
        self.state_list = state_list
        self.route_list = route_list

    @staticmethod
    def from_re(re):
        alphas = get_alphas_of_list(re)
        eliminate_slash_of_list(alphas)
        graph = NFA.from_re(re).graph
        s0 = Node.e_closure_of_T([graph.node_start])
        # init the tran_table
        state_list = [s0]
        route_list = []
        # construct the tran_table
        ptr = 0
        while ptr < len(state_list):
            si = state_list[ptr]
            cur_routes = []
            for ch in alphas:
                next_state = Node.e_closure_of_T(Node.move(si, ch))
                if next_state not in state_list and len(next_state) != 0:
                    state_list.append(next_state)
                cur_routes.append(state_list.index(next_state) if len(next_state) != 0 else -1)
            route_list.append(cur_routes)
            # print(cur_routes)
            ptr += 1
        dfa = DFA(alphas, state_list, route_list)
        dfa._optimize()
        return dfa

    def is_satisfied(self, seq):
        state = 0
        for ch in seq:
            if ch not in self.alphas:
                return False
            next_state = self._next_state(state, ch)
            if ch not in self.alphas or next_state == -1:
                return False
            state = next_state
        return True if self._is_final_state(state) else False

    def print(self):
        self._visualize_tran_table()

    def _optimize(self):
        # self.print()
        state_counts = self._get_state_count()
        # initialize the original state list
        state_groups = self._get_final_divided_state_groups()
        # get the represent state of each group
        rep = [x[0] for x in state_groups]
        non_rep = [x for x in range(state_counts) if x not in rep]
        rep_map = [-1] * (state_counts)
        for group in state_groups:
            for state in group:
                rep_map[state] = group[0]
        rep_map.append(-1)
        # represent
        for i in range(state_counts):
            self.route_list[i] = [rep_map[x] for x in self.route_list[i]]
        # reduce the high state id
        reduces = [0] * state_counts
        for state in non_rep:
            for index in range(state, state_counts):
                reduces[index] += 1
        reduces.append(0)
        for i in range(state_counts):
            self.route_list[i] = [x - reduces[x] for x in self.route_list[i]]
        # pop
        for state in reversed(non_rep):
            self.state_list.pop(state)
            self.route_list.pop(state)

    def _get_state_count(self):
        return len(self.state_list)

    def _is_final_state(self, state_id):
        return Node.has_terminal(self.state_list[state_id])

    def _get_final_divided_state_groups(self):
        state_groups = self._get_initial_state_groups()
        new_state_groups = self._next_devided_state_list(state_groups)
        while state_groups != new_state_groups:
            state_groups = new_state_groups
            new_state_groups = self._next_devided_state_list(new_state_groups)
        # print("Final state groups:", end="\t"); print(state_groups)
        return state_groups

    def _get_initial_state_groups(self):
        sl = self.state_list
        return [[sl.index(s) for s in sl if Node.has_terminal(s)],
                [sl.index(s) for s in sl if not Node.has_terminal(s)]]

    def _next_devided_state_list(self, state_groups):
        former_state_groups = state_groups
        # print(former_state_groups)
        for alpha in self.alphas:
            new_state_groups = []
            for state_list in former_state_groups:
                # create a dict to save the temporal result
                res = dict()
                for state in state_list:
                    next_state = self._next_state(state, alpha)
                    # judge which group the next state in
                    if next_state == -1:
                        group_index = -1
                    else:
                        for i in range(len(state_groups)):
                            if next_state in state_groups[i]:
                                group_index = i
                                break
                    if group_index in res.keys():
                        res[group_index].append(state)
                    else:
                        res[group_index] = [state]
                # append to new state groups
                for value in res.values():
                    new_state_groups.append(value)
            former_state_groups = new_state_groups
        return former_state_groups

    def _next_state(self, state, alpha):
        return self.route_list[state][self.alphas.index(alpha)]

    def _visualize_tran_table(self):
        table = PrettyTable(["state"] + list(self.alphas))
        for i in range(len(self.state_list)):
            state = Node.get_states_of_T(self.state_list[i])
            route_states = [x for x in self.route_list[i]]
            table.add_row([state] + route_states)
        print(table)


if __name__ == '__main__':
    pos_re = RegExp.to_postfix("( |\t|\n)*")
    print(pos_re)
    dfa = DFA.from_re(pos_re)
    print(dfa.alphas)
    dfa.print()
    print(dfa.is_satisfied("\n"))
