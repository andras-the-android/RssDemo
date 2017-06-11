package rssdemo.andras.hu.rssdemo.ui.feed

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import rssdemo.andras.hu.rssdemo.R
import rssdemo.andras.hu.rssdemo.data.FeedItem
import rssdemo.andras.hu.rssdemo.data.Subscription
import rssdemo.andras.hu.rssdemo.databinding.ActivityFeedBinding
import rssdemo.andras.hu.rssdemo.di.Injector

//50 pomodoros
class FeedActivity : AppCompatActivity(), FeedView {
    private val DRAWER_CLOSE_DELAY_MS: Long = 200

    lateinit var viewModel: FeedViewModel

    lateinit var adapter: FeedAdapter
    private lateinit var binding: ActivityFeedBinding

    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val drawerActionHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        binding = DataBindingUtil.setContentView<ActivityFeedBinding>(this, R.layout.activity_feed)
        initToolbar()
        initDrawer()
        initRecyclerView()

        viewModel.setView(this)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initDrawer() {
        drawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.app_name, R.string.app_name)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        binding.navigation.setNavigationItemSelectedListener({ onNavigationItemSelected(it)})
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // allow some time after closing the drawer before performing real navigation
        // so the user can see what is happening. This may seem a bit hacky but in practice
        // it works noticeably faster than listening to the onDrawerClosed callback
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        drawerActionHandler.postDelayed({ viewModel.onDrawerMenuSelection(menuItem) }, DRAWER_CLOSE_DELAY_MS)

        return true
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun setFeedItems(items: List<FeedItem>) {
        adapter.setItems(items)
    }

    override fun populateDrawerMenu(subscriptions: List<Subscription>) {
        val menu = binding.navigation.menu
        var order = 0
        menu.removeGroup(R.id.nav_drawer_feeds)
        for (subscription in subscriptions) {
            val menuItem = menu.add(R.id.nav_drawer_feeds, order, order++, subscription.name)
            menuItem.setIcon(R.drawable.ic_rss_feed_black)
        }
    }

    override fun showError() {
        Toast.makeText(this, R.string.errror_load_feed, Toast.LENGTH_SHORT).show()
    }

    override fun showLoaderOverlay() {
        binding.loaderOverlay.visibility = View.VISIBLE
    }

    override fun hideLoaderOverlay() {
        binding.loaderOverlay.visibility = View.GONE
    }

    override fun setTitle(name: String) {
        supportActionBar!!.title = name
    }

    override fun showFacebookShareDialog(content: ShareLinkContent) {
        ShareDialog.show(this, content)
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_feed, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        when (item.itemId) {
            R.id.share_feed -> {
                viewModel.shareFeed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}
