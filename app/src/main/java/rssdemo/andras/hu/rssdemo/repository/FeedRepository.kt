package rssdemo.andras.hu.rssdemo.repository

import rssdemo.andras.hu.rssdemo.data.Feed
import rx.Observable

class FeedRepository(private val feedConverter: FeedConverter) {

    fun getFeed(url: String): Observable<Feed> {
        return Observable.fromCallable<Feed> { feedConverter.convert(url) }
    }

}
