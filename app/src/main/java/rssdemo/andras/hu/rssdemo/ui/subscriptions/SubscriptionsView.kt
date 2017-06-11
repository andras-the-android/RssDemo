package rssdemo.andras.hu.rssdemo.ui.subscriptions

import rssdemo.andras.hu.rssdemo.data.Subscription


internal interface SubscriptionsView {

    fun setSubscriptionItems(items: List<Subscription>)

}
