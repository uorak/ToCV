package pl.orak.tocv.CircleView;

import pl.orak.tocv.CircleView.utils.Touch;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleViewPresenter {

    CircleView circleView;

    private boolean touchedDown;

    public CircleViewPresenter(CircleView circleView) {
        this.circleView = circleView;
    }

    public void onTouch(Touch touch) {
        if (!touchedDown && (touch.getTouchMode()== Touch.TouchMode.DOWN || touch.getTouchMode()== Touch.TouchMode.MOVE) && circleView.getCircleParams().pointIn(touch.getPoint()) ) {
            circleView.onPressedDown();
            touchedDown = true;
        }
        else if(touchedDown && (touch.getTouchMode()== Touch.TouchMode.UP || (touch.getTouchMode()== Touch.TouchMode.MOVE && !circleView.getCircleParams().pointIn(touch.getPoint())))){
            circleView.onPressedUp();
            touchedDown = false;
        }

    }
}
