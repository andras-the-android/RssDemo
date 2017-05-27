package rssdemo.andras.hu.rssdemo.ui.subscriptions;


import rssdemo.andras.hu.rssdemo.repository.FeedRepository;

public class SubscriptionsViewModel {

    private FeedRepository feedRepository;

    public SubscriptionsViewModel(FeedRepository feedRepository) {
        this.feedRepository = feedRepository;
    }

    public void setView(SubscriptionsView view) {
        view.getAdapter().setItems(feedRepository.getSubscriptions());
    }
}
