package rssdemo.andras.hu.rssdemo.ui;


import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedViewModel {

    private static final String TAG = "FeedViewModel";

    private FeedView view;
    private FeedRepository feedRepository;

    public FeedViewModel(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    void setView(FeedView view) {
        this.view = view;
        List<Subscription> subscriptions = feedRepository.getSubscriptions();
        view.populateDrawerMenu(subscriptions);
        if (!subscriptions.isEmpty()) {
            loadFeed(subscriptions.get(0).getUrl());
        }
    }

    void onDrawerMenuSelection(CharSequence title) {
        loadFeed(feedRepository.getUrlByName(title));
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
