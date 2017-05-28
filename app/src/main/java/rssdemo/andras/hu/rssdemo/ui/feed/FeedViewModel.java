package rssdemo.andras.hu.rssdemo.ui.feed;


import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Feed;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;
import rssdemo.andras.hu.rssdemo.ui.Navigator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedViewModel {

    private static final String TAG = "FeedViewModel";

    private FeedView view;
    private FeedRepository feedRepository;
    private SubscriptionRepository subscriptionRepository;
    private Navigator navigator;
    private String selectedFeedTitle;

    public FeedViewModel(FeedRepository feedRepository, SubscriptionRepository subscriptionRepository, Navigator navigator) {
        this.feedRepository = feedRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.navigator = navigator;
    }

    void setView(FeedView view) {
        this.view = view;
    }

    void onStart() {
        List<Subscription> subscriptions = subscriptionRepository.getSubscriptions();
        view.populateDrawerMenu(subscriptions);
        if (!subscriptions.isEmpty()) {
            Subscription subscription = subscriptions.get(0);
            selectedFeedTitle = subscription.getName();
            view.setTitle(selectedFeedTitle);
            loadFeed(subscription.getUrl());
        }
    }

    void onDrawerMenuSelection(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_drawer_subscriptions) {
            navigator.goToSubscriptionScreen();
        } else {
            String name = menuItem.getTitle().toString();
            selectedFeedTitle = name;
            view.setTitle(name);
            loadFeed(subscriptionRepository.getUrlByName(name));
        }
    }

    void shareFeed() {
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(subscriptionRepository.getUrlByName(selectedFeedTitle)))
                .build();
        view.shareContent(content);
    }

    private void loadFeed(String url) {
        view.getAdapter().clear();
        view.showLoaderOverlay();
        feedRepository.getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onFeedLoaded, this::onErrorLoadFeed);
    }

    private void onFeedLoaded(Feed feed) {
        view.getAdapter().setItems(feed.getItems());
        view.hideLoaderOverlay();
    }

    private void onErrorLoadFeed(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
        view.hideLoaderOverlay();
        view.showError();
    }
}
