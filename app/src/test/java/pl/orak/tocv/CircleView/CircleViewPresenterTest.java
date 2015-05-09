package pl.orak.tocv.CircleView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CircleViewPresenterTest {

    private CircleViewPresenter circleViewPresenter;

    @Mock
    CircleView circleView;

    private int touchCounter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(circleView.getCircleParams()).thenReturn(new CircleParams(new Point(0, 0), 1f));
        circleViewPresenter = new CircleViewPresenter(circleView);
        touchCounter = 0;
    }

    @Test
    public void testOnTouchIn() throws Exception {
        touchDownAndUp(0, 0);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(1, 0);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(0, 1);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(-1, 0);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(0.1f, 0.1f);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(0, 0.1f);
        verify(circleView, times(touchCounter)).onPressedDown();
        touchDownAndUp(0.1f, 0);
        verify(circleView, times(touchCounter)).onPressedDown();
    }


    @Test
    public void testOnTouchOut() throws Exception {
        touchDown(2, 1);
        verify(circleView, times(0)).onPressedDown();
        touchDown(-2, 1);
        verify(circleView, times(0)).onPressedDown();
        touchDown(-2, 0);
        verify(circleView, times(0)).onPressedDown();
        touchDown(0.1f, 2);
        verify(circleView, times(0)).onPressedDown();
    }

    @Test
    public void testSecondTouchDownBeforeUpNotFireOnTouch() throws Exception {
        touchDown(0, 0);
        touchDown(0, 0);
        verify(circleView, times(1)).onPressedDown();
    }


    @Test
    public void testTouchUp() throws Exception {
        touchDownAndUp(0, 0);
        verify(circleView, times(1)).onPressedUp();
    }

    @Test
    public void testSecondTouchUpBeforeDownNotFireOnTouch() throws Exception {
        touchDownAndUp(0, 0);
        touchDownAndUp(0, 0);
        verify(circleView, times(2)).onPressedUp();
        touchUp();
        verify(circleView, times(2)).onPressedUp();
    }

    @Test
    public void testTouchMove() throws Exception {
        Touch touch = new Touch(new Point(0, 0), Touch.TouchMode.MOVE);
        circleViewPresenter.onTouch(touch);
        verify(circleView, times(1)).onPressedDown();
        touch = new Touch(new Point(0, 0), Touch.TouchMode.MOVE);
        circleViewPresenter.onTouch(touch);
        verify(circleView, times(1)).onPressedDown();
        touch = new Touch(new Point(2, 0), Touch.TouchMode.MOVE);
        circleViewPresenter.onTouch(touch);
        verify(circleView, times(1)).onPressedDown();
        verify(circleView, times(1)).onPressedUp();
        touch = new Touch(new Point(2, 0), Touch.TouchMode.MOVE);
        circleViewPresenter.onTouch(touch);
        verify(circleView, times(1)).onPressedDown();
        verify(circleView, times(1)).onPressedUp();
    }

    private void touchDownAndUp(float x, float y) {
        touchDown(x, y);
        touchUp();
    }

    private void touchDown(float x, float y) {
        Touch touch = new Touch(new Point(x, y), Touch.TouchMode.DOWN);
        circleViewPresenter.onTouch(touch);
        touchCounter++;
    }

    private void touchUp() {
        Touch touch = new Touch(Touch.TouchMode.UP);
        circleViewPresenter.onTouch(touch);
    }
}