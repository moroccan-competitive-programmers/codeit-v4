import java.util.*;
import java.io.*;

/**
 *
 * @author AEoui
 */
public class ProbableCake {
  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    Kattio in = new Kattio(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Task solver = new Task();
    int t = in.nextInt();
    for (int i = 1; i <= t; ++i)
      solver.solve(i, in, out);
    out.close();
  }

  static class Task {
    public void solve(int testNumber, Kattio in, PrintWriter out) {
      int n = in.nextInt();
      int m = in.nextInt();
      int a = in.nextInt();

      int total = 0;
      int count = 0;
      for (int i = 1; i < n; ++i) {
        for (int j = 1; j < m; ++j) {
          for (int ii = i + 1; ii < n; ++ii) {
            for (int jj = j + 1; jj < m; ++jj) {

              int[] arr = { 0, i, ii, n };
              int[] brr = { 0, j, jj, m };

              int flag = 0;
              for (int k = 1; k < brr.length && flag == 0; ++k) {
                for (int kk = 1; kk < arr.length; ++kk) {
                  int area = (arr[kk] - arr[kk - 1]) * (brr[k] - brr[k - 1]);
                  if (area >= a) {
                    flag = 1;
                    break;
                  }
                }
              }

              count += flag;
              ++total;
            }
          }
        }
      }

      out.printf("%.9f\n", (double) count / (double) total);
    }
  }

  static class Kattio extends PrintWriter {
    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    public Kattio(InputStream i) {
      super(new BufferedOutputStream(System.out));
      r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(InputStream i, OutputStream o) {
      super(new BufferedOutputStream(o));
      r = new BufferedReader(new InputStreamReader(i));
    }

    public int nextInt() {
      return Integer.parseInt(nextToken());
    }

    public String next() {
      return nextToken();
    }

    public long nextLong() {
      return Long.parseLong(nextToken());
    }

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
}
