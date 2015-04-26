package pl.orak.tocv.CircleUtils;

import android.view.View;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleParams {

    public Point middle;

    public float radius;

    public CircleParams(Point middle, float radius) {
        this.middle = middle;
        this.radius = radius;
    }

    public CircleParams(View v) {
        float x = (v.getRight() - v.getLeft()) / 2;
        float y = (v.getBottom() - v.getTop()) / 2;
        middle = new Point(x, y);
        radius = v.getWidth() / 2;
    }

    public CircleParams(View v, int margin) {
        float x = (v.getRight() - v.getLeft()) / 2;
        float y = (v.getBottom() - v.getTop()) / 2;
        middle = new Point(x, y);
        radius = v.getWidth() / 2 - margin;
    }

    public boolean pointIn(Point point) {
        if (point == null) {
            return false;
        }
        float l = (float) (Math.pow((point.x - middle.x), 2) + Math.pow((point.y - middle.y), 2));
        float r = (float) Math.pow(radius, 2);
        return l <= r;
    }

}
