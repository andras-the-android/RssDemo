package rssdemo.andras.hu.rssdemo.di;

import rssdemo.andras.hu.rssdemo.repository.FeedRepository;
import rssdemo.andras.hu.rssdemo.ui.FeedActivity;

/**
 * Created by Andras Nemeth on 2017. 05. 24..
 */

public class Injector {

    private static FeedRepository feedRepository = new FeedRepository();

    public static void inject(FeedActivity activity) {
        activity.feedRepository = feedRepository;
    }
}
