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

package com.github.anninpavel.androidarchitecture.data.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.github.anninpavel.androidarchitecture.data.remote.ApiCommander
import com.github.anninpavel.androidarchitecture.data.repository.common.PagedRepository
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import com.github.anninpavel.androidarchitecture.domain.repository.SearchRepository
import com.github.anninpavel.androidarchitecture.utils.SchedulerFacade

/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchRepositoryImpl(
    private val api: ApiCommander,
    private val scheduler: SchedulerFacade
) : SearchRepository, PagedRepository {

    override fun searchUserByName(name: String, pageSize: Int): LiveData<PagedList<ItemSearchResult>> {
        return createPaged(pageSize, scheduler) { page, limit ->
            api.searchApi.search(name, page, limit)
                .map { it.items }
        }
    }
}