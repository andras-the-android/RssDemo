package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.databinding.ActivitySubscriptionsBinding;
import rssdemo.andras.hu.rssdemo.di.Injector;
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionsView {

    public SubscriptionsViewModel viewModel;
    public SubscriptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
        ActivitySubscriptionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_subscriptions);
        initToolbar(binding);
        initRecyclerView(binding);
        viewModel.setView(this);
    }

    private void initToolbar(ActivitySubscriptionsBinding binding) {
        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView(ActivitySubscriptionsBinding binding) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public SubscriptionsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subscriptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
            case R.id.add_subscription:
                openNewEditor();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openNewEditor() {
        SubscriptionEditorDialogFragment editor = SubscriptionEditorDialogFragment.create();
        editor.show(getSupportFragmentManager(), "");
        getSupportFragmentManager().executePendingTransactions();
        editor.getDialog().setOnDismissListener(dialogInterface -> viewModel.refreshData());
    }
}
