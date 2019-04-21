#include <bits/stdc++.h>

using namespace std;

int get(long long n, long long d) {
    int ans = 0;
    while (n >= d && n % d == 0) ++ans, n /= d;
    if (n > d) return ans;
    while (n >= 10 && n % 10 == 0) ++ans, n /= 10;
    return ans;
}

int main() {
    int t;
    cin>>t;
    while(t--)
    {
        long long n;
        cin>>n;
        if(n<3) cout<<-1<<endl;
        else if (n == 3) cout << 4 << endl;
        else {
            n-=3;
            vector<pair<int, long long>> ans;
            for(long long d=1;d*d<=n;d++)
            {
                if(n%d==0) {
                    if (d >= 4)
                        ans.push_back({ get(n, d), d });
                    if (n / d >= 4)
                        ans.push_back({ get(n, n / d), n / d });
                }
            }
            sort(ans.begin(), ans.end());
            if(ans.size()) cout<<ans[0].second<<endl;
            else cout<<-1<<endl;
        }
    }

    return 0;
}
