import java.util.*;
import java.io.*;

public class PalSubseqStrIterFaster {
  static final int N = (int) 1e3;
  static final int M = (int) 1e9 + 9;

  static int add(int a, int b) {
    return (a + b) % M;
  }

  static int mul(int a, int b) {
    return (int) ((a * (long) b) % M);
  }

  static int sub(int a, int b) {
    return (a - b + M) % M;
  }

  static int n;
  static int[][] dp;
  static int[][][] pre;
  static char[] s;

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in);

    n = io.getInt();
    s = io.getWord().toCharArray();
    dp = new int[2][n];
    pre = new int[26][2][n];

    for (int i = n - 1; i >= 0; i--) {
      int cidx = s[i] - 'a';

      Arrays.fill(dp[i & 1], 0);
      for (int k = 0; k < 26; k++) {
        Arrays.fill(pre[k][i & 1], 0);
      }

      dp[i & 1][i] = 1;
      for (int j = i + 1; j < n; j++) {
        int ans = dp[(i & 1) ^ 1][j];

        ans = add(1, ans);
        if (i + 1 <= j && s[i] == s[i + 1]) {
          ans = add(1, ans);
        }

        if (i + 2 <= j) {
          ans = add(ans, get(pre[cidx][(i & 1) ^ 1], i + 2, j));
        }

        dp[i & 1][j] = ans;
        pre[s[j] - 'a'][i & 1][j] = add(dp[i & 1][j - 1], 1);
      }

      for (int k = 0; k < 26; k++) {
        for (int j = 1; j < n; j++) {
          pre[k][i & 1][j] = add(pre[k][i & 1][j], pre[k][i & 1][j - 1]);
        }
      }
    }

    io.println(dp[0][n - 1]);
    io.close();
  }

  private static final int get(int[] pre, int st, int ed) {
    if (st == 0) {
      return pre[ed];
    }
    return sub(pre[ed], pre[st - 1]);
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
