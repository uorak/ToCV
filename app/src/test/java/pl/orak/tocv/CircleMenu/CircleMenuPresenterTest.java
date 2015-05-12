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
import static org.mockito.Matchers.eq;
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
        when(circleMenuView.getRotation()).thenReturn(0f);
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
        verify(circleMenuView, times(0)).updateMenuItems(anyInt(), eq(false));
    }

    @Test
    public void testOnTouchNotMoveNotUpdate() throws Exception {
        presenter.onTouch(new Touch(new Point(1,0), Touch.TouchMode.DOWN), 0);
        presenter.onTouch(new Touch(new Point(1,0), Touch.TouchMode.MOVE), 0);
        verify(circleMenuView, times(0)).updateMenuItems(anyInt(), eq(false));
    }

    @Test
    public void testOnItemClick() throws Exception {
        List<MyMenuItem> menuItems = new ArrayList<>();
        MyMenuItem item1 = mock(MyMenuItem.class);
        menuItems.add(item1);
        MyMenuItem item2 = mock(MyMenuItem.class);
        menuItems.add(item2);
        MyMenuItem item3 = mock(MyMenuItem.class);
        menuItems.add(item3);
        MyMenuItem item4 = mock(MyMenuItem.class);
        menuItems.add(item4);
        presenter.setMenuItems(menuItems);

        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(180f), eq(true));

        presenter.onEvent(new MenuItemClickedEvent(item2));
        verify(circleMenuView, times(1)).updateMenuItems(eq(90f), eq(true));

        presenter.onEvent(new MenuItemClickedEvent(item3));
        verify(circleMenuView, times(1)).updateMenuItems(eq(0f), eq(true));

        presenter.onEvent(new MenuItemClickedEvent(item4));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-90f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(90f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(2)).updateMenuItems(eq(90f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(190f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-10f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-90f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(2)).updateMenuItems(eq(-90f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-190f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(10f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(280f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-100f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(370f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(170f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-370f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-170f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-1f);
        presenter.onEvent(new MenuItemClickedEvent(item1));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-179f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-1f);
        presenter.onEvent(new MenuItemClickedEvent(item2));
        verify(circleMenuView, times(1)).updateMenuItems(eq(91f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(181f);
        presenter.onEvent(new MenuItemClickedEvent(item2));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-91f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-181f);
        presenter.onEvent(new MenuItemClickedEvent(item2));
        verify(circleMenuView, times(1)).updateMenuItems(eq(-89f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(181f);
        presenter.onEvent(new MenuItemClickedEvent(item4));
        verify(circleMenuView, times(1)).updateMenuItems(eq(89f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-181f);
        presenter.onEvent(new MenuItemClickedEvent(item4));
        verify(circleMenuView, times(2)).updateMenuItems(eq(91f), eq(true));

        when(circleMenuView.getRotation()).thenReturn(-170f);
        presenter.onEvent(new MenuItemClickedEvent(item4));
        verify(circleMenuView, times(1)).updateMenuItems(eq(80f), eq(true));

    }
}