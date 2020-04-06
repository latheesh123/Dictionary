package latheesh.com.dictionary.di

import dagger.Module
import dagger.Provides
import latheesh.com.dictionary.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {


    @Singleton
    @Provides
    internal fun provideRetrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder().baseUrl(base_Url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideDictionaryService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    companion object {
        const val base_Url = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
    }

}