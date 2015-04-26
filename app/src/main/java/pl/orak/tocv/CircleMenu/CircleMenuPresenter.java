package pl.orak.tocv.CircleMenu;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.MenuTouchEvent;
import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuPresenter {

    CircleMenuView circleMenuView;
    private List<MyMenuItem> menuItems;

    public CircleMenuPresenter(CircleMenuView circleMenuView) {
        this.circleMenuView = circleMenuView;
        EventBus.getDefault().register(this);
    }

    public void setMenuItems(List<MyMenuItem> menuItems) {
        this.menuItems = menuItems;
        for (int i = 0; i < menuItems.size(); i++) {
            circleMenuView.addMenuItem(menuItems.get(i), getItemPosition(i));
        }
    }

    public Point getItemPosition(int menuItemAt) {
        CircleParams circleParams = circleMenuView.getCircleParams();
        float x = (float) (circleParams.middle.x + circleParams.radius * Math.sin((menuItemAt) * 2 * Math.PI / menuItems.size()));
        float y = (float) (circleParams.middle.y + circleParams.radius * Math.cos((menuItemAt) * 2 * Math.PI / menuItems.size()));
        x = Utils.round(x, 2);
        y = Utils.round(y, 2);
        return new Point(x, y);
    }

    public void onEvent(MenuTouchEvent event) {
        circleMenuView.updateMenuItemsPositions();
    }
}
