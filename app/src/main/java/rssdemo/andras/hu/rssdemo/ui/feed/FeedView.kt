package rssdemo.andras.hu.rssdemo.ui.feed


import com.facebook.share.model.ShareLinkContent
import rssdemo.andras.hu.rssdemo.data.FeedItem

import rssdemo.andras.hu.rssdemo.data.Subscription

internal interface FeedView {

    fun setFeedItems(items: List<FeedItem>)

    fun populateDrawerMenu(subscriptions: List<Subscription>)

    fun showError()

    fun showLoaderOverlay()

    fun hideLoaderOverlay()

    fun setTitle(name: String)

    fun showFacebookShareDialog(content: ShareLinkContent)
}
