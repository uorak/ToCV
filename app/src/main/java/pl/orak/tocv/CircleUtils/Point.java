package pl.orak.tocv.CircleUtils;

/**
 * Created by Tomek on 2015-04-23.
 */
public class Point {

    public float x;

    public float y;

    public Point(float x, float y) {
        this.x=x;
        this.y=y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    @Override
    public boolean equals(Object o) {
        Point p = (Point)o;
        if(this.x == p.x && this.y==p.y){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
