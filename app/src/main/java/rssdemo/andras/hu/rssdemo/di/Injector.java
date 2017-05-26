package rssdemo.andras.hu.rssdemo.di;

import rssdemo.andras.hu.rssdemo.network.FeedApi;
import rssdemo.andras.hu.rssdemo.repository.FeedConverter;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.ui.FeedActivity;
import rssdemo.andras.hu.rssdemo.ui.FeedViewModel;

/**
 * I don't like Dagger and this simple class is enough to represent di.
 */
public class Injector {

    private static FeedViewModel feedViewModel = new FeedViewModel(new FeedRepository(new FeedConverter(new FeedApi())));

    public static void inject(FeedActivity activity) {
        activity.viewModel = feedViewModel;
    }
}
