#include <algorithm>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

const int INF = 1000000000;

int x, y, x1, y1, x2, y2, dp[111][111];

int calc(int x, int y) {
	if (dp[x][y] != -1) return dp[x][y];
	if (x == x1 && y == y1) {
		dp[x][y] = 0;
		return dp[x][y];
	}
	if (x == x2 && y == y2) {
		dp[x][y] = 0;
		return dp[x][y];
	}
	if (x == y1 && y == x1) {
		dp[x][y] = 0;
		return dp[x][y];
	}
	if (x == y2 && y == x2) {
		dp[x][y] = 0;
		return dp[x][y];
	}
	if (x <= min(min(x1, x2), min(y1, y2)) && y <= min(min(x1, x2), min(y1, y2))) {
		dp[x][y] = x * y;
		return dp[x][y];
	}
	int ans = INF;
	for (int i = 1; i < x; ++i)
		ans = min(ans, calc(i, y) + calc(x - i, y));
	for (int i = 1; i < y; ++i)
		ans = min(ans, calc(x, i) + calc(x, y - i));
	dp[x][y] = ans;
	return dp[x][y];
}

int main() {
	freopen("input.txt", "r", stdin);
	freopen("output.txt", "w", stdout);
	cin >> x >> y >> x1 >> y1 >> x2 >> y2;
	for (int i = 1; i <= x; ++i) {
		for (int j = 1; j <= y; ++j) {
			dp[i][j] = -1;
		}
	}
	int ans = calc(x, y);
	cout << ans << endl;
	return 0;
}