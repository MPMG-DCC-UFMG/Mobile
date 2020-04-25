package org.mpmg.mpapp.domain.repositories.typework

import org.mpmg.mpapp.domain.models.TypeWork

interface ITypeWorkRepository {

    fun insertTypeWork(typeWork: TypeWork)

    fun insertTypeWorks(typeWorks: List<TypeWork>)

    fun listAllTypeWorks(): List<TypeWork>
}