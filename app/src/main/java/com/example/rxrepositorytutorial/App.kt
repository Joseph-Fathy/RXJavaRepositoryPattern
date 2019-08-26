package com.example.rxrepositorytutorial

import android.app.Application
import androidx.room.Room
import com.example.rxrepositorytutorial.api.BASE_URL
import com.example.rxrepositorytutorial.api.CONNECTION_TIMEOUT_SECONDS
import com.example.rxrepositorytutorial.api.UserApi
import com.example.rxrepositorytutorial.db.AppDatabase
import com.example.rxrepositorytutorial.user.repo.UserLocalDS
import com.example.rxrepositorytutorial.user.repo.UserRemoteDS
import com.example.rxrepositorytutorial.user.repo.UserRepository
import com.example.rxrepositorytutorial.user.view_model.UsersVM
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit


class App : Application() {

    //For the sake of simplicity, for now we use this instead of Dagger
    companion object {
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var retrofit: Retrofit
        private lateinit var userApi: UserApi
        private lateinit var appDatabase: AppDatabase
        private lateinit var userRepository: UserRepository
        private lateinit var usersVM: UsersVM
        private lateinit var gson: Gson

        fun injectUserApi() = userApi
        fun injectUserDao() = appDatabase.userDao()
        fun injectUsersViewModel() = usersVM
        fun injectGson() = gson
    }

    override fun onCreate() {
        super.onCreate()

        gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setPrettyPrinting()
            .setVersion(1.0)
            .create()

        val logging = HttpLoggingInterceptor()

        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()


        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()

        userApi = retrofit.create(UserApi::class.java)
        appDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "RXRepoTest").build()


        userRepository = UserRepository(
            UserRemoteDS(),
            UserLocalDS()
        )
        usersVM = UsersVM(userRepository)


    }
}