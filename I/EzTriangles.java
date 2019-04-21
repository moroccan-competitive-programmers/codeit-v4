import java.util.*;
import java.io.*;

public class EzTriangles {
  public static void main(String[] args) {
    Kattio io = new Kattio(System.in);

    int t = io.getInt();
    while (t-- > 0) {
      int x1 = io.getInt();
      int y1 = io.getInt();
      int x2 = io.getInt();
      int y2 = io.getInt();

      x1 -= x2;
      y1 -= y2;

      double[] c = get(x1, y1);
      for (int i = 0; i < 4; i++) {
        c[i] += i % 2 == 0 ? x2 : y2;
      }

      if (c[0] < c[2] || (c[0] == c[2] && c[1] > c[3])) {
        System.out.printf("%.11f %.11f%n", c[0], c[1]);
      } else {
        System.out.printf("%.11f %.11f%n", c[2], c[3]);
      }
    }

    io.close();
  }

  private static final double ca = Math.cos(Math.PI / 3);
  private static final double sa = Math.sin(Math.PI / 3);

  private static final double[] get(int x, int y) {
    return new double[] { ca * x - sa * y, sa * x + ca * y, ca * x + sa * y, -sa * x + ca * y };
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
