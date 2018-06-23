#include <iostream>
#include <stdio.h>
#include <sstream>
#include <vector>
#include <math.h>

#define WHITE 0
#define BLACK 1

using namespace std;

int min_cover_recursive(const vector<vector<int> >& tree, int cur_node, int color) {

    if (tree[cur_node].empty()) {
        return color == BLACK ? 1 : 0;
    }

    int res_white = 0, res_black = 1;

    vector<int> children = tree[cur_node];

    for (int i = 0; i < children.size(); ++i) {
        int cur_child = children[i];
        res_black += min_cover_recursive(tree, cur_child, WHITE);
    }

    if (color == BLACK)
        return res_black;

    for (int i = 0; i < children.size(); ++i) {
        int cur_child = children[i];
        res_white += min_cover_recursive(tree, cur_child, BLACK);
    }

    return min(res_white, res_black);

}

int min_cover_dp(const vector<vector<int> >& tree) {
    int n = tree.size();
    int* black_count = new int[n];
    int* white_count = new int[n];

    for (int i = n-1; i >= 0; --i) {
        vector<int> children = tree[i];
        if (children.empty()) {
            black_count[i] = 1;
            white_count[i] = 0;
            continue;
        }

        int bc = 1, wc = 0;
        for (int j = 0; j < children.size(); ++j) {
            int child = children[j];
            bc += min(black_count[child], white_count[child]);
            wc += black_count[child];
        }
        black_count[i] = bc;
        white_count[i] = wc;
    }

    int res = min(black_count[0], white_count[0]);
    delete[] black_count;
    delete[] white_count;
    return res;
}


int main() {

    vector<vector<int> > tree;
    string line;
    while (getline(cin, line)) {
        stringstream ss;
        ss << line;
        vector<int> t;
        int tmp;
        ss >> tmp;
        while (ss >> tmp) {
            t.push_back(tmp);
        }
        tree.push_back(t);
    }

    cout << "I have read the rules about plagiarism punishment" << endl;
    cout << min_cover_dp(tree) << endl;

}

