#include <iostream>
#include <unordered_map>
#include <vector>
#include <algorithm>
#include <string>
using namespace std;

struct WordNCount {
    string word;
    int count;
    WordNCount(string w, int c) {
        word = w;
        count = c;
    }
    WordNCount() {}
};


int main() {

    int n = 0;
    cin >> n;
    auto words = new string[n];
    for (int i = 0; i < n; ++i) {
        cin >> words[i];
    }

    unordered_map<string, WordNCount> word_map;
    for (int i = 0; i < n; ++i) {
        string str = words[i];
        string temp_str = string(str);
        sort(temp_str.begin(), temp_str.end());
        unordered_map<string, WordNCount>::iterator itr;
        itr = word_map.find(temp_str);
        if (itr == word_map.end()) {
            word_map.insert(pair<string, WordNCount>(temp_str, WordNCount(str, 1)));
        }
        else {
            WordNCount wc = word_map.at(temp_str);
            wc.count += 1;
            string origin_word = wc.word;
            if (str.compare(origin_word) < 0) {
                wc.word = str;
            }
            word_map[temp_str] = wc;
        }

    }

    vector<string> ans;
    unordered_map<string, WordNCount>::iterator itr;
    for (itr = word_map.begin(); itr != word_map.end(); itr++) {
        WordNCount wc = itr->second;
        string word = wc.word;
        int count = wc.count;
        if (count > 1) {
            ans.push_back(word);
        }
    }

    cout << "wo yi yue du guan yu chao xi de shuo ming" << endl;
    sort(ans.begin(), ans.end());
    cout << ans.size() << endl;
    vector<string>::iterat or ans_itr;
    for (ans_itr = ans.begin(); ans_itr != ans.end(); ans_itr++) {
        cout << *ans_itr << endl;
    }
}