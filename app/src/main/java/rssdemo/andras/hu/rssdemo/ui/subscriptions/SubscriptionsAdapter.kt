package rssdemo.andras.hu.rssdemo.ui.subscriptions

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import rssdemo.andras.hu.rssdemo.R
import rssdemo.andras.hu.rssdemo.data.Subscription
import rssdemo.andras.hu.rssdemo.databinding.ListItemSubscriptionBinding
import rssdemo.andras.hu.rssdemo.ui.subscriptions.editor.SubscriptionEditorDialogFragment

class SubscriptionsAdapter(private val activity: AppCompatActivity, private val viewModel: SubscriptionsViewModel) : RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder>() {

    private var items = emptyList<Subscription>()

    internal fun setItems(items: List<Subscription>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ListItemSubscriptionBinding.inflate(layoutInflater, parent, false)
        return SubscriptionsAdapter.ViewHolder(activity, itemBinding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder internal constructor(private val activity: AppCompatActivity,
                                          private val binding: ListItemSubscriptionBinding,
                                          private val viewModel: SubscriptionsViewModel) : RecyclerView.ViewHolder(binding.getRoot()) {

        internal fun bindModel(subscription: Subscription) {
            binding.subscription = subscription
            binding.handler = this
        }

        fun onOptionsClick() {
            showPopupMenu()
        }

        private fun showPopupMenu() {
            val popup = PopupMenu(activity, binding.options)
            popup.inflate(R.menu.menu_subscription_item)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_edit -> showEditorDialog()
                    R.id.menu_delete -> showDeleteDialog()
                }
                false
            }
            popup.show()
        }

        private fun showEditorDialog() {
            val editor = SubscriptionEditorDialogFragment.create(binding.subscription)
            editor.show(activity.supportFragmentManager, "")
            activity.supportFragmentManager.executePendingTransactions()
            editor.dialog.setOnDismissListener { viewModel.refreshData() }
        }

        private fun showDeleteDialog() {
            AlertDialog.Builder(activity)
                    .setMessage(R.string.delete_confirmaion_message)
                    .setPositiveButton(R.string.yes) { _, _ -> viewModel.deleteSubscription(binding.subscription.name) }
                    .setNegativeButton(R.string.no) { _, _ -> }
                    .create().show()
        }
    }
}
