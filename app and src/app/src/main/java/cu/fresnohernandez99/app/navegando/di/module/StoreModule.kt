package cu.fresnohernandez99.app.navegando.di.module

import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStore
import cu.fresnohernandez99.app.navegando.prefsstore.PrefsStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {

  //@Binds
  //abstract fun bindProtoStore(protoStoreImpl: ProtoStoreImpl): ProtoStore

  @Binds
  @Singleton
  abstract fun bindPrefsStore(prefsStoreImpl: PrefsStoreImpl): PrefsStore
}