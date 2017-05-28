package rssdemo.andras.hu.rssdemo.ui.feed;


import com.facebook.share.model.ShareLinkContent;

import java.util.List;

import rssdemo.andras.hu.rssdemo.data.Subscription;

public interface FeedView {
    FeedAdapter getAdapter();

    void populateDrawerMenu(List<Subscription> subscriptions);

    void showError();

    void showLoaderOverlay();

    void hideLoaderOverlay();

    void setTitle(String name);

    void shareContent(ShareLinkContent content);
}
