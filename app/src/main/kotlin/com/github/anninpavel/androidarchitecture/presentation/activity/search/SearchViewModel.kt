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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.github.anninpavel.androidarchitecture.domain.interactor.UserUseCase
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import javax.inject.Inject

/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchViewModel @Inject constructor(
    userUserCase: UserUseCase
) : ViewModel() {

    private val paramLiveData = MutableLiveData<String>()
    private val searchLiveData by lazy { map(paramLiveData) { userUserCase.searchUserByLogin(login = it) } }

    val resultSearchLiveData: LiveData<PagedList<ItemSearchResult>> by lazy { switchMap(searchLiveData) { it } }

    fun searchUsers(query: String) {
        paramLiveData.value = query
    }
}