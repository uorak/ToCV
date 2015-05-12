package pl.orak.tocv.CircleMenu;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;

/**
 * Created by Tomek on 2015-04-26.
 */
public interface CircleMenuView {

    public enum UpdateMenuItemsOption {Normal, Fling, Click}

    CircleParams getCircleParams();

    void addMenuItem(MyMenuItem menuItem, Point position);

    void updateMenuItems(float angle, UpdateMenuItemsOption option);

    public enum UpdateMenuItemsOption {Normal, Fling, Click}

    float getRotation();
}
