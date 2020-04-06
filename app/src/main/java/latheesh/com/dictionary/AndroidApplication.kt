package latheesh.com.dictionary

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import latheesh.com.dictionary.di.DaggerAppComponent

class AndroidApplication :DaggerApplication(){
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}