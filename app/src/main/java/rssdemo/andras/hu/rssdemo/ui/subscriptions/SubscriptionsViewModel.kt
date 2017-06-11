package rssdemo.andras.hu.rssdemo.ui.subscriptions


import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository

class SubscriptionsViewModel(private val subscriptionRepository: SubscriptionRepository) {

    private lateinit var view: SubscriptionsView

    internal fun setView(view: SubscriptionsView) {
        this.view = view
        refreshData()
    }

    internal fun refreshData() {
        view.setSubscriptionItems(subscriptionRepository.subscriptions)
    }

    internal fun deleteSubscription(name: String) {
        subscriptionRepository.delete(name)
        refreshData()
    }
}
