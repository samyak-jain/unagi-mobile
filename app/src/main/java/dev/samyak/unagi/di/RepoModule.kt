package dev.samyak.unagi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.samyak.unagi.data.LibraryRepo
import dev.samyak.unagi.data.LibraryRepoContract

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepoModule {
    @Binds
    abstract fun bindsLibraryRepo(libraryRepo: LibraryRepo): LibraryRepoContract
}