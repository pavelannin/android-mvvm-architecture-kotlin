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

package com.github.anninpavel.androidarchitecture.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.github.anninpavel.androidarchitecture.presentation.activity.search.SearchActivity
import com.github.anninpavel.androidarchitecture.utils.SchedulerFacade
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import android.arch.persistence.room.Room
import com.github.anninpavel.androidarchitecture.Application
import com.github.anninpavel.androidarchitecture.BuildConfig
import com.github.anninpavel.androidarchitecture.data.local.Database
import com.github.anninpavel.androidarchitecture.data.mapper.UserMapper
import com.github.anninpavel.androidarchitecture.data.remote.ApiCommander
import com.github.anninpavel.androidarchitecture.data.remote.HttpClient
import com.github.anninpavel.androidarchitecture.data.repository.SearchRepositoryImpl
import com.github.anninpavel.androidarchitecture.data.repository.UserRepositoryImpl
import com.github.anninpavel.androidarchitecture.domain.interactor.UserUseCase
import com.github.anninpavel.androidarchitecture.domain.interactor.UserUseCaseImpl
import com.github.anninpavel.androidarchitecture.domain.repository.SearchRepository
import com.github.anninpavel.androidarchitecture.domain.repository.UserRepository
import com.github.anninpavel.androidarchitecture.presentation.activity.detail.DetailActivity
import com.github.anninpavel.androidarchitecture.presentation.activity.detail.DetailViewModel
import com.github.anninpavel.androidarchitecture.presentation.activity.search.SearchViewModel
import dagger.Binds
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import android.app.Application as AndroidApplication


/** @author Pavel Annin (https://github.com/anninpavel). */
@Module(
    includes = [ViewModelModule::class]
)
class ApplicationModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSchedulerFacade(): SchedulerFacade {
        return SchedulerFacade()
    }

    @Singleton
    @Provides
    fun provideApi(): ApiCommander {
        val client = HttpClient(baseUrl = BuildConfig.API_BASE_URL, isDebugging = BuildConfig.DEBUG)
        return ApiCommander(client)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "example.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchRepository(api: ApiCommander, scheduler: SchedulerFacade): SearchRepository {
        return SearchRepositoryImpl(api, scheduler)
    }

    @Singleton
    @Provides
    fun provideUserRepository(db: Database, api: ApiCommander): UserRepository {
        return UserRepositoryImpl(db, api, mapper = UserMapper())
    }

    @Singleton
    @Provides
    fun provideUserUseCase(search: SearchRepository, user: UserRepository): UserUseCase {
        return UserUseCaseImpl(search, user)
    }
}

/** @author Pavel Annin (https://github.com/anninpavel). */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun bindDetailActivity(): DetailActivity
}

/** @author Pavel Annin (https://github.com/anninpavel). */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(value = SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = DetailViewModel::class)
    abstract fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}