package pl.orak.tocv.CircleUtils;

import android.view.MotionEvent;

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

    public static Touch fromMotionEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            return (new Touch(new Point(event.getX(), event.getY()), Touch.TouchMode.DOWN));
        }else if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL ){
            return (new Touch( Touch.TouchMode.UP));
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            return (new Touch(new Point(event.getX(), event.getY()), Touch.TouchMode.MOVE));
        }
        return null;
    }
}
