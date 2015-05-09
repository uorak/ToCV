package pl.orak.tocv.CircleUtils;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Tomek on 2015-05-09.
 */
public class CircleUtilsTest {

    @Test
    public void testCalculateAngle() throws Exception {
        Point middle = new Point(0, 0);
        Point oldPoint = new Point(0, 1);
        Point newPoint = new Point(1, 0);
        assertThat(CircleUtils.calculateAngle(middle, oldPoint, newPoint), is(-90f));

        oldPoint = new Point(0, 1);
        newPoint = new Point(-1, 0);
        assertThat(CircleUtils.calculateAngle(middle, oldPoint, newPoint), is(90f));
    }

    @Test
    public void testRotatePoint() throws Exception {
        Point middle = new Point(0, 0);
        Point oldPoint = new Point(0, 1);
        Point result = CircleUtils.rotatePoint(middle, oldPoint, 90);
        assertTrue(result.equals(new Point(-1, 0)));

        oldPoint = new Point(0, 1);
        result = CircleUtils.rotatePoint(middle, oldPoint, -90);
        assertTrue(result.equals(new Point(1, 0)));

    }

}