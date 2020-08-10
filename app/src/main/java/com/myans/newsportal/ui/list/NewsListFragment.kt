package com.myans.newsportal.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myans.newsportal.R
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.databinding.FragmentListBinding
import com.myans.newsportal.utils.Resource
import com.myans.newsportal.utils.autoCleared
import com.myans.newsportal.utils.fromObjectToString
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsListFragment: Fragment(), NewsAdapter.NewsItemListener {

    private var binding: FragmentListBinding by autoCleared()
    private val viewModel: NewsListViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

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
        binding.changeCountry.setOnClickListener {
            viewModel.country = "us"
            setupObservers()
        }
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.news.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
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