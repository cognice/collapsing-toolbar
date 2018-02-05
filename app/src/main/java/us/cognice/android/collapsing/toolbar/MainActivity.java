package us.cognice.android.collapsing.toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";

    private FloatingActionButton add;
    private ManageListAdapter listAdapter;
    private CustomLayoutManager layoutManager;
    private AppBarLayout barLayout;
    private RecyclerView list;
    private boolean fabVisible = true;
    private int barOffset;
    private int itemsCounter = 0;
    protected boolean appBarLocked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        add = findViewById(R.id.add);
        barLayout = findViewById(R.id.app_bar_layout);
        barLayout.addOnOffsetChangedListener(this);
        list = findViewById(R.id.list);
        layoutManager = new CustomLayoutManager(this);
        listAdapter = new ManageListAdapter(this, generateItems());
        list.setLayoutManager(layoutManager);
        list.setAdapter(listAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                int number = itemsCounter++;
                listAdapter.addItem(new Item("i" + number, "List item #" + number, lorem));
                Toast.makeText(button.getContext(), "New item was added", Toast.LENGTH_LONG).show();
            }
        });
        EditText phoneInput = findViewById(R.id.phone);
        EditText nicknameInput = findViewById(R.id.nickname);
        phoneInput.setText("14240001122");
        nicknameInput.setText("Johny");
        initEditText(nicknameInput);
        initEditText(phoneInput);
    }

    private List<Item> generateItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add(new Item("i" + i, "List item #" + i, lorem));
        }
        itemsCounter = items.size();
        return items;
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int dy = barOffset - verticalOffset;
        barOffset = verticalOffset;
        if (dy > 0 && fabVisible) {
            // scrolling up
            toggleButtons(false);
        } else if (dy < 0 && !fabVisible){
            // scrolling down
            toggleButtons(true);
        }
        lockAppBar();
    }

    protected void lockAppBar() {
        if (barOffset == 0) {
            // if AppBar is expanded and all RecyclerView items are visible then lock AppBar
            appBarLocked = viewIsVisible(layoutManager.findViewByPosition(layoutManager.getItemCount() - 1));
            layoutManager.setScrollEnabled(!appBarLocked);
        }
        if (appBarLocked) barLayout.setExpanded(true, false);
    }

    private boolean viewIsVisible(View view) {
        Rect scrollBounds = new Rect();
        list.getHitRect(scrollBounds);
        return view != null && view.getLocalVisibleRect(scrollBounds);
    }

    private void toggleButtons(boolean visible) {
        if (visible) {
            add.show();
        } else {
            add.hide();
        }
        add.setClickable(visible);
        fabVisible = visible;
    }

    private void initEditText(final EditText edit) {
        edit.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.SRC_IN);
        edit.setOnFocusChangeListener(new UnderscoreRemover(add));
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    edit.clearFocus();
                    hideKeyboard(edit);
                    new Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    add.show();
                                }
                            }, 300);
                }
                return false;
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
