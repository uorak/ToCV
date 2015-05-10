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
//        eventBus.register(this);
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

    public void onTouch(Touch touch, float rotation) {
        if (lastPoint != null && touch.getTouchMode() == Touch.TouchMode.MOVE && circleMenuView.getCircleParams().pointIn(touch.getPoint(), offset)) {
            Point newPoint = CircleUtils.rotatePoint(circleMenuView.getCircleParams().middle, touch.getPoint(), -rotation);
            float angle = CircleUtils.calculateAngle(circleMenuView.getCircleParams().middle, lastPoint, newPoint);
            if (angle != 0) {
                circleMenuView.updateMenuItems(initialRotation + angle, false);
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
        AngleInTime flingAngle = getFlingAngle();
        if (flingAngle.angle != 0) {
            circleMenuView.updateMenuItems(flingAngle.angle, true);
        }
        angleInTimeArrayList.clear();
    }

    private void trackAngle(float angle) {
        if (angleInTimeArrayList.size() >= 10) {
            angleInTimeArrayList.remove(0);
        }
        angleInTimeArrayList.add(new AngleInTime(angle, System.currentTimeMillis()));
    }

    private AngleInTime getFlingAngle() {
        float angle = 0;
        int animationSpeed = 0;
        long timestamp = System.currentTimeMillis();
        if (angleInTimeArrayList.size() > 0) {
            for (int i = angleInTimeArrayList.size() - 1; i >= 0; i--) {
                if (timestamp - angleInTimeArrayList.get(i).timestamp < 100) {
                    angle += angleInTimeArrayList.get(i).angle;
                } else {
                    break;
                }
            }

        }
        return new AngleInTime(angle, animationSpeed);
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
