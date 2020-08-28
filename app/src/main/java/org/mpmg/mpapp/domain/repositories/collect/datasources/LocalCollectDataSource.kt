package org.mpmg.mpapp.domain.repositories.collect.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.Collect
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalCollectDataSource(applicationContext: Context) : BaseDataSource(applicationContext),
    ILocalCollectDataSource {

    override fun insertCollect(collect: Collect) {
        mpDatabase()!!.collectDAO().insert(collect)
    }

    override fun getCollectById(collectId: String): Collect? {
        return mpDatabase()!!.collectDAO().getCollectById(collectId)
    }

    override fun getCollectByPublicIdAndStatus(publicId: String, sent: Boolean): Collect? {
        return mpDatabase()!!.collectDAO().getCollectByPublicIdAndStatus(publicId, sent)
    }

    override fun markCollectSent(collectId: String) {
        val oldCollect = mpDatabase()!!.collectDAO().getCollectById(collectId)
        oldCollect?.let {
            it.isSent = true
            mpDatabase()!!.collectDAO().update(it)
        }
    }

    override fun deleteCollectById(collectId: String) {
        mpDatabase()!!.collectDAO().deleteCollectById(collectId)
    }
}