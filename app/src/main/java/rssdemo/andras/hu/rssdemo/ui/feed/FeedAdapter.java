package rssdemo.andras.hu.rssdemo.ui.feed;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.FeedItem;
import rssdemo.andras.hu.rssdemo.databinding.ListItemFeedBinding;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    private List<FeedItem> items = Collections.emptyList();

    public void setItems(List<FeedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemFeedBinding itemBinding = ListItemFeedBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindModel(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemFeedBinding binding;

        public ViewHolder(ListItemFeedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindModel(FeedItem item) {
            binding.setFeedItem(item);
        }
    }


}
