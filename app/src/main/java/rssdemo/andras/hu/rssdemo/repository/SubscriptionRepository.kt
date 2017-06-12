package rssdemo.andras.hu.rssdemo.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import rssdemo.andras.hu.rssdemo.data.Subscription
import java.util.*

class SubscriptionRepository(context: Context) {

    internal lateinit var subscriptions: MutableList<Subscription>
    private val sharedPref: SharedPreferences

    init {
        sharedPref = context.getSharedPreferences(NAME_SUBSCRIPTIONS, Context.MODE_PRIVATE)
        loadSubscriptions()
    }

    fun getUrlByName(name: String): String {
        return find(name).url
    }

    fun create(subscription: Subscription) {
        subscriptions.add(subscription)
        saveSubscriptions()
    }

    fun update(originalName: String, updatedSubscription: Subscription) {
        val storedSubscription = find(originalName)
        storedSubscription.name = updatedSubscription.name
        storedSubscription.url = updatedSubscription.url
        saveSubscriptions()
    }

    fun delete(name: String) {
        subscriptions.remove(find(name))
        saveSubscriptions()
    }

    fun isNameExisting(name: String): Boolean {
        try {
            find(name)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private fun loadSubscriptions() {
        if (isFirstRun()) {
            initDefaultSubscriptions()
        } else {
            val serializedSubscriptions = sharedPref.getString(KEY_SUBSCRIPTIONS, "")
            val listType = object : TypeToken<ArrayList<Subscription>>(){}.type
            subscriptions = Gson().fromJson<List<Subscription>>(serializedSubscriptions, listType).toMutableList()
        }
    }

    private fun isFirstRun() : Boolean {
        if (sharedPref.getBoolean(KEY_FIRST_RUN, true)) {
            sharedPref.edit().putBoolean(KEY_FIRST_RUN, false).apply()
            return true
        }
        return false
    }

    private fun initDefaultSubscriptions() {
        subscriptions = ArrayList<Subscription>()
        subscriptions.add(Subscription(
                name = "Android Police",
                url = "http://www.androidpolice.com/feed"
        ))

        subscriptions.add(Subscription(
                name = "XKCD",
                url = "https://xkcd.com/rss.xml"
        ))

        subscriptions.add(Subscription(
                name = "Android Authority",
                url = "http://www.androidauthority.com/feed"
        ))

        saveSubscriptions()
    }

    private fun saveSubscriptions() {
        val gson = Gson()
        val serializedSubscriptions = gson.toJson(subscriptions)

        val editor = sharedPref.edit()
        editor.putString(KEY_SUBSCRIPTIONS, serializedSubscriptions)
        editor.apply()
    }

    private fun find(name: String): Subscription {
        subscriptions
                .asSequence()
                .filter { name == it.name }
                .forEach { return it }
        throw IllegalArgumentException("Non existing title")
    }

    companion object {
        private val NAME_SUBSCRIPTIONS = "subscriptionsName"
        private val KEY_SUBSCRIPTIONS = "subscriptionsKey"
        private val KEY_FIRST_RUN = "firstRunKey"
    }
}
