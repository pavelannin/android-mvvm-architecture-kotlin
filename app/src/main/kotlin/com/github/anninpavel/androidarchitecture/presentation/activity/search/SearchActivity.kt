/*
 * Copyright 2018 Pavel Annin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.anninpavel.androidarchitecture.presentation.activity.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.anninpavel.androidarchitecture.R
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import com.github.anninpavel.androidarchitecture.presentation.activity.detail.DetailActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    private lateinit var viewHolder: SearchViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        viewHolder = SearchViewHolder(rootViewGroup = findViewById(R.id.main_container)).setup {
            onItemSearchResultClick = { openUser(user = it) }
        }

        viewModel.observeSearchResult()

        savedInstanceState ?: viewModel.searchUsers(query = "Иван")
    }

    private fun openUser(user: ItemSearchResult) {
        DetailActivity.launch(this, user.login)
    }

    private fun SearchViewModel.observeSearchResult() {
        val observer = Observer<PagedList<ItemSearchResult>> {
            viewHolder.bindSearchResult(data = it)
        }
        resultSearchLiveData.observe(this@SearchActivity, observer)
    }
}