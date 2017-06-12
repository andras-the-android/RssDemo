package rssdemo.andras.hu.rssdemo.ui.feed


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import rssdemo.andras.hu.rssdemo.data.FeedItem
import rssdemo.andras.hu.rssdemo.databinding.ListItemFeedBinding
import rssdemo.andras.hu.rssdemo.ui.Navigator

class FeedAdapter(private val navigator: Navigator) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private var items = emptyList<FeedItem>()

    internal fun setItems(items: List<FeedItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ListItemFeedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding, navigator)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder internal constructor(private val binding: ListItemFeedBinding,
                                          private val navigator: Navigator) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.handler = this
            binding.webview.settings.loadWithOverviewMode = true
            binding.webview.setInitialScale(180)
        }

        internal fun bindModel(item: FeedItem) {
            binding.feedItem = item
            val summary = "<html><body>" + item.description + "</body></html>"
            binding.webview.loadData(summary, "text/html", null)
        }

        fun onTitleClick() {
            navigator.goToUrl(binding.feedItem.link)
        }
    }


}
