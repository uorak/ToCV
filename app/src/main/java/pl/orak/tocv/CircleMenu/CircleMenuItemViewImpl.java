package pl.orak.tocv.CircleMenu;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

import pl.orak.tocv.CircleView.CircleViewImpl;
import pl.orak.tocv.R;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuItemViewImpl extends CircleViewImpl {

    MyMenuItem menuItem;

    private GestureDetector gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());

    public CircleMenuItemViewImpl(Context context, MyMenuItem myMenuItem) {
        super(context);
        this.menuItem = myMenuItem;
        setImageResource(R.drawable.fota);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            eventBus.post(new MenuItemClickedEvent(menuItem));
            return super.onSingleTapConfirmed(e);
        }
    }


}
