package com.example.newsApp.application

import com.example.newsApp.database.core.AppDataBase
import com.example.newsApp.database.daos.NewsDao
import com.example.newsApp.datasources.NewsDataSource
import com.example.newsApp.datasources.NewsDataSourceImpl
import com.example.newsApp.repositories.NewsRepository
import com.example.newsApp.repositories.NewsRepositoryImpl
import com.example.newsApp.viewmodels.EditViewModel
import com.example.newsApp.viewmodels.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<AppDataBase>{AppDataBase(context = get())}
    single <NewsDao> {get<AppDataBase>().newsDao()}
}

val newsRepositoryModule = module {
    includes(databaseModule)

    single<NewsDataSource> { NewsDataSourceImpl(get<NewsDao>()) }
    single<NewsRepository> { NewsRepositoryImpl(get<NewsDataSource>()) }
}

val appModule = module {
    includes(newsRepositoryModule)

    viewModel { HomeViewModel(get<NewsRepository>())}
    viewModel { EditViewModel(get<NewsRepository>()) }
}