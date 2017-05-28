package rssdemo.andras.hu.rssdemo.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rssdemo.andras.hu.rssdemo.data.Subscription;

public class SubscriptionRepository {

    private static final String NAME_SUBSCRIPTIONS = "subscriptionsName";
    private static final String KEY_SUBSCRIPTIONS = "subscriptionsKey";
    private static final String KEY_FIRST_RUN = "firstRunKey";

    private List<Subscription> subscriptions;
    private final SharedPreferences sharedPref;

    public SubscriptionRepository(Context context) {
        sharedPref = context.getSharedPreferences(NAME_SUBSCRIPTIONS, Context.MODE_PRIVATE);
        loadSubscriptions();
    }

    private void loadSubscriptions() {
        if (isFirstRun()) {
            initDefaultSubscriptions();
        } else {
            String serializedSubscriptions = sharedPref.getString(KEY_SUBSCRIPTIONS, "");
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            subscriptions = gson.fromJson(serializedSubscriptions, listType);
        }
    }

    private void initDefaultSubscriptions() {
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
        saveSubscriptions();
    }

    private void saveSubscriptions() {
        Gson gson = new Gson();
        String serializedSubscriptions = gson.toJson(subscriptions);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SUBSCRIPTIONS, serializedSubscriptions);
        editor.apply();
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getUrlByName(String name) {
        return find(name).getUrl();
    }

    public void create(Subscription subscription) {
        subscriptions.add(subscription);
        saveSubscriptions();
    }

    public void update(String originalName, Subscription updatedSubscription) {
        Subscription storedSubscription = find(originalName);
        storedSubscription.setName(updatedSubscription.getName());
        storedSubscription.setUrl(updatedSubscription.getUrl());
        saveSubscriptions();
    }

    public void delete(String name){
        subscriptions.remove(find(name));
        saveSubscriptions();
    }

    public boolean isNameExisting(String name) {
        try {
            find(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Subscription find(String name) {
        for (Subscription subscription : subscriptions) {
            if (name.equals(subscription.getName())) {
                return subscription;
            }
        }
        throw new IllegalArgumentException("Non existing title");
    }

    private boolean isFirstRun() {
        if (sharedPref.getBoolean(KEY_FIRST_RUN, true)) {
            sharedPref.edit().putBoolean(KEY_FIRST_RUN, false).apply();
            return true;
        }
        return false;
    }
}
