package com.heapix.calendarific

import android.app.Application
import android.content.SharedPreferences
import com.heapix.calendarific.net.repo.HolidaysRepo
import com.heapix.calendarific.net.services.ApiRest
import com.heapix.calendarific.net.services.HolidaysService
import com.heapix.calendarific.utils.pref.PreferencesUtils
import com.heapix.calendarific.utils.rx.AppSchedulerProvider
import com.heapix.calendarific.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.*
import retrofit2.Retrofit

private lateinit var kodeinStored: DI

class MyApp : Application() {

    private val settingModule = DI.Module("settings Module") {
        bind<Retrofit>() with singleton { ApiRest.getApi() }

        bind<SharedPreferences>() with singleton {
            PreferencesUtils.getSharedPreferences(applicationContext)
        }

        bind<CompositeDisposable>() with provider { CompositeDisposable() }

        bind<SchedulerProvider>() with singleton { AppSchedulerProvider() }

        bind<HolidaysRepo>() with singleton {
            HolidaysRepo(
                instance<Retrofit>().create(
                    HolidaysService::class.java
                ),
                instance()
            )
        }
    }

    companion object {
        var kodein: DI
            get() = kodeinStored
            set(_) {}

        fun isKodeinInitialized() = ::kodeinStored.isInitialized

    }

    override fun onCreate() {
        super.onCreate()

        if (::kodeinStored.isInitialized.not())
            kodeinStored = DI {
                import(settingModule)
            }
    }

}