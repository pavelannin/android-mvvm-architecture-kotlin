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

package com.github.anninpavel.androidarchitecture.presentation.adapter

import android.arch.paging.PagedListAdapter
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.anninpavel.androidarchitecture.R
import com.github.anninpavel.androidarchitecture.presentation.viewholder.ItemSearchResultViewHolder


/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchResultAdapter(
    var onItemClick: ((ItemSearchResult) -> Unit)? = null
) : PagedListAdapter<ItemSearchResult, ItemSearchResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchResultViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ItemSearchResultViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ItemSearchResultViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.setup {
                onClick = { onItemClick?.invoke(item) }
                bind(item)
            }
        }
    }

    private companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemSearchResult>() {
            override fun areItemsTheSame(oldItem: ItemSearchResult, newItem: ItemSearchResult): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemSearchResult, newItem: ItemSearchResult): Boolean {
                return oldItem == newItem
            }
        }
    }
}