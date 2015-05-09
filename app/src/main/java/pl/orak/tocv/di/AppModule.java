package pl.orak.tocv.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import pl.orak.tocv.di.utils.MockSetting;

import static org.mockito.Mockito.mock;

/**
 * Created by Tomek on 2015-04-27.
 */
@Module
public class AppModule {

    private Context context;

    private EventBus eventBus;

    private MockSetting mockSetting;

    public AppModule(Context context) {
        this.context = context;
        this.mockSetting = new MockSetting(false);
    }

    public AppModule(Context context, MockSetting mockSetting) {
        this.context = context;
        this.mockSetting = mockSetting;
    }


    @Singleton
    @Provides
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    EventBus provideEventBus() {
        if(mockSetting.mock){
            return mock(EventBus.class);
        }else {
            return EventBus.getDefault();
        }
    }

    @Singleton
    @Provides
    MockSetting provideMockSetting() {
        return mockSetting;
    }

}
