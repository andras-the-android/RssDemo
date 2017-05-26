package rssdemo.andras.hu.rssdemo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rssdemo.andras.hu.rssdemo.data.Feed;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rx.Observable;

public class FeedRepository {

    private FeedConverter feedConverter;

    public FeedRepository(FeedConverter feedConverter) {
        this.feedConverter = feedConverter;
    }

    public List<Subscription> getSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription;
        subscription = new Subscription();
        subscription.setName("Android Authority");
        subscription.setUrl("http://www.androidauthority.com/feed");
        subscriptions.add(subscription);

        subscription = new Subscription();
        subscription.setName("Android Police");
        subscription.setUrl("http://www.androidpolice.com/feed");
        subscriptions.add(subscription);

        subscription = new Subscription();
        subscription.setName("Android Developers");
        subscription.setUrl("https://android-developers.blogspot.com/atom.xml");
        subscriptions.add(subscription);

        return subscriptions;
    }

    public Observable<Feed> getFeed(final String url) {
        return Observable.fromCallable(new Callable<Feed>() {
            @Override
            public Feed call() throws Exception {
                return feedConverter.convert(url);
            }
        });
    }




}
