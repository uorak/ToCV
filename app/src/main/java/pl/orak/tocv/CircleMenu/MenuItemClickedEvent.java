package pl.orak.tocv.CircleMenu;

import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-05-10.
 */
public class MenuItemClickedEvent {

    public Utils.ScreenOrientation screenOrientation;
    MyMenuItem menuItem;

    public MenuItemClickedEvent(MyMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public MenuItemClickedEvent(MyMenuItem menuItem, Utils.ScreenOrientation screenOrientation) {
        this.menuItem = menuItem;
        this.screenOrientation = screenOrientation;
    }
}
