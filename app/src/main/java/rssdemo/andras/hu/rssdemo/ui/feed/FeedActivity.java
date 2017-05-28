package rssdemo.andras.hu.rssdemo.ui.feed;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import rssdemo.andras.hu.rssdemo.R;
import rssdemo.andras.hu.rssdemo.data.Subscription;
import rssdemo.andras.hu.rssdemo.databinding.ActivityFeedBinding;
import rssdemo.andras.hu.rssdemo.di.Injector;

//45 pomodoros
public class FeedActivity extends AppCompatActivity implements FeedView {

    private static final long DRAWER_CLOSE_DELAY_MS = 200;

    public FeedViewModel viewModel;
    public FeedAdapter adapter;
    private ActivityFeedBinding binding;
    private ActionBarDrawerToggle drawerToggle;
    private final Handler drawerActionHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        initToolbar();
        initDrawer();
        initRecyclerView();

        viewModel.setView(this);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDrawer() {
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name, R.string.app_name);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        binding.navigation.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    private void initRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private boolean onNavigationItemSelected(final MenuItem menuItem) {
        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening. This may seem a bit hacky but in practice
        // it works noticeably faster than listening to the onDrawerClosed callback
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        drawerActionHandler.postDelayed(() -> viewModel.onDrawerMenuSelection(menuItem), DRAWER_CLOSE_DELAY_MS);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onStart();
    }

    @Override
    public void populateDrawerMenu(List<Subscription> subscriptions) {
        final Menu menu = binding.navigation.getMenu();
        int order = 0;
        menu.removeGroup(R.id.nav_drawer_feeds);
        for (Subscription subscription : subscriptions) {
            MenuItem menuItem = menu.add(R.id.nav_drawer_feeds, order, order++, subscription.getName());
            menuItem.setIcon(R.drawable.ic_rss_feed_black);
        }
    }

    @Override
    public FeedAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.errror_load_feed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoaderOverlay() {
        binding.loaderOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoaderOverlay() {
        binding.loaderOverlay.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(String name) {
        //noinspection ConstantConditions
        getSupportActionBar().setTitle(name);
    }

    @Override
    public void showFacebookShareDialog(ShareLinkContent content) {
        ShareDialog.show(this, content);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.share_feed:
                viewModel.shareFeed();
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
