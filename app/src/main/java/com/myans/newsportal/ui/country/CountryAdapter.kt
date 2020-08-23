package com.myans.newsportal.ui.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.databinding.CountryItemBinding

class CountryAdapter(private val listener: CountryListDialog.CountryListener): RecyclerView.Adapter<CountryViewHolder>() {

    private var items: List<Country> = emptyList()

    fun setItems(items: List<Country>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding: CountryItemBinding = CountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) = holder.bind(items[position])
}

class CountryViewHolder(private val itemBinding: CountryItemBinding, private val listener: CountryListDialog.CountryListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var country: Country

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: Country) {
        this.country = item
        itemBinding.countryName.text = item.name
        Glide.with(itemBinding.root)
            .load(item.flagUrl)
            .into(itemBinding.countryFlag)
        itemBinding.isChecked.visibility = if (item.selected) View.VISIBLE else View.GONE
    }

    override fun onClick(p0: View?) {
        listener.onCountryClicked(country)
    }

}
