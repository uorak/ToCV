package pl.orak.tocv.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.orak.tocv.CircleMenu.MyMenuItem;

/**
 * Created by Tomek on 2015-04-27.
 */
@Module
public class MenuModule {

    @Singleton
    @Provides
    List<MyMenuItem> provideMenuItems() {
        List<MyMenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MyMenuItem item = new MyMenuItem(i);
            menuItems.add(item);
        }
        return menuItems;
    }


}
