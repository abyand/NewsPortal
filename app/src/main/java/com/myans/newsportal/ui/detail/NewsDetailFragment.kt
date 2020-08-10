package com.myans.newsportal.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myans.newsportal.R
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.databinding.FragmentDetailBinding
import com.myans.newsportal.utils.autoCleared
import com.myans.newsportal.utils.fromStringToObject
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type


@AndroidEntryPoint
class NewsDetailFragment: Fragment() {

    private var binding: FragmentDetailBinding by autoCleared()
    private val viewModel: NewsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type: Type = object : TypeToken<News?>() {}.type
        arguments?.getString("news_item")?.let { viewModel.newsDetail = fromStringToObject(it, type) }
        bindView()
    }

    private fun bindView() {
        binding.name.text = viewModel.newsDetail?.title
        binding.speciesAndStatus.text = viewModel.newsDetail?.content
        Glide.with(binding.root)
            .load(viewModel.newsDetail?.urlToImage)
            .placeholder(R.drawable.placeholder_news)
            .into(binding.image)
    }

}