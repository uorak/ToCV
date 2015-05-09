package pl.orak.tocv.CircleUtils;

import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-05-09.
 */
public class CircleUtils {

    public static float calculateAngle(Point middle, Point oldPoint, Point newPoint) {
        float dx = newPoint.x - middle.x;
        float dy = newPoint.y - middle.y;
        double a = Math.atan2(dy, dx);

        float dpx = oldPoint.x - middle.x;
        float dpy = oldPoint.y - middle.y;
        double b = Math.atan2(dpy, dpx);

        double diff = a - b;

        return (float) Math.toDegrees(diff);
    }

    public static  Point rotatePoint(Point middle, Point point, float angle) {

        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));

        // translate point back to origin:
        point.x -= middle.x;
        point.y -= middle.y;

        // rotate point
        double xnew = point.x * c - point.y * s;
        double ynew = point.x * s + point.y * c;

        // translate point back:
        point.x = Utils.round((float) (xnew + middle.x), 3);
        point.y = Utils.round((float) (ynew + middle.y), 3);
        return point;
    }

}
