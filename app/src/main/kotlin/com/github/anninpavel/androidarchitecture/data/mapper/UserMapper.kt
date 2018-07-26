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

package com.github.anninpavel.androidarchitecture.data.mapper

import com.github.anninpavel.androidarchitecture.domain.model.User
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity

/** @author Pavel Annin (https://github.com/anninpavel). */

class UserMapper {

    fun toUserEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            login = user.login,
            name = user.name,
            company = user.company,
            location = user.location,
            avatarUrl = user.avatarUrl
        )
    }
}