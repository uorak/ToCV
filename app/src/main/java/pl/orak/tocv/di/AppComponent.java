package pl.orak.tocv.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.orak.tocv.CircleMenu.CircleMenuItemViewImpl;
import pl.orak.tocv.CircleMenu.CircleMenuPresenter;
import pl.orak.tocv.CircleMenu.CircleMenuViewImpl;
import pl.orak.tocv.CircleView.CircleViewImpl;
import pl.orak.tocv.di.utils.BaseTarget;

/**
 * Created by Tomek on 2015-04-27.
 */
@Singleton
@Component(modules = {AppModule.class, MenuModule.class})
public interface AppComponent {
    void inject(BaseTarget target);
    void inject(CircleMenuPresenter presenter);

    void inject(CircleMenuViewImpl view);

    void inject(CircleViewImpl view);

    void inject(CircleMenuItemViewImpl view);
}
