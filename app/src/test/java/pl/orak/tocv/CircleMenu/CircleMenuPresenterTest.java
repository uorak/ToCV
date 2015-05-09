package pl.orak.tocv.CircleMenu;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import pl.orak.tocv.CircleUtils.CircleParams;
import pl.orak.tocv.CircleUtils.Point;
import pl.orak.tocv.CircleUtils.Touch;
import pl.orak.tocv.ToCvApp;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CircleMenuPresenterTest {

    CircleMenuPresenter presenter;

    @Mock
    CircleMenuView circleMenuView;

    @Before
    public void setUp() throws Exception {
        ToCvApp.initComponent(mock(Context.class), true);
        MockitoAnnotations.initMocks(this);
        when(circleMenuView.getCircleParams()).thenReturn(new CircleParams(new Point(0, 0), 1f));
        presenter = new CircleMenuPresenter(circleMenuView);
    }

    @Test
    public void testInitialMenuItemsPositions1() throws Exception {
        List<MyMenuItem> menuItems= new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        presenter.setMenuItems(menuItems);
        Point result = presenter.getItemPosition(0);
        assertTrue(result.equals(new Point(0, 1)));

    }

    @Test
    public void testInitialMenuItemsPositions2() throws Exception {
        List<MyMenuItem> menuItems= new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        MyMenuItem item2 = mock(MyMenuItem.class);
        menuItems.add(item2);
        presenter.setMenuItems(menuItems);
        Point result = presenter.getItemPosition(0);
        assertTrue(result.equals(new Point(0, 1)));
        result = presenter.getItemPosition(1);
        assertTrue(result.equals(new Point(0, -1)));
    }

    @Test
    public void testInitialMenuItemsPositions3() throws Exception {
        List<MyMenuItem> menuItems= new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        MyMenuItem item2 = mock(MyMenuItem.class);
        menuItems.add(item2);
        MyMenuItem item3 = mock(MyMenuItem.class);
        menuItems.add(item3);
        presenter.setMenuItems(menuItems);
        Point result = presenter.getItemPosition(0);
        assertTrue(result.equals(new Point(0,1)));
    }

    @Test
    public void testInitialMenuItemsPositions4() throws Exception {
        List<MyMenuItem> menuItems= new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        MyMenuItem item2 = mock(MyMenuItem.class);
        menuItems.add(item2);
        MyMenuItem item3 = mock(MyMenuItem.class);
        menuItems.add(item3);
        MyMenuItem item4 = mock(MyMenuItem.class);
        menuItems.add(item4);
        presenter.setMenuItems(menuItems);
        Point result = presenter.getItemPosition(0);
        assertTrue(result.equals(new Point(0,1)));
        result = presenter.getItemPosition(1);
        assertTrue(result.equals(new Point(1, 0)));
        result = presenter.getItemPosition(2);
        assertTrue(result.equals(new Point(0, -1)));
        result = presenter.getItemPosition(3);
        assertTrue(result.equals(new Point(-1, 0)));
    }

    @Test
    public void testAddMenuOnSettingMenu() throws Exception {
        List<MyMenuItem> menuItems= new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        MyMenuItem item2 = mock(MyMenuItem.class);
        menuItems.add(item2);
        MyMenuItem item3 = mock(MyMenuItem.class);
        menuItems.add(item3);
        presenter.setMenuItems(menuItems);
        Point pos = presenter.getItemPosition(0);
        verify(circleMenuView, times(1)).addMenuItem(item1, pos);

    }

    @Test
    public void testOnTouchDownNotUpdate() throws Exception {
        presenter.onTouch(new Touch(new Point(0, 0), Touch.TouchMode.DOWN), 0);
        presenter.onTouch(new Touch(new Point(1,0), Touch.TouchMode.DOWN), 0);
        presenter.onTouch(new Touch(new Point(0,1), Touch.TouchMode.DOWN), 0);
        verify(circleMenuView, times(0)).updateMenuItems(anyInt());
    }

    @Test
    public void testOnTouchNotMoveNotUpdate() throws Exception {
        presenter.onTouch(new Touch(new Point(1,0), Touch.TouchMode.DOWN), 0);
        presenter.onTouch(new Touch(new Point(1,0), Touch.TouchMode.MOVE), 0);
        verify(circleMenuView, times(0)).updateMenuItems(anyInt());
    }



}