package org.mpmg.mpapp.core

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.mpmg.mpapp.domain.network.retrofit.*
import org.mpmg.mpapp.domain.repositories.association.AssociationRepository
import org.mpmg.mpapp.domain.repositories.association.datasources.LocalAssociationDataSource
import org.mpmg.mpapp.domain.repositories.city.CityRepository
import org.mpmg.mpapp.domain.repositories.city.datasources.LocalCityDataSource
import org.mpmg.mpapp.domain.repositories.config.ConfigRepository
import org.mpmg.mpapp.domain.repositories.collect.CollectRepository
import org.mpmg.mpapp.domain.repositories.collect.datasources.*
import org.mpmg.mpapp.domain.repositories.config.datasources.LocalConfigDataSource
import org.mpmg.mpapp.domain.repositories.config.datasources.RemoteConfigDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.PublicWorkRepository
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.LocalPublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.publicwork.datasources.RemotePublicWorkDataSource
import org.mpmg.mpapp.domain.repositories.typephoto.TypePhotoRepository
import org.mpmg.mpapp.domain.repositories.typephoto.datasources.LocalTypePhotoDataSource
import org.mpmg.mpapp.domain.repositories.typework.TypeWorkRepository
import org.mpmg.mpapp.domain.repositories.typework.datasources.LocalTypeWorkDataSource
import org.mpmg.mpapp.domain.repositories.user.UserRepository
import org.mpmg.mpapp.domain.repositories.user.datasources.LocalUserDataSource
import org.mpmg.mpapp.domain.repositories.user.datasources.RemoteUserDataSource
import org.mpmg.mpapp.domain.repositories.workstatus.WorkStatusRepository
import org.mpmg.mpapp.domain.repositories.workstatus.datasources.LocalWorkStatusDataSource
import org.mpmg.mpapp.ui.viewmodels.MainActivityViewModel
import org.mpmg.mpapp.ui.screens.collect.fragments.CollectMainFragment
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel
import org.mpmg.mpapp.ui.screens.home.viewmodels.HomeViewModel
import org.mpmg.mpapp.ui.screens.login.viewmodels.CreateAccountViewModel
import org.mpmg.mpapp.ui.screens.login.viewmodels.LoginViewModel
import org.mpmg.mpapp.ui.screens.photo.viewmodels.PhotoViewModel
import org.mpmg.mpapp.ui.screens.publicwork.fragments.list.PublicWorkListFragment
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.BottomMenuViewModel
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.CrudPublicWorkViewModel
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkMapViewModel
import org.mpmg.mpapp.ui.screens.setup.viewmodels.ConfigurationViewModel
import org.mpmg.mpapp.ui.screens.upload.viewmodels.SendViewModel
import org.mpmg.mpapp.ui.viewmodels.*

val viewModelModules = module {
    // Shared View Models
    viewModel { LocationViewModel() }
    viewModel { MainActivityViewModel() }

    scope(named(PublicWorkListFragment::class.java.name)) {
        scoped { PublicWorkListViewModel(get(), get(), get()) }
    }

    scope(named(CollectMainFragment::class.java.name)) {
        scoped { CollectFragmentViewModel(get(), get(), get(), get(), get()) }
        scoped { PhotoViewModel(get(), get()) }
    }

    // ViewModels for UI
    viewModel { HomeViewModel(get()) }
    viewModel { SendViewModel(androidApplication(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { ConfigurationViewModel(androidApplication()) }
    viewModel { CollectFragmentViewModel(get(), get(), get(), get(), get()) }
    viewModel { CreateAccountViewModel(get()) }
    viewModel { CrudPublicWorkViewModel(get(), get(), get()) }
    viewModel { BottomMenuViewModel() }
    viewModel { PublicWorkMapViewModel() }
}

val repositoriesModules = module {
    single { UserRepository(get(), get()) }
    single { PublicWorkRepository(get(), get()) }
    single { TypeWorkRepository(get()) }
    single { ConfigRepository(get(), get()) }
    single { CollectRepository(get(), get(), get(), get()) }
    single { TypePhotoRepository(get()) }
    single { AssociationRepository(get()) }
    single { WorkStatusRepository(get()) }
    single { CityRepository(get()) }
}

val networkModule = module {
    factory { provideClient() }
    single { provideRetrofit(get()) }
    factory { provideMPApi(get()) }
}

val dataSourceModules = module {
    single { LocalPublicWorkDataSource(androidApplication()) }
    single { LocalTypeWorkDataSource(androidApplication()) }
    single { LocalPhotoDataSource(androidApplication()) }
    single { LocalCollectDataSource(androidApplication()) }
    single { LocalConfigDataSource(androidApplication()) }
    single { LocalTypePhotoDataSource(androidApplication()) }
    single { LocalAssociationDataSource(androidApplication()) }
    single { LocalWorkStatusDataSource(androidApplication()) }
    single { LocalCityDataSource(androidApplication()) }
    single { LocalUserDataSource(androidApplication()) }

    single { RemoteConfigDataSource(get()) }
    single { RemotePublicWorkDataSource(get()) }
    single { RemoteCollectDataSource(get()) }
    single { RemotePhotoDataSource(get()) }
    single { RemoteUserDataSource(get()) }
}