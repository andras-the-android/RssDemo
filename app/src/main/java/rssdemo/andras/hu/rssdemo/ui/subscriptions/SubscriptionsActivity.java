package rssdemo.andras.hu.rssdemo.ui.subscriptions;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.databinding.ActivitySubscriptionsBinding;
import rssdemo.andras.hu.rssdemo.di.Injector;

public class SubscriptionsActivity extends AppCompatActivity implements SubscriptionsView {

    public SubscriptionsViewModel viewModel;
    private SubscriptionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
        ActivitySubscriptionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_subscriptions);

        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter = new SubscriptionsAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        viewModel.setView(this);
    }

    @Override
    public SubscriptionsAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
