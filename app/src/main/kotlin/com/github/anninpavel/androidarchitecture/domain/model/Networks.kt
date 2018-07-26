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

package com.github.anninpavel.androidarchitecture.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/** @author Pavel Annin (https://github.com/anninpavel). */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ItemSearchResult(
    @JsonProperty(value = "id", required = true) val id: Long,
    @JsonProperty(value = "login", required = true) val login: String,
    @JsonProperty(value = "avatar_url", required = true) val avatarUrl: String
)

/** @author Pavel Annin (https://github.com/anninpavel). */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchResult(
    @JsonProperty(value = "items", required = true) val items: List<ItemSearchResult>
)

/** @author Pavel Annin (https://github.com/anninpavel). */
@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    @JsonProperty(value = "id", required = true) val id: Long,
    @JsonProperty(value = "login", required = true) val login: String,
    @JsonProperty(value = "name", required = false) val name: String?,
    @JsonProperty(value = "company", required = false) val company: String?,
    @JsonProperty(value = "location", required = false) val location: String?,
    @JsonProperty(value = "avatar_url", required = false) val avatarUrl: String?
)