package us.cognice.android.collapsing.toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by Kirill Simonov on 13.10.2017.
 */
public class UnderscoreRemover implements View.OnFocusChangeListener {

    private FloatingActionButton[] buttons;

    public UnderscoreRemover(FloatingActionButton... buttons) {
        this.buttons = buttons;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            v.getBackground().clearColorFilter();
            for (FloatingActionButton button : buttons) {
                button.hide();
            }
        } else {
            v.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN);
        }
    }
}