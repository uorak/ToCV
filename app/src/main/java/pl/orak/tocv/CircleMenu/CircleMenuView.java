package pl.orak.tocv.CircleMenu;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-04-26.
 */
public interface CircleMenuView {

    CircleParams getCircleParams();

    void addMenuItem(MyMenuItem menuItem, Point position);

    void updateMenuItems(float angle, UpdateMenuItemsOption option);

    float getRotation();

    Utils.ScreenOrientation getScreenOrientation();

    public enum UpdateMenuItemsOption {Normal, Fling, Initial, Click}
}
