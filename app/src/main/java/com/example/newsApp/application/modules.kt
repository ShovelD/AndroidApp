package com.example.newsApp.application

import com.example.newsApp.database.core.AppDataBase
import com.example.newsApp.database.daos.NewsDao
import com.example.newsApp.datasources.NewsDataSource
import com.example.newsApp.datasources.NewsDataSourceImpl
import com.example.newsApp.datasources.RemoteDataSourceImpl
import com.example.newsApp.datasources.RemoteNewsDataSource
import com.example.newsApp.httpClientBuilder
import com.example.newsApp.json
import com.example.newsApp.repositories.NewsRepository
import com.example.newsApp.repositories.NewsRepositoryImpl
import com.example.newsApp.repositories.RemoteNewsRepository
import com.example.newsApp.repositories.RemoteNewsRepositoryImpl
import com.example.newsApp.viewmodels.EditViewModel
import com.example.newsApp.viewmodels.HomeViewModel
import com.example.newsApp.viewmodels.RemoteViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single<AppDataBase>{AppDataBase(context = get())}
    single <NewsDao> {get<AppDataBase>().newsDao()}
}

val siteDataBaseModule = module{
    single<HttpClient>{ httpClientBuilder(json)}
}

val newsRepositoryModule = module {
    includes(databaseModule)

    single<NewsDataSource> { NewsDataSourceImpl(get<NewsDao>()) }
    single<NewsRepository> { NewsRepositoryImpl(get<NewsDataSource>()) }
}

val remoteRepositoryModule = module {
    includes(siteDataBaseModule)

    single <RemoteNewsDataSource>{RemoteDataSourceImpl(get<HttpClient>())}
    single <RemoteNewsRepository>{RemoteNewsRepositoryImpl(get<RemoteNewsDataSource>())}
}

val appModule = module {
    includes(newsRepositoryModule)
    includes(remoteRepositoryModule)

    viewModel { HomeViewModel(get<NewsRepository>())}
    viewModel { EditViewModel(get<NewsRepository>()) }
    viewModel{RemoteViewModel(get<RemoteNewsRepository>(),get<NewsRepository>())}
}