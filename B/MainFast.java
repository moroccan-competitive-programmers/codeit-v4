import java.util.*;
import java.io.*;

/**
 *
 * @author AEoui
 */
public class MainFast {
  static long INF = Long.MAX_VALUE;

  public static void main(String[] args) {
    InputStream inputStream = System.in;
    OutputStream outputStream = System.out;
    Kattio in = new Kattio(inputStream);
    PrintWriter out = new PrintWriter(outputStream);
    Task solver = new Task();
    solver.solve(1, in, out);
    out.close();
  }

  static class Task {
    public void solve(int testNumber, Kattio in, PrintWriter out) {
      int n = in.nextInt();
      Point[] points = new Point[n];
      for (int i = 0; i < n; ++i) {
        points[i] = new Point(in.nextInt(), in.nextInt());
      }
      long ans = solve(points);
      out.println(ans);
    }

    static long solve(Point[] points) {
      int min = 0;
      for (int i = 1; i < points.length; ++i) {
        if (points[i].y < points[min].y) {
          min = i;
        } else if (points[i].y == points[min].y && points[i].x < points[min].x) {
          min = i;
        }
      }

      final Point p0 = points[min];
      points[min] = swap(points[0], points[0] = points[min]);
      Arrays.sort(points, new Comparator<Point>() {
        @Override
        public int compare(Point p, Point q) {
          int o = orientation(p0, p, q);
          if (o == 0) {
            return p0.dist(q) >= p0.dist(p) ? -1 : 1;
          }

          return o < 0 ? -1 : 1;
        }
      });

      Point[] sPoints = new Point[points.length - 1];
      long minArea = Long.MIN_VALUE;
      for (int i = 1; i < points.length; ++i) {

        int idx = 0;
        for (int j = 0; j < points.length; ++j)
          if (j != i) {
            sPoints[idx++] = points[j];
          }

        int m = 1;
        for (int j = 1; j < sPoints.length; ++j) {
          while (j < sPoints.length - 1 && orientation(sPoints[0], sPoints[j], sPoints[j + 1]) == 0) {
            ++j;
          }
          sPoints[m++] = sPoints[j];
        }

        List<Point> hull = convexHullPart2(sPoints);

        long area = 0;
        for (int j = 0; j < hull.size(); ++j) {
          int k = (j + 1) % hull.size();
          area += (hull.get(j).x * hull.get(k).y);
          area -= (hull.get(j).y * hull.get(k).x);
        }
        minArea = Math.max(minArea, area);
      }

      // min Area without considering the points[0]
      int cur = 0;
      for (int i = 1; i < points.length; ++i) {
        sPoints[cur++] = points[i];
      }

      min = 0;
      for (int i = 1; i < sPoints.length; ++i) {
        if (sPoints[i].y < sPoints[min].y) {
          min = i;
        } else if (sPoints[i].y == sPoints[min].y && sPoints[i].x < sPoints[min].x) {
          min = i;
        }
      }

      Point pp0 = sPoints[min];
      sPoints[min] = swap(sPoints[0], sPoints[0] = sPoints[min]);
      Arrays.sort(sPoints, new Comparator<Point>() {
        @Override
        public int compare(Point p, Point q) {
          int o = orientation(pp0, p, q);
          if (o == 0) {
            return pp0.dist(q) >= pp0.dist(p) ? -1 : 1;
          }

          return o < 0 ? -1 : 1;
        }
      });
      List<Point> hull = convexHullPart2(sPoints);
      long area = 0;
      for (int j = 0; j < hull.size(); ++j) {
        int k = (j + 1) % hull.size();
        area += ((long) hull.get(j).x * (long) hull.get(k).y);
        area -= ((long) hull.get(j).y * (long) hull.get(k).x);
      }

      return -1 * Math.max(area, minArea);
    }

    static List<Point> convexHullPart2(Point[] points) {
      Point p0 = points[0];
      List<Point> hull = new ArrayList<>();
      int m = 1;
      for (int i = 1; i < points.length; ++i) {
        while (i < points.length - 1 && orientation(p0, points[i], points[i + 1]) == 0) {
          ++i;
        }

        points[m++] = points[i];
      }

      if (m < 3)
        return Collections.emptyList();
      Stack<Point> stk = new Stack<>();
      stk.push(points[0]);
      stk.push(points[1]);
      stk.push(points[2]);

      for (int i = 3; i < m; ++i) {
        while (!stk.isEmpty() && orientation(nextTop(stk), stk.peek(), points[i]) >= 0) {
          stk.pop();
        }
        stk.push(points[i]);
      }

      while (!stk.isEmpty()) {
        hull.add(stk.pop());
      }

      return hull;
    }

    static Point nextTop(Stack<Point> stk) {
      Point top = stk.pop();
      Point ntop = stk.peek();
      stk.push(top);
      return ntop;
    }

    static Point swap(Point p, Point q) {
      return p;
    }

    static int orientation(Point p, Point q, Point r) {
      long o = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
      if (o == 0)
        return 0;
      return o < 0 ? -1 : 1;
    }

  }

  static long p2(long x) {
    return x * x;
  }

  static class Point {
    long x, y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public long dist(Point p) {
      return p2(this.x - p.x) + p2(this.y - p.y);
    }

    @Override
    public String toString() {
      return this.x + " " + this.y;
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
