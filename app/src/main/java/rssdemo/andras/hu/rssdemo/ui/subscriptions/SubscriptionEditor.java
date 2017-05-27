package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import rssdemo.andras.hu.rssdemo.databinding.ViewSubscriptionEditorBinding;


public class SubscriptionEditor extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewSubscriptionEditorBinding binding = ViewSubscriptionEditorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
