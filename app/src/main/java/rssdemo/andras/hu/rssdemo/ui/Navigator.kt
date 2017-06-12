package rssdemo.andras.hu.rssdemo.ui

import android.content.Context
import android.content.Intent
import android.net.Uri

import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsActivity

class Navigator(private val context: Context) {

    fun goToSubscriptionScreen() {
        val intent = Intent(context, SubscriptionsActivity::class.java)
        context.startActivity(intent)
    }

    fun goToUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context.startActivity(i)
    }
}
