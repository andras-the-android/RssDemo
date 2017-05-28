package rssdemo.andras.hu.rssdemo.ui.subscriptions;


import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository;

public class SubscriptionsViewModel {

    private SubscriptionRepository subscriptionRepository;
    private SubscriptionsView view;

    public SubscriptionsViewModel(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void setView(SubscriptionsView view) {
        this.view = view;
        refreshData();
    }

    void refreshData() {
        view.getAdapter().setItems(subscriptionRepository.getSubscriptions());
    }

    void deleteSubscription(String name) {
        subscriptionRepository.delete(name);
        refreshData();
    }
}
