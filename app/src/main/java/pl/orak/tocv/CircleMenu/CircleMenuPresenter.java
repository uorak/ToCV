package pl.orak.tocv.CircleMenu;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.CircleUtils;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.ToCvApp;
import pl.orak.tocv.Utils;

/**
 * Created by Tomek on 2015-04-26.
 */
public class CircleMenuPresenter {

    public static final int ANGLE_TRACKING_TIME = 100;
    CircleMenuView circleMenuView;
    @Inject
    EventBus eventBus;
    private List<MyMenuItem> menuItems;
    private Point lastPoint;
    private float initialRotation;
    private int offset;

    private ArrayList<AngleInTime> angleInTimeArrayList = new ArrayList<>();

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
        float x = (float) (circleParams.middle.x + circleParams.radius * Math.sin((menuItemAt) * 2 * Math.PI / menuItems.size() + Math.PI));
        float y = (float) (circleParams.middle.y + circleParams.radius * Math.cos((menuItemAt) * 2 * Math.PI / menuItems.size() + Math.PI));
        x = Utils.round(x, 2);
        y = Utils.round(y, 2);
        return new Point(x, y);
    }

    public void onTouch(Touch touch, float rotation) {
        if (lastPoint != null && touch.getTouchMode() == Touch.TouchMode.MOVE && circleMenuView.getCircleParams().pointIn(touch.getPoint(), offset)) {
            Point newPoint = CircleUtils.rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), -rotation);
            float angle = CircleUtils.calculateAngle(circleMenuView.getCircleParams().middle, lastPoint, newPoint);
            if (angle != 0) {
                circleMenuView.updateMenuItems(initialRotation + angle, CircleMenuView.UpdateMenuItemsOption.Normal);
                lastPoint = newPoint;
                initialRotation = initialRotation + angle;
                trackAngle(angle);
            }
        } else {
            lastPoint = null;
            checkFling();
        }
        if (touch.getTouchMode() == Touch.TouchMode.DOWN) {
            initialRotation = -rotation;
            lastPoint = CircleUtils.rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), -rotation);
        } else if (touch.getTouchMode() == Touch.TouchMode.UP) {
            lastPoint = null;
            checkFling();
        }

    }

    private void checkFling() {
        float angle = getFlingAngle();
        if (angle != 0) {
            circleMenuView.updateMenuItems(angle, CircleMenuView.UpdateMenuItemsOption.Fling);
        }
        angleInTimeArrayList.clear();
    }

    private void trackAngle(float angle) {
        if (angleInTimeArrayList.size() >= 10) {
            angleInTimeArrayList.remove(0);
        }
        angleInTimeArrayList.add(new AngleInTime(angle, System.currentTimeMillis()));
    }

    private float getFlingAngle() {
        float angle = 0;
        long timestamp = System.currentTimeMillis();
        if (angleInTimeArrayList.size() > 0) {
            for (int i = angleInTimeArrayList.size() - 1; i >= 0; i--) {
                if (timestamp - angleInTimeArrayList.get(i).timestamp < ANGLE_TRACKING_TIME) {
                    angle += angleInTimeArrayList.get(i).angle;
                } else {
                    break;
                }
            }

        }
        return -angle;
    }

    public void onEvent(MenuItemClickedEvent event) {
        float angle;
        if (event.screenOrientation == Utils.ScreenOrientation.Landscape) {
            angle = getAfterClickAngle(event.menuItem, 90);
        } else {
            angle = getAfterClickAngle(event.menuItem, 180);
        }
        circleMenuView.updateMenuItems(angle, CircleMenuView.UpdateMenuItemsOption.Click);
    }

    private float getAfterClickAngle(MyMenuItem menuItem, float stopAngle) {
        float alpha = 360 / menuItems.size();
        float rotation = circleMenuView.getRotation() % 360;
        float actualAngle = ((menuItems.indexOf(menuItem) * alpha - stopAngle) + rotation) % 360;
        if (actualAngle < 0) {
            actualAngle = 360 + actualAngle;
        }
        float angle = 0;
        if (actualAngle <= 180) {
            angle = 180 - actualAngle;
        } else {
            angle = -(actualAngle - 180);
        }
        return angle;
    }

    private class AngleInTime {
        public float angle;
        public long timestamp;

        public AngleInTime(float angle, long timestamp) {
            this.angle = angle;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "AngleInTime{" +
                    "angle=" + angle +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }

}
