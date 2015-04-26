package pl.orak.tocv.CircleView;

import pl.orak.tocv.CircleUtils.CircleParams;

/**
 * Created by Tomek on 2015-04-23.
 */
public interface CircleView {

    void onPressedDown();

    void onPressedUp();

    CircleParams getCircleParams();

}
