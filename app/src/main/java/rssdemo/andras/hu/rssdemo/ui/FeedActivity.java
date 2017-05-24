package rssdemo.andras.hu.rssdemo.ui;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.databinding.ActivityFeedBinding;
import rssdemo.andras.hu.rssdemo.di.Injector;
import rssdemo.andras.hu.rssdemo.repository.FeedRepository;

public class FeedActivity extends AppCompatActivity {

    public FeedRepository feedRepository;
    private ActivityFeedBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDrawer();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeedAdapter();
        binding.recyclerView.setAdapter(adapter);
        adapter.setItems(feedRepository.loadFeed("").getItems());
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        final Menu menu = binding.navigation.getMenu();
        for (Subscription subscription : feedRepository.getSubscriptions()) {
            menu.add(subscription.getName());
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
