package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor


import android.support.annotation.StringRes

import rssdemo.andras.hu.rssdemo.data.Subscription

internal interface SubscriptionEditorView {

    fun raiseErrorOnName(@StringRes messageResId: Int)

    fun raiseErrorOnUrl(@StringRes messageResId: Int)

    fun closeEditor()

    fun getSubscription(): Subscription
}
