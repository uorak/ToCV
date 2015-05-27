package pl.orak.tocv.CircleMenu;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.R;
import pl.orak.tocv.ToCvApp;
import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuViewImpl extends FrameLayout implements CircleMenuView {

    private static final int FLING_ANIMATION_DURATION = 800;
    private static final int CLICK_ANIMATION_DURATION = 800;
    private final TimeInterpolator FLING_INTERPOLATOR = new DecelerateInterpolator(2);
    private final TimeInterpolator CLICK_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    ArrayList<CircleMenuItemViewImpl> circleMenuItemViews = new ArrayList<>();
    HashMap<View, ViewPropertyAnimator> animatorViewHashMap = new HashMap<>();
    @Inject
    List<MyMenuItem> menuItems;
    private CircleMenuPresenter presenter;
    private CircleParams circleParams;
    private int selectedItemIndex;


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
        ToCvApp.inject(this);
        presenter = new CircleMenuPresenter(this);
        presenter.setOffset((int) getResources().getDimension(R.dimen.menu_item_size));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (circleMenuItemViews.isEmpty()) {
            presenter.setMenuItems(menuItems, selectedItemIndex);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public CircleParams getCircleParams() {
        if (circleParams == null) {
            circleParams = new CircleParams(this, (int) getResources().getDimension(R.dimen.menu_item_size) / 2);
        }
        return circleParams;
    }

    @Override
    public void addMenuItem(MyMenuItem menuItem, Point position) {
        CircleMenuItemViewImpl circleMenuItemView = new CircleMenuItemViewImpl(getContext(), menuItem);
        int menuItemSize = (int) getResources().getDimension(R.dimen.menu_item_size);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(menuItemSize, menuItemSize);
        layoutParams.leftMargin = (int) (position.x - menuItemSize / 2);
        layoutParams.topMargin = (int) (getHeight() - position.y - menuItemSize / 2);
        circleMenuItemView.setLayoutParams(layoutParams);
        addView(circleMenuItemView);
        circleMenuItemViews.add(circleMenuItemView);
    }

    @DebugLog
    @Override
    public void updateMenuItems(float angle, UpdateMenuItemsOption option) {
        rotateView(this, angle, option);
        for (CircleMenuItemViewImpl circleMenuItemView : circleMenuItemViews) {
            rotateView(circleMenuItemView, -angle, option);
        }
    }

    @Override
    public Utils.ScreenOrientation getScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return Utils.ScreenOrientation.Portrait;
        } else {
            return Utils.ScreenOrientation.Landscape;
        }
    }

    private void rotateView(View view, float angle, UpdateMenuItemsOption option) {
        if (option == UpdateMenuItemsOption.Fling) {
            rotateViewWithAnimation(view, angle, FLING_ANIMATION_DURATION, FLING_INTERPOLATOR);
        } else if (option == UpdateMenuItemsOption.Normal) {
            view.setRotation(-angle);
        } else if (option == UpdateMenuItemsOption.Click) {
            rotateViewWithAnimation(view, angle, CLICK_ANIMATION_DURATION, CLICK_INTERPOLATOR);
        }
    }

    private void rotateViewWithAnimation(View view, float angle, int duration, TimeInterpolator interpolator) {
        animatorViewHashMap.put(view, view.animate().setDuration(duration).setInterpolator(interpolator).rotationBy(angle));
        animatorViewHashMap.get(view).start();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Touch touch = Touch.fromMotionEvent(event);
        if (touch != null) {
            if (touch.getTouchMode() == Touch.TouchMode.DOWN) {
                stopAnimations();
            }
            presenter.onTouch(touch);
        }
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

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.selectedItemIndex = bundle.getInt("selectedItemIndex");
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        selectedItemIndex = presenter.getSelectedItemIndex();
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putInt("selectedItemIndex", this.selectedItemIndex);
        return bundle;
    }
}
