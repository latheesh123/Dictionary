package latheesh.com.dictionary.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import latheesh.com.dictionary.viewmodel.DictionaryViewModel

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DictionaryViewModel::class)
    abstract fun bindSettingsViewModel(discoverViewModel: DictionaryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}