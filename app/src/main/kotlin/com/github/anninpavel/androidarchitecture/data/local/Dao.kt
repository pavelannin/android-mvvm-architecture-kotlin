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

package com.github.anninpavel.androidarchitecture.data.local

import android.arch.persistence.room.*
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity

/** @author Pavel Annin (https://github.com/anninpavel). */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg elements: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: T): Long

    @Update
    fun update(element: T): Int

    @Delete
    fun delete(element: T): Int

    @Delete
    fun deleteAll(vararg elements: T)
}

/** @author Pavel Annin (https://github.com/anninpavel). */
@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM users WHERE login = :login")
    fun byLogin(login: String): UserEntity?
}