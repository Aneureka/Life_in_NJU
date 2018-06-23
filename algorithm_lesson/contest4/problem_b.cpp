#include <iostream>
#include <stdio.h>
#include <algorithm>
using namespace std;

int find_two_sum(const int nums[], int low, int high, int target);

int main() {

    int n = 0;
    int target = 0;
    cin >> n >> target;

    auto nums = new int[n];
    for (int i = 0; i < n; ++i) {
        scanf("%d", &nums[i]);
        nums[i] = nums[i]*3 - target;
    }

    if (n < 3) {
        cout << 0 << endl;
        return 0;
    }

    sort(&nums[0], &nums[n]);

    // find the division of not-less-than-zero
    int positive_index = n;
    for (int i = 0; i < n; ++i) {
        if (nums[i] >= 0) {
            positive_index = i;
            break;
        }
    }

    int ans = 0;
    for (int i = 0; i < n-2; ++i) {
        ans += find_two_sum(nums, i+1, n-1, 0-nums[i]);
    }

    cout << "wo yi yue du guan yu chao xi de shuo ming" << endl;
    cout << ans << endl;

}


// assume that the array is sorted
int find_two_sum(const int nums[], int low, int high, int target) {
    if (low >= high)
        return 0;
    int p = low, q = high;
    int count = 0;
    while (p < q) {
        int sum = nums[p] + nums[q];
        if (sum == target) {
            count++;
            p++;
            q--;
        }
        else if (sum < target) {
            p++;
        }
        else {
            q--;
        }
    }
    return count;
}