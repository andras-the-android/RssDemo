package rssdemo.andras.hu.rssdemo.ui.subscriptions

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import rssdemo.andras.hu.rssdemo.R
import rssdemo.andras.hu.rssdemo.data.Subscription
import rssdemo.andras.hu.rssdemo.databinding.ActivitySubscriptionsBinding
import rssdemo.andras.hu.rssdemo.di.Injector
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment

class SubscriptionsActivity : AppCompatActivity(), SubscriptionsView {

    lateinit var viewModel: SubscriptionsViewModel
    lateinit var adapter: SubscriptionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
        val binding = DataBindingUtil.setContentView<ActivitySubscriptionsBinding>(this, R.layout.activity_subscriptions)
        initToolbar(binding)
        initRecyclerView(binding)
        viewModel.setView(this)
    }

    private fun initToolbar(binding: ActivitySubscriptionsBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView(binding: ActivitySubscriptionsBinding) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter
    }

    override fun setSubscriptionItems(items: List<Subscription>) {
        adapter.setItems(items)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_subscriptions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
        // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.add_subscription -> {
                openNewEditor()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openNewEditor() {
        val editor = SubscriptionEditorDialogFragment.create()
        editor.show(supportFragmentManager, "")
        supportFragmentManager.executePendingTransactions()
        editor.dialog.setOnDismissListener { viewModel.refreshData() }
    }
}
