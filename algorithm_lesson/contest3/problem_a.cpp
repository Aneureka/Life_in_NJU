#include <iostream>
#include <stdio.h>
#include <queue>
#include <algorithm>
#include <functional>
using namespace std;

int main() {

    priority_queue<int, vector<int>, less<int>> max_heap;
    priority_queue<int, vector<int>, greater<int>> min_heap;
    int median = 0;
    scanf("%d", &median);
    printf("%d ", median);
    int count = 1;

    int temp = 0;
    while (scanf("%d", &temp) != -1) {
        if (temp >= median) {
            min_heap.push(temp);
            if (count % 2 == 0) {
                int temp_median = min_heap.top();
                min_heap.pop();
                max_heap.push(median);
                median = temp_median;
            }
            printf("%d ", median);
            count++;
        }
        else {
            max_heap.push(temp);
            if (count % 2 == 1) {
                int temp_median = max_heap.top();
                max_heap.pop();
                min_heap.push(median);
                median = temp_median;
            }
            printf("%d ", median);
            count++;
        }
    }

}

