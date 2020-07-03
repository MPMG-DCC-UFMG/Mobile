package org.mpmg.mpapp.domain.repositories.config.datasources

import org.mpmg.mpapp.domain.network.models.EntityVersion
import org.mpmg.mpapp.domain.network.models.PublicWorkRemote
import org.mpmg.mpapp.domain.network.models.TypePhotoRemote
import org.mpmg.mpapp.domain.network.models.TypeWorkRemote

interface IRemoteConfigDataSource {

    suspend fun loadTypeWorks(): List<TypeWorkRemote>

    suspend fun getTypeWorkVersion(): EntityVersion

    suspend fun loadPublicWorks(): List<PublicWorkRemote>

    suspend fun getPublicWorkVersion(): EntityVersion

    suspend fun loadTypePhotos(): List<TypePhotoRemote>

    suspend fun getTypePhotosVersion(): EntityVersion

    suspend fun loadPublicWorksDiff(version: Int): List<PublicWorkRemote>

}