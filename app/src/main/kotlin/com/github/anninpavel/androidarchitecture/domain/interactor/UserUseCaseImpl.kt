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

package com.github.anninpavel.androidarchitecture.domain.interactor

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.github.anninpavel.androidarchitecture.domain.model.ItemSearchResult
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity
import com.github.anninpavel.androidarchitecture.domain.repository.SearchRepository
import com.github.anninpavel.androidarchitecture.domain.repository.UserRepository
import io.reactivex.Maybe

/** @author Pavel Annin (https://github.com/anninpavel). */
class UserUseCaseImpl(
    private val search: SearchRepository,
    private val user: UserRepository
) : UserUseCase {

    override fun searchUserByLogin(login: String): LiveData<PagedList<ItemSearchResult>> {
        return search.searchUserByName(login, PAGE_SIZE)
    }

    override fun userByLogin(login: String): Maybe<UserEntity> {
        return user.fetchUserByLogin(login)
    }

    private companion object {
        private const val PAGE_SIZE = 10
    }
}