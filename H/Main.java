import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.OutputStream;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Writer;
import java.io.UnsupportedEncodingException;
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
    OutputWriter out = new OutputWriter(outputStream);
    HoudaKindergarten solver = new HoudaKindergarten();
    solver.solve(1, in, out);
    out.close();
  }

  static class HoudaKindergarten {

    int n;
    int[] arr;
    final long MODULO = (int) (1e9 + 7);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
      n = in.nextInt();
      arr = in.nextIntArray(n);
      ArrayUtils.countingSort(arr);
      long[] twoPow = new long[n + 1];
      twoPow[0] = 1;
      for (int i = 1; i <= n; ++i) {
        twoPow[i] = (twoPow[i - 1] << 1L) % MODULO;
      }
      long ans = 0;
      for (int i = 0; i < n; ++i) {
        ans += arr[i] * twoPow[i] - arr[i] * twoPow[n - i - 1];
        ans %= MODULO;
      }
      out.println(ans);
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

    public static boolean isSpaceChar(int c) {
      return c == 32 || c == 10 || c == 13 || c == 9 || c == EOF;
    }

    public int[] nextIntArray(int n) {
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = nextInt();
      }
      return arr;
    }

  }

  static class OutputWriter extends PrintWriter {

    public OutputWriter(OutputStream os, boolean autoFlush) {
      super(os, autoFlush);
    }

    public OutputWriter(Writer out) {
      super(out);
    }

    public OutputWriter(Writer out, boolean autoFlush) {
      super(out, autoFlush);
    }

    public OutputWriter(String fileName) throws FileNotFoundException {
      super(fileName);
    }

    public OutputWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
      super(fileName, csn);
    }

    public OutputWriter(File file) throws FileNotFoundException {
      super(file);
    }

    public OutputWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
      super(file, csn);
    }

    public OutputWriter(OutputStream out) {
      super(out);
    }

    public void flush() {
      super.flush();
    }

    public void close() {
      super.close();
    }

  }

  static interface FastIO {

  }

  static class ArrayUtils {

    public static void countingSort(int[] a) {
      int max = 0;
      for (int x : a) {
        max = Math.max(max, x);
      }
      int[] cnt = new int[max + 1];
      for (int x : a) {
        ++cnt[x];
      }
      for (int i = 1; i < cnt.length; i++) {
        cnt[i] += cnt[i - 1];
      }
      int n = a.length;
      int[] b = new int[n];
      for (int i = 0; i < n; i++) {
        b[--cnt[a[i]]] = a[i];
      }
      System.arraycopy(b, 0, a, 0, n);
    }

  }
}
