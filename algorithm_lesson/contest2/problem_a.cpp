#include <iostream>
using namespace std;

int main() {

    int max, temp, cur;
    cin >> temp;
    max = temp;

    while (cin >> cur) {
        if (temp < 0) {
            temp = cur;
        }
        else {
            temp += cur;
        }
        if (temp > max) {
            max = temp;
        }
    }
    cout << max;
}