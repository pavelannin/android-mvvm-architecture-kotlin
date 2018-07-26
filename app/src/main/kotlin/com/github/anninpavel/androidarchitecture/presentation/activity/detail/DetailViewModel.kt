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

package com.github.anninpavel.androidarchitecture.presentation.activity.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.anninpavel.androidarchitecture.domain.interactor.UserUseCase
import com.github.anninpavel.androidarchitecture.domain.model.UserEntity
import com.github.anninpavel.androidarchitecture.presentation.common.Response
import com.github.anninpavel.androidarchitecture.presentation.common.failure
import com.github.anninpavel.androidarchitecture.presentation.common.loading
import com.github.anninpavel.androidarchitecture.presentation.common.success
import com.github.anninpavel.androidarchitecture.utils.SchedulerFacade
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/** @author Pavel Annin (https://github.com/anninpavel). */
class DetailViewModel @Inject constructor(
    private val userUserCase: UserUseCase,
    private val scheduler: SchedulerFacade
) : ViewModel() {

    private val rxDisposables = CompositeDisposable()
    private val _userLiveData = MutableLiveData<Response<UserEntity>>()

    val userLiveData: LiveData<Response<UserEntity>>
        get() = _userLiveData

    override fun onCleared() {
        super.onCleared()
        rxDisposables.dispose()
    }

    fun loadUser(login: String) {
        userUserCase.userByLogin(login)
            .subscribeOn(scheduler.io)
            .observeOn(scheduler.ui)
            .doOnSubscribe { _userLiveData.value = Response.loading() }
            .subscribe(
                { _userLiveData.value = Response.success(value = it) },
                { _userLiveData.value = Response.failure(error = it) },
                { _userLiveData.value = Response.failure(error = NoSuchElementException()) }
            ).addTo(rxDisposables)
    }
}