package pl.orak.tocv.CircleMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
    private CircleMenuPresenter presenter;

    HashMap<MyMenuItem, CircleMenuItemViewImpl> circleMenuItemViewHashMap = new HashMap<>();

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
        if(circleMenuItemViewHashMap.isEmpty()) {
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
        return new CircleParams(this, (int) getResources().getDimension(R.dimen.menu_item_size)/2);
    }

    @Override
    public void addMenuItem(MyMenuItem menuItem, Point position) {
        CircleMenuItemViewImpl circleMenuItemView = new CircleMenuItemViewImpl(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.menu_item_size),
                (int) getResources().getDimension(R.dimen.menu_item_size));
        layoutParams.leftMargin = (int) (position.x-getResources().getDimension(R.dimen.menu_item_size)/2);
        layoutParams.topMargin = (int) (getHeight()-position.y-getResources().getDimension(R.dimen.menu_item_size)/2);
        circleMenuItemView.setLayoutParams(layoutParams);
        circleMenuItemView.setImageResource(R.drawable.fota);
        addView(circleMenuItemView);
        circleMenuItemViewHashMap.put(menuItem,circleMenuItemView);
    }

    @Override
    public void updateMenuItems(float vector) {
        setRotation(vector);
        Iterator it = circleMenuItemViewHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            CircleMenuItemViewImpl circleMenuItemView = (CircleMenuItemViewImpl) pair.getValue();
            circleMenuItemView.setRotation(-vector);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Touch touch = Touch.fromMotionEvent(event);
        if(touch!=null){
            presenter.onTouch(touch, getRotation());
        }
        return super.dispatchTouchEvent(event);
    }
}
