package org.mpmg.mpapp.ui.shared.filters

import org.mpmg.mpapp.domain.models.PublicWork
import org.mpmg.mpapp.ui.shared.filters.SyncStatus.*

class FilterSyncStatus : IPublicWorkFilter() {

    override val filterKey: String
        get() = this::class.java.name

    val syncSet = hashSetOf<SyncStatus>()

    fun addSyncStatus(syncStatus: SyncStatus) {
        syncSet.add(syncStatus)
    }

    fun removeSyncStatus(syncStatus: SyncStatus) {
        syncSet.remove(syncStatus)
    }

    override fun keepItem(publicWork: PublicWork): Boolean {
        var keep = !publicWork.isSent && publicWork.idCollect == null
        syncSet.forEach { syncStatus ->
            keep = keep || applyFilter(publicWork, syncStatus)
        }
        return keep
    }

    private fun applyFilter(publicWork: PublicWork, syncStatus: SyncStatus): Boolean {
        return when (syncStatus) {
            SENT -> publicWork.isSent
            COLLECTED -> publicWork.idCollect != null
        }
    }

    fun isStatusEnabled(syncStatus: SyncStatus): Boolean {
        return syncSet.contains(syncStatus)
    }
}