#include <iostream>
#include <math.h>
#include <stdio.h>
#include <vector>
using namespace std;

short get_palindrome_count(vector<char> seq) {

    short n = seq.size();
    short** count = new short*[n];
    for (short i = 0; i < n; ++i) {
        count[i] = new short[n];
    }
    for (short i = 0; i < n; ++i) {
        for (short j = 0; j < n; ++j) {
            count[i][j] = 0;
        }
    }
    for (short i = 0; i < n; ++i) {
        count[i][i] = 1;
    }

    for (short l = 1; l < n; ++l) {
        for (short i = 0; i < n-l; ++i) {
            short low = i;
            short high = i+l;
            // the first & last char may be counted in or not
            count[low][high] = max(count[low][high-1], count[low+1][high]);
            if (seq[low] == seq[high]) {
                count[low][high] = max(count[low][high], (short)(2+count[low+1][high-1]));
            }
        }
    }

    short c = count[0][n-1];
    delete[] count;

    return c;
}


int main() {

    vector<char> seq;

    char c;
    while (scanf("%c ", &c) != EOF) {
        seq.push_back(c);
    }

    cout << "I have read the rules about plagiarism punishment" << endl;
    cout << get_palindrome_count(seq) << endl;

}
