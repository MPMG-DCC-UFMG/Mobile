package org.mpmg.mpapp.domain.repositories.publicwork

import androidx.lifecycle.LiveData
import org.mpmg.mpapp.domain.models.Address
import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.domain.models.relations.PublicWorkAndAdress
import org.mpmg.mpapp.domain.repositories.publicwork.local.ILocalPublicWorkDataSource

class PublicWorkRepository(
    private val localPublicWorkDataSource: ILocalPublicWorkDataSource
) :
    IPublicWorkRepository {

    private val TAG: String = PublicWorkRepository::class.java.simpleName

    override fun insertPublicWork(publicWork: PublicWork, address: Address) {
        localPublicWorkDataSource.insertPublicWork(publicWork, address)
    }

    override fun listAllPublicWorks(): List<PublicWorkAndAdress> {
        return localPublicWorkDataSource.listAllPublicWorks()
    }

    override fun listAllPublicWorksLive(): LiveData<List<PublicWorkAndAdress>> {
        return localPublicWorkDataSource.listAllPublicWorksLive()
    }
}