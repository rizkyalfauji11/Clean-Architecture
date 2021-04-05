package id.code.alpha.data.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import id.code.alpha.data.BuildConfig
import id.code.alpha.data.network.ApiServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        const val QUALIFIER_OKHTTP_BUILDER = "OKHTTP_BUILDER"
        const val QUALIFIER_RETROFIT_API = "RETROFIT_API"
        const val QUALIFIER_MOCK_API = "QUALIFIER_MOCK_API"

        const val HOSPITAL_API = "HOSPITAL_API"
        const val MOCK_HOSPITAL_API = "MOCK_HOSPITAL_API"
        const val QUALIFIER_GSON = "Gson"

        const val TIME_OUT_LIMIT = 20
    }

    @Singleton
    @Provides
    @Named(QUALIFIER_OKHTTP_BUILDER)
    fun provideOKHttpBuilder(
        context: Context
    ): OkHttpClient.Builder {
        return if (BuildConfig.FLAVOR == "development" || BuildConfig.FLAVOR == "staging" || BuildConfig.FLAVOR == "uat") {
            OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(ChuckInterceptor(context))
        } else {
            OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_LIMIT.toLong(), TimeUnit.SECONDS)
        }
    }

    @Singleton
    @Provides
    @Named(QUALIFIER_RETROFIT_API)
    fun provideRetrofitApi(
        @Named(QUALIFIER_GSON) gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL)
    }

    @Singleton
    @Provides
    @Named(QUALIFIER_GSON)
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Singleton
    @Provides
    @Named(QUALIFIER_MOCK_API)
    fun provideMockApi(
        @Named(QUALIFIER_GSON) gson: Gson
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_URL_MOCK)
    }

    @Singleton
    @Provides
    @Named(HOSPITAL_API)
    fun provideHospitalApi(
        @Named(QUALIFIER_OKHTTP_BUILDER) clientBuilder: OkHttpClient.Builder,
        @Named(QUALIFIER_RETROFIT_API) builder: Retrofit.Builder
    ): ApiServices {
        return builder.client(clientBuilder.build())
            .build()
            .create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    @Named(MOCK_HOSPITAL_API)
    fun provideBankApiMockJuloOne(
        @Named(QUALIFIER_OKHTTP_BUILDER) clientBuilder: OkHttpClient.Builder,
        @Named(QUALIFIER_MOCK_API) builder: Retrofit.Builder
    )
            : ApiServices {
        return builder
            .client(clientBuilder.build())
            .build()
            .create(ApiServices::class.java)
    }
}