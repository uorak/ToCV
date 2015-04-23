package pl.orak.tocv.CircleView.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.makeramen.roundedimageview.RoundedImageView;

import pl.orak.tocv.CircleView.CircleView;
import pl.orak.tocv.CircleView.CircleViewPresenter;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleViewImpl extends RoundedImageView implements CircleView {
    public static final int PRESS_ANIMATION_DURATION = 50;
    private CircleViewPresenter presenter;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            presenter.onTouch(new Touch(new Point(event.getX(), event.getY()), Touch.TouchMode.DOWN));
        }else if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL ){
            presenter.onTouch(new Touch( Touch.TouchMode.UP));
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            presenter.onTouch(new Touch(new Point(event.getX(), event.getY()), Touch.TouchMode.MOVE));
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
        float x = (getRight()-getLeft())/2;
        float y = (getBottom()-getTop())/2;
        float radius = getWidth()/2;
        return new CircleParams(new Point(x,y), radius);
    }
}
