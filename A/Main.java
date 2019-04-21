import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in Actual solution is at the top
 *
 * @author MaxHeap
 */
public class Main {

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    InputReader in = new InputReader(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    HoudaAndLab solver = new HoudaAndLab();
    solver.solve(1, in, out);
    out.close();
  }

  static class HoudaAndLab {

    char[][] grid;
    int n;
    int m;
    int top;
    int bottom;
    DisjointSet ds;
    int[] dx = { 1, -1, 0, 0 };
    int[] dy = { 0, 0, -1, 1 };

    public void solve(int testNumber, InputReader in, PrintWriter out) {
      n = in.nextInt();
      m = in.nextInt();
      grid = new char[n][];
      for (int i = 0; i < n; ++i) {
        grid[i] = in.nextCharArray();
      }
      top = n * m + 1;
      bottom = n * m + 2;
      ds = new DisjointSet(bottom + 1);
      for (int i = 0; i < m; ++i) {
        if (grid[0][i] == '1') {
          ds.merge(i, top);
        }
      }

      for (int i = 0; i < m; ++i) {
        if (grid[n - 1][i] == '1') {
          ds.merge(getIndex(n - 1, i), bottom);
        }
      }

      int q = in.nextInt();
      while (q-- > 0) {
        int type = in.nextInt();
        if (type == 1) {
          int rootTop = ds.find(top);
          int rootBottom = ds.find(bottom);
          if (rootBottom == rootTop) {
            out.println("YES");
          } else {
            out.println("NO");
          }
        } else {
          int x = in.nextInt() - 1;
          int y = in.nextInt() - 1;
          link(x, y);
        }
      }
    }

    void link(int x, int y) {
      if (x == 0) {
        ds.merge(getIndex(x, y), top);
      }
      if (x == n - 1) {
        ds.merge(getIndex(x, y), bottom);
      }
      for (int i = 0; i < 4; ++i) {
        int xx = x + dx[i];
        int yy = y + dy[i];
        if (xx < 0 || yy < 0 || xx >= n || yy >= m) {
          continue;
        }
        if (grid[xx][yy] == '1') {
          ds.merge(getIndex(xx, yy), getIndex(x, y));
        }
      }
      grid[x][y] = '1';
    }

    int getIndex(int i, int j) {
      return i * m + j;
    }

  }

  static interface FastIO {

  }

  static class DisjointSet {

    int[] parent;
    int[] rank;
    int[] count;

    public DisjointSet(int[] parent, int[] rank) {
      this.parent = parent.clone();
      this.rank = rank.clone();

    }

    public DisjointSet clone() {
      return new DisjointSet(parent, rank);
    }

    public DisjointSet(int size) {
      parent = new int[size];
      for (int i = 0; i < size; i++) {
        parent[i] = i;
      }
      rank = new int[size];
      count = ArrayUtils.createArray(size, 1);
    }

    public int find(int x) {
      return x == parent[x] ? x : (parent[x] = find(parent[x]));
    }

    public void merge(int a, int b) {
      a = find(a);
      b = find(b);
      if (a == b) {
        return;
      }
      if (rank[a] < rank[b]) {
        parent[a] = b;
        count[b] += count[a];
      } else {
        parent[b] = a;
        count[a] += count[b];
        if (rank[a] == rank[b]) {
          ++rank[a];
        }
      }
    }

  }

  static class InputReader implements FastIO {

    private InputStream stream;
    private static final int DEFAULT_BUFFER_SIZE = 1 << 16;
    private static final int EOF = -1;
    private byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
    private int curChar;
    private int numChars;

    public InputReader(InputStream stream) {
      this.stream = stream;
    }

    public int read() {
      if (this.numChars == EOF) {
        throw new UnknownError();
      } else {
        if (this.curChar >= this.numChars) {
          this.curChar = 0;

          try {
            this.numChars = this.stream.read(this.buf);
          } catch (IOException ex) {
            throw new InputMismatchException();
          }

          if (this.numChars <= 0) {
            return EOF;
          }
        }

        return this.buf[this.curChar++];
      }
    }

    public int nextInt() {
      int c;
      for (c = this.read(); isSpaceChar(c); c = this.read()) {
      }

      byte sgn = 1;
      if (c == 45) {
        sgn = -1;
        c = this.read();
      }

      int res = 0;

      while (c >= 48 && c <= 57) {
        res *= 10;
        res += c - 48;
        c = this.read();
        if (isSpaceChar(c)) {
          return res * sgn;
        }
      }

      throw new InputMismatchException();
    }

    public String next() {
      int c;
      while (isSpaceChar(c = this.read())) {
      }

      StringBuilder result = new StringBuilder();
      result.appendCodePoint(c);

      while (!isSpaceChar(c = this.read())) {
        result.appendCodePoint(c);
      }

      return result.toString();
    }

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

    public char[] nextCharArray() {
      return next().toCharArray();
    }

  }

  static class ArrayUtils {

    public static int[] createArray(int count, int value) {
      int[] array = new int[count];
      Arrays.fill(array, value);
      return array;
    }

  }
}
