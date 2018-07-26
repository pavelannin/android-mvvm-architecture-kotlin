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

package com.github.anninpavel.androidarchitecture.presentation.activity.detail

import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.anninpavel.androidarchitecture.R
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity

/** @author Pavel Annin (https://github.com/anninpavel). */
class DetailViewHolder(rootViewGroup: ViewGroup) {

    private val context by lazy { rootViewGroup.context }

    private val toolbar by lazy { rootViewGroup.findViewById<Toolbar>(R.id.toolbar) }
    private val userAvatarImageView by lazy { rootViewGroup.findViewById<ImageView>(R.id.user_avatar_image) }
    private val userNameTextView by lazy { rootViewGroup.findViewById<TextView>(R.id.user_name_text) }
    private val userCompanyTextView by lazy { rootViewGroup.findViewById<TextView>(R.id.user_company_text) }
    private val userLocationTextView by lazy { rootViewGroup.findViewById<TextView>(R.id.user_location_text) }
    private val progressIndicator by lazy { rootViewGroup.findViewById<ProgressBar>(R.id.progress_indicator) }


    var isProgressIndicatorVisible: Boolean?
        get() = progressIndicator.visibility == View.VISIBLE
        set(value) = progressIndicator.run { visibility = if (value == true) View.VISIBLE else View.GONE }

    var avatarUrl: String? = null
        set(value) {
            field = value
            Glide.with(context)
                .load(value)
                .apply(RequestOptions.circleCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(userAvatarImageView)
        }

    var name: String? = null
        set(value) {
            field = value
            userNameTextView.text = value
        }

    var company: String? = null
        set(value) {
            field = value
            userCompanyTextView.text = value
            userCompanyTextView.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }

    var location: String? = null
        set(value) {
            field = value
            userLocationTextView.text = value
            userLocationTextView.visibility = if (value.isNullOrBlank()) View.GONE else View.VISIBLE
        }


    var onBackClick: (() -> Unit)? = null


    fun setup(init: DetailViewHolder.() -> Unit): DetailViewHolder {
        init()
        toolbar.setOnClickListener { onBackClick?.invoke() }
        return this
    }

    fun bind(data: UserEntity?) {
        avatarUrl = data?.avatarUrl
        name = data?.name
        company = data?.company
        location = data?.location
    }
}