package pl.orak.tocv.CircleView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.makeramen.roundedimageview.RoundedImageView;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.R;
import pl.orak.tocv.ToCvApp;

/**
 * Created by Tomek on 2015-04-23.
 */
public class CircleViewImpl extends RoundedImageView implements CircleView {
    public static final int PRESS_ANIMATION_DURATION = 50;
    public static final float SCALE_ANIMATION_FACTOR = 0.8f;
    @Inject
    protected EventBus eventBus;
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
        ToCvApp.inject(this);
        presenter = new CircleViewPresenter(this);
        setOval(true);
        setBackgroundResource(R.drawable.circular_shadow);
        int padding = (int) getContext().getResources().getDimension(R.dimen.shadow_padding);
        setPadding(padding, padding, padding, padding);
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
        animate().scaleX(SCALE_ANIMATION_FACTOR).setDuration(PRESS_ANIMATION_DURATION).start();
        animate().scaleY(SCALE_ANIMATION_FACTOR).setDuration(PRESS_ANIMATION_DURATION).start();
    }

    @Override
    public void onPressedUp() {
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
