package pl.orak.tocv.CircleView.utils;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleParams {

    Point middle;

    float radius;

    public CircleParams(Point middle, float radius) {
        this.middle=middle;
        this.radius=radius;
    }

    public boolean pointIn(Point point) {
        if(point==null){
            return false;
        }
        float l = (float) (Math.pow((point.x-middle.x),2)+ Math.pow((point.y-middle.y),2));
        float r = (float) Math.pow(radius,2);
        return l<=r;
    }
}
