package com.myans.newsportal.ui.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myans.newsportal.R
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.databinding.FragmentListBinding
import com.myans.newsportal.ui.country.CountryListDialog
import com.myans.newsportal.utils.DebouncingQueryTextListener
import com.myans.newsportal.utils.Resource
import com.myans.newsportal.utils.autoCleared
import com.myans.newsportal.utils.fromObjectToString
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class NewsListFragment: Fragment(), NewsAdapter.NewsItemListener {

    private var binding: FragmentListBinding by autoCleared()
    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.m_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnCloseListener {
                viewModel.getAllNews()
                true
            }
            maxWidth = Integer.MAX_VALUE
            setOnQueryTextListener(
                DebouncingQueryTextListener(
                    this@NewsListFragment.lifecycle
                ) { newText ->
                    newText?.let {
                        if (!it.equals(viewModel.getKeyword()))
                            viewModel.getAllNews(it)
                    }
                }
            )
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.buttonChangeCountry.setOnClickListener {
            showCountryDialog()
        }
        binding.buttonChangeCountry.text = viewModel.selectedCountry.name
        viewModel.getAllNews()
        setHasOptionsMenu(true)
    }

    private fun showCountryDialog() {
        val dialog = CountryListDialog(viewModel.selectedCountry.countryId)
        dialog.listener = object :CountryListDialog.CountryListener {
            override fun onCountryClicked(country: Country) {
                dialog.dismiss()
                binding.buttonChangeCountry.text = country.name
                viewModel.saveSelectedCountry(country)
                viewModel.getAllNews()
            }
        }
        dialog.show(parentFragmentManager, CountryListDialog.TAG)
    }


    private fun setupRecyclerView() {
        adapter = NewsAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.news.observe(viewLifecycleOwner, Observer {
            if (viewModel.getKeyword().isEmpty())
                binding.layoutChangeCountry.visibility = View.VISIBLE
            else
                binding.layoutChangeCountry.visibility = View.GONE
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(it.data)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    adapter.setItems(emptyList())
                }
            }
        })
    }

    override fun onCLickedNews(newsItem: News) {
        Timber.d("clicked")

        findNavController().navigate(
            R.id.action_list_fragment_to_detail_fragment,
            bundleOf("news_item" to fromObjectToString(newsItem))
        )
    }
}