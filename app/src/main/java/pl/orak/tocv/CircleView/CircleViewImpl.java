package pl.orak.tocv.CircleView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.makeramen.roundedimageview.RoundedImageView;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Touch;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleViewImpl extends RoundedImageView implements CircleView {
    public static final int PRESS_ANIMATION_DURATION = 50;
    private CircleViewPresenter presenter;
    private CircleParams circleParams;

    public CircleViewImpl(Context context) {
        super(context);
        init();
    }

    public CircleViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleViewImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        presenter = new CircleViewPresenter(this);
        setOval(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Touch touch = Touch.fromMotionEvent(event);
        if(touch!=null){
            presenter.onTouch(touch);
        }
        return true;
    }

    @Override
    public void onPressedDown() {
        Log.d("mytest","down");
        animate().scaleX(0.8f).setDuration(PRESS_ANIMATION_DURATION).start();
        animate().scaleY(0.8f).setDuration(PRESS_ANIMATION_DURATION).start();
    }

    @Override
    public void onPressedUp() {
        Log.d("mytest","up");
        animate().scaleX(1f).setDuration(PRESS_ANIMATION_DURATION).start();
        animate().scaleY(1f).setDuration(PRESS_ANIMATION_DURATION).start();
    }

    @Override
    public CircleParams getCircleParams() {
        if (circleParams==null){
            circleParams= new CircleParams(this);
        }
        return circleParams;
    }

}
