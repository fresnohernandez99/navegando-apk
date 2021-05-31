package cu.fresnohernandez99.app.navegando.di.module

import cu.fresnohernandez99.app.navegando.data.api.ApiClient
import cu.fresnohernandez99.app.navegando.data.repository.ApiRepository
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideApiRepository(apiClient: ApiClient, prefsStore: PrefsStore): ApiRepository {
        return ApiRepository(apiClient, prefsStore)
    }

}