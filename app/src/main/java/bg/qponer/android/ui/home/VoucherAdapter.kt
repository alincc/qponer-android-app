package bg.qponer.android.ui.home

import android.view.View
import bg.qponer.android.R
import bg.qponer.android.data.model.Voucher
import bg.qponer.android.databinding.ListItemVoucherBinding
import bg.qponer.android.ui.BaseAdapter
import bg.qponer.android.ui.BaseViewHolder

internal class VoucherAdapter : BaseAdapter<Voucher, VoucherViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override val layoutResId = R.layout.list_item_voucher

    override fun getItemId(position: Int) = getItem(position).id

    override fun createViewHolder(itemView: View) = VoucherViewHolder(itemView)

    override fun bindViewHolder(holder: VoucherViewHolder, item: Voucher) = holder.bind(item)

    override fun areItemsTheSame(oldItem: Voucher, newItem: Voucher) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Voucher, newItem: Voucher) =
        oldItem == newItem
}

internal class VoucherViewHolder(
    itemView: View
) : BaseViewHolder<ListItemVoucherBinding>(itemView) {

    fun bind(item: Voucher) {
        dataBinding.apply {
            this.item = item
        }.also {
            it.executePendingBindings()
        }
    }
}