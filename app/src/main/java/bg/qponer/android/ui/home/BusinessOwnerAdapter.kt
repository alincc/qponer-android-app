package bg.qponer.android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bg.qponer.android.R
import bg.qponer.android.data.model.BusinessOwner
import bg.qponer.android.databinding.ListItemBusinessOwnerBinding

class BusinessOwnerAdapter : RecyclerView.Adapter<BusinessOwnerViewHolder>() {

    private val differ = AsyncListDiffer(this, BusinessOwnerDiffCallback)

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

    fun setData(data: List<BusinessOwner>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusinessOwnerViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_business_owner, parent, false)
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

typealias Callback = ((BusinessOwner) -> Unit)

class BusinessOwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val itemViewDataBinding: ListItemBusinessOwnerBinding =
        DataBindingUtil.bind(itemView)
            ?: throw IllegalStateException("No binding for item view")

    fun bind(
        item: BusinessOwner,
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

private object BusinessOwnerDiffCallback : DiffUtil.ItemCallback<BusinessOwner>() {

    override fun areItemsTheSame(oldItem: BusinessOwner, newItem: BusinessOwner) =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: BusinessOwner, newItem: BusinessOwner) =
        oldItem == newItem
}