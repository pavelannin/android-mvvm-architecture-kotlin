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
@file:JvmMultifileClass

package com.github.anninpavel.androidarchitecture.data.repository.common

import android.arch.lifecycle.LiveData
import android.arch.paging.*
import android.support.annotation.MainThread
import com.github.anninpavel.androidarchitecture.utils.SchedulerFacade
import io.reactivex.Single

/** @author Pavel Annin (https://github.com/anninpavel). */
interface PagedRepository {

    @MainThread
    fun <T> createPaged(
        pageSize: Int,
        scheduler: SchedulerFacade,
        loadFactory: (offset: Int, limit: Int) -> Single<List<T>>
    ): LiveData<PagedList<T>> {
        val sourceFactory = PagedDataSourceFactory(loadFactory, scheduler)

        val pagingConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize)
            .setPageSize(pageSize)
            .build()

        return LivePagedListBuilder(sourceFactory, pagingConfig).build()
    }
}

/** @author Pavel Annin (https://github.com/anninpavel). */
class PagedDataSourceFactory<T>(
    private val loadFactory: (offset: Int, limit: Int) -> Single<List<T>>,
    private val scheduler: SchedulerFacade
) : DataSource.Factory<Int, T>() {

    override fun create(): DataSource<Int, T> {
        return PagedDataSource(loadFactory, scheduler)
    }
}

/** @author Pavel Annin (https://github.com/anninpavel). */
class PagedDataSource<T>(
    private val loadFactory: (page: Int, limit: Int) -> Single<List<T>>,
    private val scheduler: SchedulerFacade
) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        val page = 1
        loadFactory.invoke(page, params.requestedLoadSize)
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe(
                { callback.onResult(it, null, page + 1) },
                { /* Empty. */ }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        loadFactory.invoke(params.key, params.requestedLoadSize)
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .subscribe(
                { callback.onResult(it, params.key + 1) },
                { /* Empty. */ }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // Ignored, since we only ever append to our initial load
    }
}