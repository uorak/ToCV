package pl.orak.tocv.di;

import javax.inject.Singleton;

import dagger.Component;
import pl.orak.tocv.CircleMenu.CircleMenuPresenter;
import pl.orak.tocv.di.utils.BaseTarget;

/**
 * Created by Tomek on 2015-04-27.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(BaseTarget target);

    void inject(CircleMenuPresenter presenter);

}
