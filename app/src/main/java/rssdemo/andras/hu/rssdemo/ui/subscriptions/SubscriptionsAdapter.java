package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.databinding.ListItemSubscriptionBinding;


public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemSubscriptionBinding binding;

        public ViewHolder(ListItemSubscriptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindModel(Subscription subscription) {
            binding.setSubscription(subscription);
        }
    }
}
