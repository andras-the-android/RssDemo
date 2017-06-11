package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.support.v7.app.AlertDialog;
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
    private SubscriptionsViewModel viewModel;

    public SubscriptionsAdapter(AppCompatActivity activity, SubscriptionsViewModel viewModel) {
        this.activity = activity;
        this.viewModel = viewModel;
    }

    void setItems(List<Subscription> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemSubscriptionBinding itemBinding = ListItemSubscriptionBinding.inflate(layoutInflater, parent, false);
        return new SubscriptionsAdapter.ViewHolder(activity, itemBinding, viewModel);
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
        private SubscriptionsViewModel viewModel;

        ViewHolder(AppCompatActivity activity, ListItemSubscriptionBinding binding, SubscriptionsViewModel viewModel) {
            super(binding.getRoot());
            this.activity = activity;
            this.binding = binding;
            this.viewModel = viewModel;
        }

        void bindModel(Subscription subscription) {
            binding.setSubscription(subscription);
            binding.setHandler(this);
        }

        public void onOptionsClick() {
            showPopupMenu();
        }

        private void showPopupMenu() {
            PopupMenu popup = new PopupMenu(activity, binding.options);
            popup.inflate(R.menu.menu_subscription_item);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit:
                        showEditorDialog();
                        break;
                    case R.id.menu_delete:
                        showDeleteDialog();
                        break;
                }
                return false;
            });
            popup.show();
        }

        private void showEditorDialog() {
            SubscriptionEditorDialogFragment editor = SubscriptionEditorDialogFragment.Companion.create(binding.getSubscription());
            editor.show(activity.getSupportFragmentManager(), "");
            activity.getSupportFragmentManager().executePendingTransactions();
            editor.getDialog().setOnDismissListener(dialogInterface -> viewModel.refreshData());
        }

        private void showDeleteDialog() {
            new AlertDialog.Builder(activity)
                    .setMessage(R.string.delete_confirmaion_message)
                    .setPositiveButton(R.string.yes, (dialog, which) -> viewModel.deleteSubscription(binding.getSubscription().getName()))
                    .setNegativeButton(R.string.no, (dialog, which) -> {})
                    .create().show();
        }
    }
}
