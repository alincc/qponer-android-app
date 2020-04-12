package bg.qponer.android.ui.business

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bg.qponer.android.R
import bg.qponer.android.data.model.Business
import bg.qponer.android.databinding.ListItemBusinessBinding

internal class BusinessOwnerAdapter : RecyclerView.Adapter<BusinessOwnerViewHolder>() {

    private val differ = AsyncListDiffer(
        this,
        BusinessOwnerDiffCallback
    )

    var onItemClickListener: Callback? = null
    var onItemLeaderButtonClickListener: Callback? = null
    var onItemBuyVoucherClickListener: Callback? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].id
    }

    fun setData(data: List<Business>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessOwnerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_business, parent, false)
        return BusinessOwnerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BusinessOwnerViewHolder, position: Int) =
        holder.bind(
            differ.currentList[position],
            onItemClickListener,
            onItemLeaderButtonClickListener,
            onItemBuyVoucherClickListener
        )
}

typealias Callback = ((Business) -> Unit)

internal class BusinessOwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val itemViewDataBinding: ListItemBusinessBinding =
        DataBindingUtil.bind(itemView)
            ?: throw IllegalStateException("No binding for item view")

    fun bind(
        item: Business,
        onClickListener: Callback?,
        onItemLeaderButtonClickListener: Callback?,
        onItemBuyVoucherClickListener: Callback?
    ) {
        itemViewDataBinding.apply {
            this.root.setOnClickListener { onClickListener?.let { it(item) } }
            this.businessOwnerViewLeadersButton.setOnClickListener {
                onItemLeaderButtonClickListener?.let {
                    it(
                        item
                    )
                }
            }
            this.businessOwnerBuyVoucherButton.setOnClickListener {
                onItemBuyVoucherClickListener?.let {
                    it(
                        item
                    )
                }
            }
            this.item = item
        }.also {
            it.executePendingBindings()
        }
    }

}

private object BusinessOwnerDiffCallback : DiffUtil.ItemCallback<Business>() {

    override fun areItemsTheSame(oldItem: Business, newItem: Business) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Business, newItem: Business) =
        oldItem == newItem
}