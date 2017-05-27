package rssdemo.andras.hu.rssdemo.ui.subscriptions;


import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;

public class SubscriptionsViewModel {

    private SubscriptionRepository subscriptionRepository;

    public SubscriptionsViewModel(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void setView(SubscriptionsView view) {
        view.getAdapter().setItems(subscriptionRepository.getSubscriptions());
    }
}
