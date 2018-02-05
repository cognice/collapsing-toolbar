package us.cognice.android.collapsing.toolbar;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by Kirill Simonov on 15.12.2017.
 */
public class CustomLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
