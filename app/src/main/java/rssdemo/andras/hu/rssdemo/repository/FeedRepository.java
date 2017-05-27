package rssdemo.andras.hu.rssdemo.repository;

import rssdemo.andras.hu.rssdemo.data.Feed;
import rx.Observable;

public class FeedRepository {

    private FeedConverter feedConverter;

    public FeedRepository(FeedConverter feedConverter) {
        this.feedConverter = feedConverter;
    }

    public Observable<Feed> getFeed(final String url) {
        return Observable.fromCallable(() -> feedConverter.convert(url));
    }

}
