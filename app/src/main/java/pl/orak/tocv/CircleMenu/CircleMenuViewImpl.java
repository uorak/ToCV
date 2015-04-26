package pl.orak.tocv.CircleMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleView.CircleViewImpl;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.R;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuViewImpl extends FrameLayout implements CircleMenuView {
    private CircleMenuPresenter presenter;

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
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        List<MyMenuItem> menuItems = new ArrayList<>();
        MyMenuItem item1 = new MyMenuItem();
        menuItems.add(item1);
        MyMenuItem item2 = new MyMenuItem();
        menuItems.add(item2);
        MyMenuItem item3 = new MyMenuItem();
        menuItems.add(item3);
        presenter.setMenuItems(menuItems);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public CircleParams getCircleParams() {
        return new CircleParams(this, (int) getResources().getDimension(R.dimen.menu_item_size)/2);
    }

    @Override
    public void addMenuItem(MyMenuItem menuItem, Point position) {
        CircleViewImpl circleView = new CircleViewImpl(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.menu_item_size),
                (int) getResources().getDimension(R.dimen.menu_item_size));
        layoutParams.leftMargin = (int) (position.x-getResources().getDimension(R.dimen.menu_item_size)/2);
        layoutParams.topMargin = (int) (getHeight()-position.y-getResources().getDimension(R.dimen.menu_item_size)/2);
        circleView.setLayoutParams(layoutParams);
        circleView.setImageResource(R.drawable.fota);
        addView(circleView);
    }
}
