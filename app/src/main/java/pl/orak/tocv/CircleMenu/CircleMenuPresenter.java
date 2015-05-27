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
    private List<MyMenuItem> menuItems = new ArrayList<>();
    private Point lastPoint;
    private float initialRotation;
    private int offset;

    private ArrayList<AngleInTime> angleInTimeArrayList = new ArrayList<>();
    private boolean flingProcessed;
    private int selectedItemIndex;

    public CircleMenuPresenter(CircleMenuView circleMenuView) {
        ToCvApp.inject(this);
        eventBus.register(this);
        this.circleMenuView = circleMenuView;
    }

    public int getSelectedItemIndex() {
        return selectedItemIndex;
    }

    public void setMenuItems(List<MyMenuItem> menuItems, int selectedItemIndex) {
        this.selectedItemIndex = selectedItemIndex;
        this.menuItems = menuItems;
        for (int i = 0; i < menuItems.size(); i++) {
            circleMenuView.addMenuItem(menuItems.get(i), getItemInitialPosition(i));
        }
        float angle = getAngleToStopPosition(menuItems.get(selectedItemIndex));
        if (angle != 0) {
            circleMenuView.updateMenuItems(angle, CircleMenuView.UpdateMenuItemsOption.Normal);
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Point getItemInitialPosition(int menuItemAt) {
        CircleParams circleParams = circleMenuView.getCircleParams();
        float x = (float) (circleParams.middle.x + circleParams.radius * Math.sin((menuItemAt) * 2 * Math.PI / menuItems.size() + Math.PI));
        float y = (float) (circleParams.middle.y + circleParams.radius * Math.cos((menuItemAt) * 2 * Math.PI / menuItems.size() + Math.PI));
        x = Utils.round(x, 4);
        y = Utils.round(y, 4);
        return new Point(x, y);
    }

    public void onTouch(Touch touch) {
        if (lastPoint != null && touch.getTouchMode() == Touch.TouchMode.MOVE && circleMenuView.getCircleParams().pointIn(touch.getPoint(), offset)) {
            Point newPoint = CircleUtils.rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), -circleMenuView.getRotation());
            float angle = CircleUtils.calculateAngle(circleMenuView.getCircleParams().middle, lastPoint, newPoint);
            if (angle != 0) {
                circleMenuView.updateMenuItems(initialRotation + angle, CircleMenuView.UpdateMenuItemsOption.Normal);
                lastPoint = newPoint;
                initialRotation = initialRotation + angle;
                trackAngle(angle);
            }
        } else if (touch.getTouchMode() == Touch.TouchMode.DOWN) {
            flingProcessed = false;
            initialRotation = -circleMenuView.getRotation();
            lastPoint = CircleUtils.rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), -circleMenuView.getRotation());
        } else if (lastPoint != null && (touch.getTouchMode() == Touch.TouchMode.UP || !circleMenuView.getCircleParams().pointIn(touch.getPoint(), offset))) {
            lastPoint = null;
            processFling();
        } else {
            lastPoint = null;
            angleInTimeArrayList.clear();
        }

    }

    private void processFling() {
        float angle = getFlingAngle();
        if (Math.abs(angle) > 4) {
            flingProcessed = true;
//        }
        float angleToClosestPosition = getClosestItemAngle(angle);
        angle += angleToClosestPosition;
//        if (Math.abs(angle) > 4) {
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
        if (!flingProcessed) {
            float angle = getAngleToStopPosition(event.menuItem);
            circleMenuView.updateMenuItems(angle, CircleMenuView.UpdateMenuItemsOption.Click);
            selectedItemIndex = menuItems.indexOf(event.menuItem);
        }
    }

    private float getAngleToStopPosition(MyMenuItem menuItem, float offsetAngle) {
        float alpha = 360f / menuItems.size();
        float rotation = circleMenuView.getRotation() + offsetAngle;
        float stopAngle = getStopAngle(circleMenuView.getScreenOrientation());
        float actualAngle = ((menuItems.indexOf(menuItem) * alpha - stopAngle) + rotation) % 360;
        if (actualAngle < 0) {
            actualAngle = 360 + actualAngle;
        }
        return 180 - actualAngle;
    }

    private float getAngleToStopPosition(MyMenuItem menuItem) {
        return getAngleToStopPosition(menuItem, 0);
    }

    public float getClosestItemAngle(float offsetAngle) {
        float angle = 360;
        for (MyMenuItem menuItem : menuItems) {
            float angleToStopPosition = getAngleToStopPosition(menuItem, offsetAngle);
            if (Math.abs(angleToStopPosition) < Math.abs(angle) && (offsetAngle == 0 || Math.signum(angleToStopPosition) == Math.signum(offsetAngle))) {
                angle = angleToStopPosition;
                selectedItemIndex = menuItems.indexOf(menuItem);
            }
        }
        return angle;
    }

    public float getClosestItemAngle() {
        return getClosestItemAngle(0);
    }

    private float getStopAngle(Utils.ScreenOrientation screenOrientation) {
        if (screenOrientation == Utils.ScreenOrientation.Landscape) {
            return 90;
        } else {
            return 180;
        }
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
