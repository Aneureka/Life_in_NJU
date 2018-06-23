#include <iostream>
using namespace std;

void k_sort(int nums[], int low, int high, const int item_size);
int partition(int nums[], int low, int high);
int divide(int nums[], int low, int high);

int main() {
    int nums[] = {5, 1, 2, 5, 2, 1, 6, 7, 11, 44, 21, 52, 1000, 22, 4, 14};
    int k = 4;
    int size = 16 / k;
    k_sort(nums, 0, 15, size);

    for (int i = 0; i < 16; ++i) {
        cout << nums[i] << " ";
    }
    return 0;
}

void k_sort(int nums[], int low, int high, const int item_size) {
    if (high-low+1 <= item_size)
        return;
    int mid = divide(nums, low, high);
    divide(nums, low, mid);
    divide(nums, mid+1, high);
}

int divide(int nums[], int low, int high) {
    int mid = (low + high) / 2;
    int p = low, q = high;
    int ptr = partition(nums, p, q);
    while (ptr != mid &&  p <= q) {
        if (ptr < mid) {
            p = ptr + 1;
        }
        else {
            q = ptr - 1;
        }
        ptr = partition(nums, p, q);
    }
    return mid;
}


int partition(int nums[], int low, int high) {
    int pivot = nums[low];
    int p = low, q = high + 1;
    while (true) {
        while (nums[++p] <= pivot)
            if (p == high)
                break;
        while (nums[--q] >= pivot)
            if (q == low)
                break;
        if (p >= q)
            break;
        swap(nums[p], nums[q]);
    }

    swap(nums[low], nums[q]);
    return q;
}