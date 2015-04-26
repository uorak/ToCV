package pl.orak.tocv.CircleMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import de.greenrobot.event.EventBus;
import pl.orak.tocv.CircleView.CircleViewImpl;
import pl.orak.tocv.MenuTouchEvent;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuItemViewImpl extends CircleViewImpl {
    public CircleMenuItemViewImpl(Context context) {
        super(context);
    }

    public CircleMenuItemViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleMenuItemViewImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        EventBus.getDefault().post(new MenuTouchEvent());
        return super.onTouchEvent(event);
    }
}
