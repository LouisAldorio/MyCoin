package com.catnip.mycoin.ui.coinlist;

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.catnip.mycoin.R
import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.databinding.ItemCoinBinding


class CoinListAdapter(private val itemClick: (Coin) -> Unit) :
    RecyclerView.Adapter<CoinListAdapter.CoinViewHolder>() {


    private var items: MutableList<Coin> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Coin>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(items: List<Coin>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class CoinViewHolder(private val binding: ItemCoinBinding, val itemClick: (Coin) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Coin) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.ivIconCoin.load(image)
                binding.tvCoinName.text = name
                binding.tvConSymbol.text = symbol
                binding.tvCoinPrice.text = itemView.context.getString(R.string.text_placeholder_coin_price, currentPrice.toString())
            }
        }
    }
}