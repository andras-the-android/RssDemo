package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.databinding.ListItemSubscriptionBinding;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment;

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder> {

    private List<Subscription> items = Collections.emptyList();
    private AppCompatActivity activity;

    public SubscriptionsAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setItems(List<Subscription> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemSubscriptionBinding itemBinding = ListItemSubscriptionBinding.inflate(layoutInflater, parent, false);
        return new SubscriptionsAdapter.ViewHolder(activity, itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindModel(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatActivity activity;
        private final ListItemSubscriptionBinding binding;

        ViewHolder(AppCompatActivity activity, ListItemSubscriptionBinding binding) {
            super(binding.getRoot());
            this.activity = activity;
            this.binding = binding;
        }

        void bindModel(Subscription subscription) {
            binding.setSubscription(subscription);
            binding.setHandler(this);
        }

        public void onOptionsClick() {
            PopupMenu popup = new PopupMenu(activity, binding.options);
            popup.inflate(R.menu.menu_subscription_item);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        break;
                    case R.id.menu_edit:
                        SubscriptionEditorDialogFragment editor = SubscriptionEditorDialogFragment.create(binding.getSubscription());
                        editor.show(activity.getSupportFragmentManager(), "sdcd");
                        break;
                }
                return false;
            });
            popup.show();
        }
    }
}
