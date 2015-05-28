package pl.orak.tocv.CircleUtils;

/**
 * Created by Tomek on 2015-05-27.
 */
public class AngleInTime {
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
