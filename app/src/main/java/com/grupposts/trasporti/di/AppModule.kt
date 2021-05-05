package com.grupposts.trasporti.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.grupposts.trasporti.base.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.*
import javax.inject.Singleton

/**
 * @author Andrea Maglie
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val PREF_FILE_NAME = "username-prefs"

    @Provides
    fun provideLocale(): Locale = Locale.getDefault()

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            PREF_FILE_NAME,
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Provides
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider = ResourceProvider(context)
}

