package pl.orak.tocv.CircleView;

import android.content.Context;
import android.util.AttributeSet;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Tomek on 2015-05-12.
 */
public class CenterCircleViewImpl extends CircleViewImpl {
    public CenterCircleViewImpl(Context context) {
        super(context);
    }

    public CenterCircleViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterCircleViewImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onPressedUp() {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(this);
    }
}
