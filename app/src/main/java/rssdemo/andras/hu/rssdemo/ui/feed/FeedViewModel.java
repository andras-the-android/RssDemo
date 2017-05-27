package rssdemo.andras.hu.rssdemo.ui.feed;


import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.ui.Navigator;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedViewModel {

    private static final String TAG = "FeedViewModel";

    private FeedView view;
    private FeedRepository feedRepository;
    private Navigator navigator;

    public FeedViewModel(FeedRepository feedRepository, Navigator navigator) {
        this.feedRepository = feedRepository;
        this.navigator = navigator;
    }

    void setView(FeedView view) {
        this.view = view;
        List<Subscription> subscriptions = feedRepository.getSubscriptions();
        view.populateDrawerMenu(subscriptions);
        if (!subscriptions.isEmpty()) {
            loadFeed(subscriptions.get(0).getUrl());
        }
    }

    void onDrawerMenuSelection(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_drawer_subscriptions) {
            navigator.goToSubscriptionScreen();
        } else {
            loadFeed(feedRepository.getUrlByName(menuItem.getTitle()));
        }
    }


    private void loadFeed(String url) {
        feedRepository.getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(feed -> view.getAdapter().setItems(feed.getItems()), this::onErrorLoadFeed);
    }

    private void onErrorLoadFeed(Throwable throwable) {
        Log.e(TAG, throwable.getMessage(), throwable);
        view.showError();
    }
}
