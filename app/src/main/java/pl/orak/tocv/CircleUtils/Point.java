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

    @Override
    public boolean equals(Object o) {
        Point p = (Point)o;
        if(this.x == p.x && this.y==p.y){
            return true;
        }
        return false;
    }
}
