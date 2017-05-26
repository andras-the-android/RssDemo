package rssdemo.andras.hu.rssdemo.repository;


import com.einmalfel.earl.Item;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

import rssdemo.andras.hu.rssdemo.data.Feed;
import rssdemo.andras.hu.rssdemo.data.FeedItem;
import rssdemo.andras.hu.rssdemo.network.FeedApi;

public class FeedConverter {

    private FeedApi api;

    public FeedConverter(FeedApi api) {
        this.api = api;
    }

    Feed convert(String feedUrl) throws IOException, DataFormatException, XmlPullParserException {
        com.einmalfel.earl.Feed rawFeed = api.loadFeed(feedUrl);

        Feed feed = new Feed();
        feed.setDescription(rawFeed.getDescription());
        List<FeedItem> items = new ArrayList<>();
        feed.setItems(items);

        for (Item rawFeedItem : rawFeed.getItems()) {
            items.add(buildFeedItem(rawFeedItem));
        }

        return feed;
    }

    private FeedItem buildFeedItem(Item rawFeedItem) {
        FeedItem item = new FeedItem();
        item.setTitle(rawFeedItem.getTitle());
        item.setDescription(rawFeedItem.getDescription());
        item.setLink(rawFeedItem.getLink());
//        item.setDate(rawFeedItem.);
        item.setImageLink(rawFeedItem.getImageLink());
        return item;
    }
}
