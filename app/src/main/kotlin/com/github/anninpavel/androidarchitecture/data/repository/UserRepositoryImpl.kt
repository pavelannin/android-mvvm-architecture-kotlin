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

import com.github.anninpavel.androidarchitecture.data.local.Database
import com.github.anninpavel.androidarchitecture.data.mapper.UserMapper
import com.github.anninpavel.androidarchitecture.data.remote.ApiCommander
import com.github.anninpavel.androidarchitecture.domain.model.User
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity
import com.github.anninpavel.androidarchitecture.domain.repository.UserRepository
import io.reactivex.Maybe

/** @author Pavel Annin (https://github.com/anninpavel). */
class UserRepositoryImpl(
    private val db: Database,
    private val api: ApiCommander,
    private val mapper: UserMapper
) : UserRepository {

    override fun fetchUserByLogin(login: String): Maybe<UserEntity> {
        return Maybe.concat(
            userCacheByLogin(login),
            api.userApi.loadUser(login).flatMapMaybe { saveUser(user = it) }
        ).firstElement()
    }

    private fun userCacheByLogin(login: String): Maybe<UserEntity> {
        return Maybe.fromCallable { db.userDao().byLogin(login) }
    }

    private fun saveUser(user: User): Maybe<UserEntity> {
        return Maybe.fromCallable {
            mapper.toUserEntity(user).also {
                db.userDao().insert(element = it)
            }
        }
    }
}