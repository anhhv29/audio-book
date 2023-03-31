/*
 *        http://www.apache.org/licenses/LICENSE-2.0
 */

package vn.gotech.audiobook.widget.kprogresshud;

import android.content.Context;

class Helper {

    private static float scale;

    public static int dpToPixel(float dp, Context context) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dp * scale);
    }
}
