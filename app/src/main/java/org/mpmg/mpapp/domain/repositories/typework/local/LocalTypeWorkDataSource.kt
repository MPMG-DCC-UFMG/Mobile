package org.mpmg.mpapp.domain.repositories.typework.local

import android.content.Context
import org.mpmg.mpapp.domain.database.MPDatabase
import org.mpmg.mpapp.domain.models.TypeWork
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalTypeWorkDataSource(applicationContext: Context) : BaseDataSource(applicationContext), ILocalTypeWorkDataSource {

    override fun insertTypeWork(typeWork: TypeWork) {
        mpDatabase()!!.typeWorkDAO().insert(typeWork)
    }

    override fun insertTypeWorks(typeWorks: List<TypeWork>) {
        mpDatabase()!!.typeWorkDAO().insertAll(typeWorks.toTypedArray())
    }

    override fun listAllTypeWorks(): List<TypeWork> {
        return mpDatabase()!!.typeWorkDAO().listAllTypeWork()
    }
}