package rssdemo.andras.hu.rssdemo.di

import android.content.Context
import rssdemo.andras.hu.rssdemo.network.FeedApi
import rssdemo.andras.hu.rssdemo.repository.FeedConverter
import rssdemo.andras.hu.rssdemo.repository.FeedRepository
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository
import rssdemo.andras.hu.rssdemo.ui.Navigator
import rssdemo.andras.hu.rssdemo.ui.feed.FeedActivity
import rssdemo.andras.hu.rssdemo.ui.feed.FeedAdapter
import rssdemo.andras.hu.rssdemo.ui.feed.FeedViewModel
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsActivity
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsAdapter
import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsViewModel
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * I don't like Dagger and this simple class is enough to represent di.
 */
object Injector {

    private lateinit var feedRepository: FeedRepository
    private lateinit var subscriptionRepository: SubscriptionRepository

    fun init(context: Context) {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        feedRepository = FeedRepository(FeedConverter(FeedApi(), dateFormat))
        subscriptionRepository = SubscriptionRepository(context.applicationContext)
    }

    fun inject(activity: FeedActivity) {
        val navigator = Navigator(activity)
        activity.viewModel = FeedViewModel(feedRepository, subscriptionRepository, navigator)
        activity.adapter = FeedAdapter(navigator)
    }

    fun inject(activity: SubscriptionsActivity) {
        activity.viewModel = SubscriptionsViewModel(subscriptionRepository)
        activity.adapter = SubscriptionsAdapter(activity, activity.viewModel)
    }

    fun inject(subscriptionEditor: SubscriptionEditorDialogFragment) {
        subscriptionEditor.viewModel = SubscriptionEditorViewModel(subscriptionRepository)
    }
}
