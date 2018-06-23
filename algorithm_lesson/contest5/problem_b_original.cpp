#include <iostream>
#include <vector>
using namespace std;

struct Node {
    int time;
    int color;
    int est;
    int eft;
    vector<int> next;
};

void dfs(Node* nodes, Node& v);

int main() {

    int n = 0;
    cin >> n;
    Node* nodes = new Node[n+1];

    int number = 0, time = 0;
    for (int i = 1; i < n+1; ++i) {
        cin >> number >> time;
        nodes[i].time = time;
        nodes[i].color = 0;
    }

    int w = 0, v = 0;
    while (cin >> w >> v) {
        nodes[w].next.push_back(v);
    }

    cout << "I have read the rules about plagiarism punishment" << endl;
    for (int i = 1; i < n+1; ++i) {
        if (nodes[i].color == 0) {
            dfs(nodes, nodes[i]);
        }
    }

    int max_time = 0;
    for (int i = 1; i < n+1; ++i) {
        if (nodes[i].eft > max_time)
            max_time = nodes[i].eft;
    }


    delete[] nodes;
    cout << max_time << endl;
}

void dfs(Node* nodes, Node& v) {
    v.color = 1;
    v.est = 0;
    vector<int> next = v.next;
    for (int i = 0; i < next.size(); ++i) {
        Node w = nodes[next[i]];
        if (w.color == 0) {
            dfs(nodes, w);
            if (w.eft >= v.est) {
                v.est = w.eft;
            }
        }
        else {
            if (w.eft >= v.est) {
                v.est = w.eft;
            }
        }
    }
    v.eft = v.est + v.time;
    v.color = 2;
}

