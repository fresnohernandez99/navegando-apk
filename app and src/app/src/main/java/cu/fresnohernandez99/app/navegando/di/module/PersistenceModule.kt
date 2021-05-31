package cu.fresnohernandez99.app.navegando.di.module

import android.app.Application
import androidx.room.Room
import cu.fresnohernandez99.app.navegando.data.persistence.AppDatabase
import cu.fresnohernandez99.app.navegando.data.persistence.VoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Navegando.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideVoteDao(appDatabase: AppDatabase): VoteDao {
        return appDatabase.voteDao()
    }

}
