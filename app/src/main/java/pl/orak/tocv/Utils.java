package pl.orak.tocv;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import java.math.BigDecimal;

/**
 * Created by Tomek on 2015-04-26.
 */
public class Utils {

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static float dpToPx(int dp, Context context){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

}
