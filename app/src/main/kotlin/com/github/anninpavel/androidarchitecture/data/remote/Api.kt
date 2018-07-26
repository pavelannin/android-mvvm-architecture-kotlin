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

package com.github.anninpavel.androidarchitecture.data.remote

import com.github.anninpavel.androidarchitecture.domain.model.SearchResult
import com.github.anninpavel.androidarchitecture.domain.model.User
import io.reactivex.Single

/** @author Pavel Annin (https://github.com/anninpavel). */
class SearchApi(client: HttpClient) {

    private val service by lazy { client.retrofit.create(SearchService::class.java) }

    fun search(query: String, page: Int, limit: Int): Single<SearchResult> = service.search(query, page, limit)
}

/** @author Pavel Annin (https://github.com/anninpavel). */
class UserApi(client: HttpClient) {

    private val service by lazy { client.retrofit.create(UserService::class.java) }

    fun loadUser(userLogin: String): Single<User> = service.loadUser(userLogin)
}