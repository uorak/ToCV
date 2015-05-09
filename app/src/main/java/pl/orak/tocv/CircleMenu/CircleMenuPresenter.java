package pl.orak.tocv.CircleMenu;

import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.MenuItemMoveEvent;
import pl.orak.tocv.ToCvApp;
import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuPresenter {

    CircleMenuView circleMenuView;
    private List<MyMenuItem> menuItems;

    @Inject
    EventBus eventBus;
    private Point lastPoint;
    private float initialRotation;
    private int offset;

    public CircleMenuPresenter(CircleMenuView circleMenuView) {
        ToCvApp.inject(this);
        eventBus.register(this);
        this.circleMenuView = circleMenuView;
    }

    public void setMenuItems(List<MyMenuItem> menuItems) {
        this.menuItems = menuItems;
        for (int i = 0; i < menuItems.size(); i++) {
            circleMenuView.addMenuItem(menuItems.get(i), getItemPosition(i));
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }


    public Point getItemPosition(int menuItemAt) {
        CircleParams circleParams = circleMenuView.getCircleParams();
        float x = (float) (circleParams.middle.x + circleParams.radius * Math.sin((menuItemAt) * 2 * Math.PI / menuItems.size()));
        float y = (float) (circleParams.middle.y + circleParams.radius * Math.cos((menuItemAt) * 2 * Math.PI / menuItems.size()));
        x = Utils.round(x, 2);
        y = Utils.round(y, 2);
        return new Point(x, y);
    }


    public void onEvent(MenuItemMoveEvent event) {
        circleMenuView.updateMenuItems(event.vector);

    }


    public void onTouch(Touch touch, float rotation) {
        if (lastPoint != null && touch.getTouchMode() == Touch.TouchMode.MOVE && circleMenuView.getCircleParams().pointIn(touch.getPoint(), offset)) {
            Point newPoint = rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), rotation);
            float angle = calculateAngle(circleMenuView.getCircleParams().middle, lastPoint, newPoint);
            if(angle!=0) {
                circleMenuView.updateMenuItems(initialRotation + angle);
            }
        } else {
            lastPoint = null;
        }
        if (touch.getTouchMode() == Touch.TouchMode.DOWN) {
            initialRotation = rotation;
            lastPoint = rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), rotation);
        } else if (touch.getTouchMode() == Touch.TouchMode.UP) {
            lastPoint = null;
        }

    }

    public float calculateAngle(Point middle, Point oldPoint, Point newPoint) {
        float dx = newPoint.x - middle.x;
        float dy = newPoint.y - middle.y;
        double a = Math.atan2(dy, dx);

        float dpx = oldPoint.x - middle.x;
        float dpy = oldPoint.y - middle.y;
        double b = Math.atan2(dpy, dpx);

        double diff = a - b;

        return (float) Math.toDegrees(diff);
    }

    public Point rotatePoint(Point middle, Point point, float angle) {

        double s = Math.sin(Math.toRadians(angle));
        double c = Math.cos(Math.toRadians(angle));

        // translate point back to origin:
        point.x -= middle.x;
        point.y -= middle.y;

        // rotate point
        double xnew = point.x * c - point.y * s;
        double ynew = point.x * s + point.y * c;

        // translate point back:
        point.x = Utils.round((float) (xnew + middle.x), 3);
        point.y = Utils.round((float) (ynew + middle.y), 3);
        return point;
    }
}
