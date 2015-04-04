package pl.orak.tocv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Tomek on 2015-04-04.
 */
@RunWith(CustomRobolectricRunner.class)
public class SimpleUnitTest {

    @Test
    public void checkJUnitWork() {
        // failing test gives much better feedback
        // to show that all works correctly ;)
        assertThat(true, is(true));
    }

    @Test
    public void testIt() {
        // failing test gives much better feedback
        // to show that all works correctly ;)
        assertThat(RuntimeEnvironment.application, notNullValue());
    }

}
