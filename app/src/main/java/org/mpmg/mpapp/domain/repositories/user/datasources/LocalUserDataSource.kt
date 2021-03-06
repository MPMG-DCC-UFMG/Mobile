package org.mpmg.mpapp.domain.repositories.user.datasources

import android.content.Context
import org.mpmg.mpapp.domain.database.models.User
import org.mpmg.mpapp.domain.repositories.shared.BaseDataSource

class LocalUserDataSource(applicationContext: Context) : BaseDataSource(applicationContext) {

    fun insertUser(user: User) {
        mpDatabase()?.userDAO()?.insert(user)
    }

    fun getUserByEmail(email: String): User? {
        return mpDatabase()?.userDAO()?.getUserByEmail(email)
    }
}