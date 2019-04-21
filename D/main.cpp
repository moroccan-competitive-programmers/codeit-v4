#include <bits/stdc++.h>

using namespace std;

int main()
{
  int t;
  cin >> t;
  while (t--)
  {
    long long n;
    cin >> n;
    if (n < 3)
      cout << -1 << endl;
    else if (n == 3)
      cout << 4 << endl;
    else
    {
      n -= 3;
      long long ans = -1;
      for (long long d = 2; d * d <= n; d++)
      {
        if (n % d == 0)
        {
          ans = d;
          break;
        }
      }
      if (ans != -1 && (n / ans > 3))
        cout << n / ans << endl;
      else
        cout << -1 << endl;
    }
  }

  return 0;
}
