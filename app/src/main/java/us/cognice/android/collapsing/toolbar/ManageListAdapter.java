package us.cognice.android.collapsing.toolbar;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ManageListAdapter extends RecyclerView.Adapter<ManageListAdapter.ViewHolder> {

    private final MainActivity mainActivity;
    private final List<Item> items;

    public ManageListAdapter(MainActivity mainActivity, List<Item> items) {
        this.mainActivity = mainActivity;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = items.get(position);
        holder.nameView.setText(holder.item.getName());
        holder.detailsView.setText(holder.item.getMessage());
        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked on " + holder.item.getName() + " icon", Toast.LENGTH_LONG).show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked on " + holder.item.getName() + " edit button", Toast.LENGTH_LONG).show();
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(v.getContext());
                adb.setTitle("Confirm action");
                adb.setMessage("Are you sure you want to delete location '" + holder.item.getName() + "'?");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeItem(holder.item.getId());
                    }});
                adb.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(String id) {
        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, items.size() - i);
                lockAppBar();
                break;
            }
        }
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
        lockAppBar();
    }

    private void lockAppBar() {
        //waiting for add/remove animation to complete
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        mainActivity.lockAppBar();
                    }
                }, 300);
    }

    public List<Item> getItems() {
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView detailsView;
        final ImageView location;
        final ImageView edit;
        final ImageView remove;
        Item item;
        public final View view;

        ViewHolder(View view) {
            super(view);
            this.view = view;
            this.nameView = view.findViewById(R.id.itemName);
            this.detailsView = view.findViewById(R.id.itemDetails);
            this.location = view.findViewById(R.id.itemIcon);
            this.edit = view.findViewById(R.id.itemEdit);
            this.remove = view.findViewById(R.id.itemRemove);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}
