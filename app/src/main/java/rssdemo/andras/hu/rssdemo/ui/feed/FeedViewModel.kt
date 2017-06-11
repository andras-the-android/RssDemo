package rssdemo.andras.hu.rssdemo.ui.feed


import android.net.Uri
import android.util.Log
import android.view.MenuItem
import com.facebook.share.model.ShareLinkContent
import rssdemo.andras.hu.rssdemo.R
import rssdemo.andras.hu.rssdemo.data.Feed
import rssdemo.andras.hu.rssdemo.data.FeedItem
import rssdemo.andras.hu.rssdemo.repository.FeedRepository
import rssdemo.andras.hu.rssdemo.repository.SubscriptionRepository
import rssdemo.andras.hu.rssdemo.ui.Navigator
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class FeedViewModel(private val feedRepository: FeedRepository, private val subscriptionRepository: SubscriptionRepository, private val navigator: Navigator) {

    private val TAG = "FeedViewModel"

    private lateinit var view: FeedView
    private var selectedFeedTitle: String = ""

    internal fun setView(view: FeedView) {
        this.view = view
    }

    internal fun onStart() {
        val subscriptions = subscriptionRepository.subscriptions
        view.populateDrawerMenu(subscriptions)
        if (!subscriptions.isEmpty()) {
            val subscription = subscriptions[0]
            selectedFeedTitle = subscription.name
            view.setTitle(selectedFeedTitle)
            loadFeed(subscription.url)
        }
    }

    internal fun onDrawerMenuSelection(menuItem: MenuItem) {
        if (menuItem.itemId == R.id.nav_drawer_subscriptions) {
            navigator.goToSubscriptionScreen()
        } else {
            selectedFeedTitle = menuItem.title.toString()
            view.setTitle(selectedFeedTitle)
            loadFeed(subscriptionRepository.getUrlByName(selectedFeedTitle))
        }
    }

    internal fun shareFeed() {
        if (selectedFeedTitle.isEmpty()) {
            return
        }

        val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(subscriptionRepository.getUrlByName(selectedFeedTitle)))
                .build()
        view.showFacebookShareDialog(content)
    }

    private fun loadFeed(url: String) {
        view.setFeedItems(emptyList<FeedItem>())
        view.showLoaderOverlay()
        feedRepository.getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ onFeedLoaded(it) }, { onErrorLoadFeed(it) })
    }

    private fun onFeedLoaded(feed: Feed) {
        view.setFeedItems(feed.items)
        view.hideLoaderOverlay()
    }

    private fun onErrorLoadFeed(throwable: Throwable) {
        Log.e(TAG, throwable.message, throwable)
        view.hideLoaderOverlay()
        view.showError()
    }
}
