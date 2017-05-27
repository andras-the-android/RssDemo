package rssdemo.andras.hu.rssdemo.di;

import android.content.Context;

import rssdemo.andras.hu.rssdemo.network.FeedApi;
import rssdemo.andras.hu.rssdemo.repository.FeedConverter;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.ui.Navigator;
import rssdemo.andras.hu.rssdemo.ui.feed.FeedActivity;
import rssdemo.andras.hu.rssdemo.ui.feed.FeedViewModel;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsActivity;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsViewModel;

/**
 * I don't like Dagger and this simple class is enough to represent di.
 */
public class Injector {

    private static FeedRepository feedRepository = new FeedRepository(new FeedConverter(new FeedApi()));

    public static void init(Context context) {
    }

    public static void inject(FeedActivity activity) {
        Navigator navigator = new Navigator(activity);
        activity.viewModel = new FeedViewModel(feedRepository, navigator);
    }

    public static void inject(SubscriptionsActivity activity) {
        activity.viewModel = new SubscriptionsViewModel(feedRepository);
    }
}
