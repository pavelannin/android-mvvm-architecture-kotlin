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

import android.arch.paging.PagedList
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.anninpavel.androidarchitecture.R
import android.support.v7.widget.DefaultItemAnimator
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import com.github.anninpavel.androidarchitecture.presentation.adapter.SearchResultAdapter


/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchViewHolder(rootViewGroup: ViewGroup) {

    private val recyclerView by lazy { rootViewGroup.findViewById<RecyclerView>(R.id.recycler_view) }
    private val searchResultAdapter by lazy { SearchResultAdapter() }

    var onItemSearchResultClick: ((ItemSearchResult) -> Unit)? = null

    init {
        with(recyclerView) {
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            adapter = searchResultAdapter
        }
    }

    fun setup(init: SearchViewHolder.() -> Unit): SearchViewHolder {
        init()
        searchResultAdapter.onItemClick = onItemSearchResultClick
        return this
    }

    fun bindSearchResult(data: PagedList<ItemSearchResult>?) {
        searchResultAdapter.submitList(data)
    }
}