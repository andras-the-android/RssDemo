package rssdemo.andras.hu.rssdemo.di;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Locale;

import rssdemo.andras.hu.rssdemo.network.FeedApi;
import rssdemo.andras.hu.rssdemo.repository.FeedConverter;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;
import rssdemo.andras.hu.rssdemo.ui.Navigator;
import rssdemo.andras.hu.rssdemo.ui.feed.FeedActivity;
import rssdemo.andras.hu.rssdemo.ui.feed.FeedAdapter;
import rssdemo.andras.hu.rssdemo.ui.feed.FeedViewModel;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsAdapter;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsActivity;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsViewModel;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorViewModel;

/**
 * I don't like Dagger and this simple class is enough to represent di.
 */
public class Injector {

    private static FeedRepository feedRepository;
    private static SubscriptionRepository subscriptionRepository;

    public static void init(Context context) {
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());
        feedRepository = new FeedRepository(new FeedConverter(new FeedApi(), dateFormat));
        subscriptionRepository = new SubscriptionRepository(context.getApplicationContext());
    }

    public static void inject(FeedActivity activity) {
        Navigator navigator = new Navigator(activity);
        activity.setViewModel(new FeedViewModel(feedRepository, subscriptionRepository, navigator));
        activity.setAdapter(new FeedAdapter(navigator));
    }

    public static void inject(SubscriptionsActivity activity) {
        activity.setViewModel(new SubscriptionsViewModel(subscriptionRepository));
        activity.setAdapter(new SubscriptionsAdapter(activity, activity.getViewModel()));
    }

    public static void inject(SubscriptionEditorDialogFragment subscriptionEditor) {
        subscriptionEditor.setViewModel(new SubscriptionEditorViewModel(subscriptionRepository));
    }
}
