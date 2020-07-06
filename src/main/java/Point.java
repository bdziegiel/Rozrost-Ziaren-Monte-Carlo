
class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean isInsideRadius(Point p, int r) {
        return Math.abs(p.x - this.x) < r && Math.abs(p.y - this.y) < r;
    }
}
