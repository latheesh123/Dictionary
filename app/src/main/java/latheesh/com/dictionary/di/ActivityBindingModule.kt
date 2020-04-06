package latheesh.com.dictionary.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import latheesh.com.dictionary.ui.DictionaryActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector()
    internal abstract fun bindingDictionaryActivity(): DictionaryActivity

}