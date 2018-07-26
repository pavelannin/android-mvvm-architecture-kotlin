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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/** @author Pavel Annin (https://github.com/anninpavel). */
interface SearchService {

    @GET(value = "search/users")
    fun search(
        @Query(value = "q") query: String,
        @Query(value = "page") page: Int,
        @Query(value = "per_page") limit: Int
    ): Single<SearchResult>
}

/** @author Pavel Annin (https://github.com/anninpavel). */
interface UserService {

    @GET(value = "users/{user_login}")
    fun loadUser(
        @Path(value = "user_login") userLogin: String
    ): Single<User>
}