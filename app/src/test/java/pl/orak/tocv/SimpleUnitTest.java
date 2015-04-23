package pl.orak.tocv;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Tomek on 2015-04-04.
 */
public class SimpleUnitTest {

    @Test
    public void checkJUnitWork() {
        // failing test gives much better feedback
        // to show that all works correctly ;)
        assertThat(true, is(true));
    }

//    @Test
//    public void testIt() {
//        // failing test gives much better feedback
//        // to show that all works correctly ;)
//        assertThat(RuntimeEnvironment.application, notNullValue());
//    }

}
