package rssdemo.andras.hu.rssdemo.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.Subscription;

public class SubscriptionRepository {

    private List<Subscription> subscriptions;

    public SubscriptionRepository(Context context) {
        loadSubscriptions();
    }

    private void loadSubscriptions() {
        subscriptions = new ArrayList<>();
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
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getUrlByName(String name) {
        return find(name).getUrl();
    }

    public void create(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public void update(String originalName, Subscription updatedSubscription) {
        Subscription storedSubscription = find(originalName);
        storedSubscription.setName(updatedSubscription.getName());
        storedSubscription.setUrl(updatedSubscription.getUrl());
    }

    public void delete(String name){
        subscriptions.remove(find(name));
    }

    private Subscription find(String name) {
        for (Subscription subscription : subscriptions) {
            if (name.equals(subscription.getName())) {
                return subscription;
            }
        }
        throw new IllegalArgumentException("Non existing title");
    }
}
