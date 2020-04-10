package bg.qponer.android.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<*>> : RecyclerView.Adapter<VH>() {

    private val differ = initDiffer()

    private fun initDiffer() = AsyncListDiffer(this, object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T) =
            this@BaseAdapter.areItemsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T) =
            this@BaseAdapter.areContentsTheSame(oldItem, newItem)

    })

    protected abstract val layoutResId: Int

    override fun getItemCount(): Int = differ.currentList.size

    protected fun getItem(position: Int): T = differ.currentList[position]

    fun setData(data: List<T>) = differ.submitList(data)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
            .let { createViewHolder(it) }


    override fun onBindViewHolder(holder: VH, position: Int) {
        differ.currentList[position]
            .let { bindViewHolder(holder, it) }
    }

    protected abstract fun createViewHolder(itemView: View): VH

    protected abstract fun bindViewHolder(holder: VH, item: T)

    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}

abstract class BaseViewHolder<VDB: ViewDataBinding>(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    protected val dataBinding: VDB = DataBindingUtil.bind(itemView)
        ?: throw throw IllegalStateException("No binding for item view")
}