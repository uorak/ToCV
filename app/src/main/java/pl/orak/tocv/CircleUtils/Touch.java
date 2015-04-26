package pl.orak.tocv.CircleUtils;

/**
 * Created by Tomek on 2015-04-23.
 */
public class Touch {


    private Point point;
    private TouchMode touchMode;

    public Touch(Point point, TouchMode touchMode) {
        this.point=point;
        this.touchMode=touchMode;
    }

    public Touch(TouchMode touchMode) {
        this.touchMode=touchMode;
    }

    public Point getPoint() {
        return point;
    }

    public TouchMode getTouchMode() {
        return touchMode;
    }

    public enum TouchMode {DOWN, MOVE, UP}
}
