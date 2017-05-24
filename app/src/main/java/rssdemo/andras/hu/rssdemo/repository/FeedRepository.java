package rssdemo.andras.hu.rssdemo.repository;

import java.util.ArrayList;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.Feed;

public class FeedRepository {

    public List<Feed> getFeeds() {
        List<Feed> feeds = new ArrayList<>();
        Feed feed;
        feed = new Feed();
        feed.setId("feed_1");
        feed.setName("Android Authority");
        feed.setUrl("http://www.androidauthority.com/feed");
        feeds.add(feed);

        feed = new Feed();
        feed.setName("Android Police");
        feed.setUrl("http://www.androidpolice.com/feed");
        feeds.add(feed);

        feed = new Feed();
        feed.setName("Android Developers");
        feed.setUrl("https://android-developers.blogspot.com/atom.xml");
        feeds.add(feed);

        return feeds;
    }
}
