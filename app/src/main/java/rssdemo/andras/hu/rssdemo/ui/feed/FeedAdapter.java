package rssdemo.andras.hu.rssdemo.ui.feed;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.FeedItem;
import rssdemo.andras.hu.rssdemo.databinding.ListItemFeedBinding;
import rssdemo.andras.hu.rssdemo.ui.Navigator;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    private List<FeedItem> items = Collections.emptyList();
    private Navigator navigator;

    public FeedAdapter(Navigator navigator) {
        this.navigator = navigator;
    }

    void setItems(List<FeedItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    void clear() {
        setItems(Collections.emptyList());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemFeedBinding itemBinding = ListItemFeedBinding.inflate(layoutInflater, parent, false);
        itemBinding.webview.getSettings().setLoadWithOverviewMode(true);
        itemBinding.webview.setInitialScale(180);
        return new ViewHolder(itemBinding, navigator);
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

        private final ListItemFeedBinding binding;
        private Navigator navigator;

        ViewHolder(ListItemFeedBinding binding, Navigator navigator) {
            super(binding.getRoot());
            this.binding = binding;
            this.navigator = navigator;
            binding.setHandler(this);
        }

        void bindModel(FeedItem item) {
            binding.setFeedItem(item);
            String summary = "<html><body>" + item.getDescription() + "</body></html>";
            binding.webview.loadData(summary, "text/html", null);
        }

        public void onTitleClick() {
            navigator.goToUrl(binding.getFeedItem().getLink());
        }
    }


}
