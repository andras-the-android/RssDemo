package rssdemo.andras.hu.rssdemo.ui.subscriptions.editor;


import android.support.annotation.StringRes;

import rssdemo.andras.hu.rssdemo.data.Subscription;

interface SubscriptionEditorView {

    void raiseErrorOnName(@StringRes int messageResId);

    void raiseErrorOnUrl(@StringRes int messageResId);

    void closeEditor();

    Subscription getSubscription();
}
