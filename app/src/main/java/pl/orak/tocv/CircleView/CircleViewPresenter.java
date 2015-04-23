package pl.orak.tocv.CircleView;

import pl.orak.tocv.CircleView.utils.CircleParams;
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
        CircleParams circleParams = circleView.getCircleParams();
        if (!touchedDown && circleParams.pointIn(touch.getPoint()) ) {
            circleView.onPressedDown();
        }
        if(touchedDown && touch.getTouchMode()== Touch.TouchMode.UP){
            circleView.onPressedUp();
        }
        touchedDown = touch.getTouchMode() == Touch.TouchMode.DOWN;
    }
}
