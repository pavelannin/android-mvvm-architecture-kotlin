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

package com.github.anninpavel.androidarchitecture.presentation.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.anninpavel.androidarchitecture.R
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult

/** @author Pavel Annin (https://github.com/anninpavel). */
class ItemSearchResultViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {

    private val userAvatarImageView by lazy { rootView.findViewById<ImageView>(R.id.user_avatar_image) }
    private val userLoginTextView by lazy { rootView.findViewById<TextView>(R.id.user_login_text) }

    var onClick: (() -> Unit)? = null

    fun setup(init: ItemSearchResultViewHolder.() -> Unit): ItemSearchResultViewHolder {
        init()
        itemView.setOnClickListener { onClick?.invoke() }
        return this
    }

    fun bind(data: ItemSearchResult) {
        userLoginTextView.text = data.login
        Glide.with(itemView.context)
            .load(data.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(userAvatarImageView)
    }
}