import java.util.*;
import java.io.*;

public class PalTreePaths {
  private static final class Hash {
    private static final int N = (int) 1e5 + 5;
    private static final int b1 = 27;
    private static final int b2 = 29;
    private static final int m1 = (int) 1e9 + 21;
    private static final int m2 = (int) 1e9 + 87;
    private static final int[] p1 = new int[N];
    private static final int[] p2 = new int[N];
    private static final int[] ip1 = new int[N];
    private static final int[] ip2 = new int[N];

    private static final int add(int a, int b, int m) {
      return (a + b) % m;
    }

    private static final int mul(int a, int b, int m) {
      return (int) ((a * (long) b) % m);
    }

    private static final int sub(int a, int b, int m) {
      return (a - b + m) % m;
    }

    private static final int pow(int a, int b, int m) {
      if (b == 0) {
        return 1;
      }
      int t = pow(a, b >> 1, m);
      t = mul(t, t, m);
      return (b & 1) == 1 ? mul(t, a, m) : t;
    }

    private static final int div(int a, int b, int m) {
      return mul(a, pow(b, m - 2, m), m);
    }

    static {
      p1[0] = 1;
      p2[0] = 1;
      for (int i = 1; i < N; i++) {
        p1[i] = mul(b1, p1[i - 1], m1);
        p2[i] = mul(b2, p2[i - 1], m2);
      }

      ip1[N - 1] = div(1, p1[N - 1], m1);
      ip2[N - 1] = div(1, p2[N - 1], m2);
      for (int i = N - 2; i >= 0; i--) {
        ip1[i] = mul(b1, ip1[i + 1], m1);
        ip2[i] = mul(b2, ip2[i + 1], m2);
      }

      assert (ip1[0] == 1 && ip2[0] == 1);
      assert (mul(ip1[5], p1[5], m1) == 1 && mul(ip2[N - 10], p2[N - 10], m2) == 1);
    }

    int hash1, hash2, len;

    void add(int v) {
      hash1 = add(mul(hash1, b1, m1), v, m1);
      hash2 = add(mul(hash2, b2, m2), v, m2);
      ++len;
    }

    void add(Hash h) {
      hash1 = add(h.hash1, mul(hash1, p1[h.len], m1), m1);
      hash2 = add(h.hash2, mul(hash2, p2[h.len], m2), m2);
    }

    Hash getEquivalent(Hash prefix) {
      Hash h = new Hash();
      h.hash1 = mul(sub(hash1, prefix.hash1, m1), ip1[prefix.len], m1);
      h.hash2 = mul(sub(hash2, prefix.hash2, m2), ip2[prefix.len], m2);
      return h;
    }

    @Override
    public boolean equals(Object o) {
      if (o == null || !this.getClass().equals(o.getClass())) {
        return false;
      }
      Hash h = (Hash) o;
      return hash1 == h.hash1 && hash2 == h.hash2;
    }

    @Override
    public int hashCode() {
      return Long.hashCode((((long) hash1) << 32) | hash2);
    }
  }

  static int n;
  static long ans;
  static ArrayList<int[]>[] a;
  static HashMap<Hash, Integer>[] h;
  static int[] sz;
  static Hash[] add;
  static ArrayList<Hash> toAdd;

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in);

    n = io.getInt();
    sz = new int[n + 1];
    a = new ArrayList[n + 1];
    h = new HashMap[n + 1];
    add = new Hash[n + 1];
    for (int i = 0; i <= n; i++) {
      a[i] = new ArrayList<>();
      h[i] = new HashMap<>();
      add[i] = new Hash();
    }
    toAdd = new ArrayList<>();

    for (int i = 1; i < n; i++) {
      int u = io.getInt();
      int v = io.getInt();
      int c = io.getWord().charAt(0) - 'a' + 1;
      a[u].add(new int[] { v, c });
      a[v].add(new int[] { u, c });
    }

    dfsSize(1, 0);
    solve(1, 0);

    io.println(ans);
    io.close();
  }

  private static final int dfsSize(int u, int p) {
    sz[u] = 1;
    for (int[] e : a[u]) {
      if (e[0] != p) {
        sz[u] += dfsSize(e[0], u);
      }
    }
    return sz[u];
  }

  private static final void solve(int u, int p) {
    int big = 0, bigc = 0;
    for (int[] e : a[u]) {
      int v = e[0];
      if (v != p) {
        solve(v, u);
        if (sz[v] > sz[big]) {
          big = v;
          bigc = e[1];
        }
      }
    }

    if (big != 0) {
      h[u] = h[big];
      add[u] = add[big];
      add[u].add(bigc);

      Hash directEdge = new Hash();
      directEdge.add(bigc);
      directEdge = directEdge.getEquivalent(add[u]);
      h[u].put(directEdge, 1 + h[u].getOrDefault(directEdge, 0));
    }

    for (int[] e : a[u]) {
      int v = e[0], c = e[1];
      if (v != p && v != big) {
        Hash directEdge = new Hash();
        directEdge.add(c);
        directEdge = directEdge.getEquivalent(add[u]);
        ans += h[u].getOrDefault(directEdge, 0);
        directEdge.len = 1;
        toAdd.add(directEdge);

        for (Map.Entry<Hash, Integer> entry : h[v].entrySet()) { // get
          Hash vhash = entry.getKey();
          int val = entry.getValue();

          vhash.add(add[v]);
          vhash.add(c);
          vhash = vhash.getEquivalent(add[u]);
          vhash.len = val;
          toAdd.add(vhash);

          ans += h[u].getOrDefault(vhash, 0) * (long) val;
        }

        for (Hash vhash : toAdd) {
          h[u].put(vhash, vhash.len + h[u].getOrDefault(vhash, 0)); // not exactly this
        }
        toAdd.clear();
      }
    }
  }
}

class Kattio extends PrintWriter {
  public Kattio(InputStream i) {
    super(new BufferedOutputStream(System.out));
    r = new BufferedReader(new InputStreamReader(i));
  }

  public Kattio(InputStream i, OutputStream o) {
    super(new BufferedOutputStream(o));
    r = new BufferedReader(new InputStreamReader(i));
  }

  public boolean hasMoreTokens() {
    return peekToken() != null;
  }

  public int getInt() {
    return Integer.parseInt(nextToken());
  }

  public double getDouble() {
    return Double.parseDouble(nextToken());
  }

  public long getLong() {
    return Long.parseLong(nextToken());
  }

  public String getWord() {
    return nextToken();
  }

  private BufferedReader r;
  private String line;
  private StringTokenizer st;
  private String token;

  private String peekToken() {
    if (token == null)
      try {
        while (st == null || !st.hasMoreTokens()) {
          line = r.readLine();
          if (line == null)
            return null;
          st = new StringTokenizer(line);
        }
        token = st.nextToken();
      } catch (IOException e) {
      }
    return token;
  }

  private String nextToken() {
    String ans = peekToken();
    token = null;
    return ans;
  }
}
