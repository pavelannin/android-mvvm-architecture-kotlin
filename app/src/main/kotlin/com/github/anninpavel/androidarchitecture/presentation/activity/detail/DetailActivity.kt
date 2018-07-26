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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.anninpavel.androidarchitecture.R
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity
import com.github.anninpavel.androidarchitecture.presentation.common.Failure
import com.github.anninpavel.androidarchitecture.presentation.common.Progress
import com.github.anninpavel.androidarchitecture.presentation.common.Response
import com.github.anninpavel.androidarchitecture.presentation.common.Success
import dagger.android.AndroidInjection
import javax.inject.Inject

/** @author Pavel Annin (https://github.com/anninpavel). */
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewHolder: DetailViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val userLogin = intent?.extras?.getString(EXTRA_USER_LOGIN)
                ?: throw IllegalArgumentException("Unknown argument $EXTRA_USER_LOGIN")

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        viewHolder = DetailViewHolder(rootViewGroup = findViewById(R.id.main_container)).setup {
            onBackClick = { finish() }
        }

        viewModel.observeLoadUser()

        savedInstanceState ?: viewModel.loadUser(userLogin)
    }

    private fun DetailViewModel.observeLoadUser() {
        val observer = Observer<Response<UserEntity>> { response ->
            when (response) {
                is Progress -> viewHolder.isProgressIndicatorVisible = true
                is Success -> with(viewHolder) {
                    bind(response.value)
                    isProgressIndicatorVisible = false
                }
                is Failure -> with(viewHolder) {
                    isProgressIndicatorVisible = false
                    // TODO: Show error
                }
            }
        }
        userLiveData.observe(this@DetailActivity, observer)
    }

    companion object {

        private const val EXTRA_USER_LOGIN = "com.github.anninpavel.androidarchitecture.extras.user_login"

        fun launch(context: Context, userLogin: String) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_USER_LOGIN, userLogin)
            }
            context.startActivity(intent)
        }
    }
}