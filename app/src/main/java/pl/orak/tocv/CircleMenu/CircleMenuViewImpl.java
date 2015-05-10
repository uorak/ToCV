package pl.orak.tocv.CircleMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.R;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuViewImpl extends FrameLayout implements CircleMenuView {

    private static final int FLING_ANIMATION_DURATION = 800;
    private final DecelerateInterpolator INTERPOLATOR = new DecelerateInterpolator(2);
    HashMap<MyMenuItem, CircleMenuItemViewImpl> circleMenuItemViewHashMap = new HashMap<>();
    HashMap<View, ViewPropertyAnimator> animatorViewHashMap = new HashMap<>();
    private CircleMenuPresenter presenter;
    private GestureDetector gestureDetector = new GestureDetector(getContext(), new MyGestureDetector());

    public CircleMenuViewImpl(Context context) {
        super(context);
        init();
    }

    public CircleMenuViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleMenuViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        presenter = new CircleMenuPresenter(this);
        presenter.setOffset((int) getResources().getDimension(R.dimen.menu_item_size));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (circleMenuItemViewHashMap.isEmpty()) {
            List<MyMenuItem> menuItems = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                MyMenuItem item = new MyMenuItem();
                menuItems.add(item);
            }
            presenter.setMenuItems(menuItems);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public CircleParams getCircleParams() {
        return new CircleParams(this, (int) getResources().getDimension(R.dimen.menu_item_size) / 2);
    }

    @Override
    public void addMenuItem(MyMenuItem menuItem, Point position) {
        CircleMenuItemViewImpl circleMenuItemView = new CircleMenuItemViewImpl(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.menu_item_size),
                (int) getResources().getDimension(R.dimen.menu_item_size));
        layoutParams.leftMargin = (int) (position.x - getResources().getDimension(R.dimen.menu_item_size) / 2);
        layoutParams.topMargin = (int) (getHeight() - position.y - getResources().getDimension(R.dimen.menu_item_size) / 2);
        circleMenuItemView.setLayoutParams(layoutParams);
        circleMenuItemView.setImageResource(R.drawable.fota);
        addView(circleMenuItemView);
        circleMenuItemViewHashMap.put(menuItem, circleMenuItemView);
        animatorViewHashMap.put(circleMenuItemView, circleMenuItemView.animate().setDuration(FLING_ANIMATION_DURATION).setInterpolator(INTERPOLATOR));
    }

    @Override
    public void updateMenuItems(float angle, boolean animate) {
        rotateView(this, angle, animate);
        Iterator it = circleMenuItemViewHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            CircleMenuItemViewImpl circleMenuItemView = (CircleMenuItemViewImpl) pair.getValue();
            rotateView(circleMenuItemView, -angle, animate);
        }
    }

    private void rotateView(View view, float angle, boolean animate) {
        if (animate) {
            animatorViewHashMap.put(view, view.animate().setDuration(FLING_ANIMATION_DURATION).setInterpolator(INTERPOLATOR).rotationBy(-angle));
            animatorViewHashMap.get(view).start();
        } else {
            view.setRotation(-angle);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Touch touch = Touch.fromMotionEvent(event);
        if (touch != null) {
            if (touch.getTouchMode() == Touch.TouchMode.DOWN) {
                stopAnimations();
            }
            presenter.onTouch(touch, getRotation());
        }
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    private void stopAnimations() {
        Iterator it = animatorViewHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ViewPropertyAnimator viewPropertyAnimator = (ViewPropertyAnimator) pair.getValue();
            viewPropertyAnimator.cancel();
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("mytest", "single tap");
            return super.onSingleTapConfirmed(e);
        }
    }
}
