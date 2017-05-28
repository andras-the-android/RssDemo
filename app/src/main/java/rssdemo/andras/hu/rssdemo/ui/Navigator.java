package rssdemo.andras.hu.rssdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import rssdemo.andras.hu.rssdemo.ui.subscriptions.SubscriptionsActivity;

public final class Navigator {

    private Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    public void goToSubscriptionScreen() {
        Intent intent = new Intent(context, SubscriptionsActivity.class);
        context.startActivity(intent);
    }

    public void goToUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }
}
