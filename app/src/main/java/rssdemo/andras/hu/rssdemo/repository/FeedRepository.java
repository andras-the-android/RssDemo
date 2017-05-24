package rssdemo.andras.hu.rssdemo.repository;

import java.util.ArrayList;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.Feed;
import rssdemo.andras.hu.rssdemo.data.FeedItem;
import rssdemo.andras.hu.rssdemo.data.Subscription;

public class FeedRepository {

    public List<Subscription> getSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription;
        subscription = new Subscription();
        subscription.setName("Android Authority");
        subscription.setUrl("http://www.androidauthority.com/subscription");
        subscriptions.add(subscription);

        subscription = new Subscription();
        subscription.setName("Android Police");
        subscription.setUrl("http://www.androidpolice.com/subscription");
        subscriptions.add(subscription);

        subscription = new Subscription();
        subscription.setName("Android Developers");
        subscription.setUrl("https://android-developers.blogspot.com/atom.xml");
        subscriptions.add(subscription);

        return subscriptions;
    }

    public Feed loadFeed(String url) {
        Feed feed = new Feed();
        List<FeedItem> items = new ArrayList<>();

        FeedItem item = new FeedItem();
        item.setTitle("Android Authority");
        item.setDescription("Android News blog dedicated to providing expert tips, news, reviews, Android Phones, Android Apps, Android Tablet, Rooting & Howtos.");
        item.setUrl("\"http://www.androidpolice.com/subscription\"");
        item.setDate("2017.05.01 12:34");
        item.setImageUrl("http://cdn01.androidauthority.net/wp-content/uploads/2016/07/Pokemon-Go-Pokeball-Poke-Ball-2-1340x754.jpg");
        items.add(item);
        items.add(item);
        items.add(item);

        feed.setItems(items);
        return feed;
    }


}
