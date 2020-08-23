package com.myans.newsportal.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.databinding.DialogCountryListBinding
import com.myans.newsportal.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListDialog(var countryId: String) : BottomSheetDialogFragment() {

    interface CountryListener {
        fun onCountryClicked(country: Country)
    }

    companion object {
        const val TAG = "CountryListBottomSheet"
    }

    private var binding: DialogCountryListBinding by autoCleared()
    private val viewModel: CountryListViewModel by viewModels()
    private lateinit var adapter: CountryAdapter
    lateinit var listener: CountryListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        binding = DialogCountryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        val list = viewModel.getCountryList()
        list.find {
            it.countryId == countryId
        }?.selected = true
        adapter.setItems(list)
    }

    private fun setupRecyclerView() {
        adapter = CountryAdapter(listener)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }
}