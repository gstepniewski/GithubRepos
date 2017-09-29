package com.infunade.githubrepos

import android.app.Application
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class GithubModule {
    @Provides @Singleton
    fun githubApi(): GithubApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(GithubApi::class.java)
    }
}

@Component(modules = arrayOf(GithubModule::class))
@Singleton
interface GithubComponent {
    fun inject(vm: SearchViewModel)
}

class GithubApp: Application() {

    companion object {
        lateinit var githubComponent: GithubComponent
    }

    override fun onCreate() {
        super.onCreate()
        githubComponent = DaggerGithubComponent.builder().build()
    }
}