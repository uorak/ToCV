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

        float angle = -(float) Math.toDegrees(diff);
        if (angle < -180) {
            angle = angle + 360;
        }
        if (angle > 180) {
            angle = angle - 360;
        }

        return angle;
    }

    public static Point rotatePoint(Point middle, Point point, float angle) {

//        angle = -angle;
        Point newPoint = new Point(point);

        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));

        // translate point back to origin:
        newPoint.x -= middle.x;
        newPoint.y -= middle.y;

        // rotate point
        double xnew = newPoint.x * c + newPoint.y * s;
        double ynew = -newPoint.x * s + newPoint.y * c;

        // translate point back:
        newPoint.x = Utils.round((float) (xnew + middle.x), 3);
        newPoint.y = Utils.round((float) (ynew + middle.y), 3);
        return newPoint;
    }

}
