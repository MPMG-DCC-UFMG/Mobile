package org.mpmg.mpapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mpmg.mpapp.domain.models.User
import org.mpmg.mpapp.domain.repositories.config.IConfigRepository
import org.mpmg.mpapp.domain.repositories.user.IUserRepository


class LoginViewModel(
    val applicationContext: Application,
    private val userRepository: IUserRepository,
    private val configRepository: IConfigRepository
) : AndroidViewModel(applicationContext) {

    fun checkUserLogged(): Boolean {
        return checkGoogleSignedAccount() || checkFirebaseSignedAccount()
    }

    private fun checkGoogleSignedAccount(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(applicationContext)
        return account?.email?.let {
            logIn(it)
            true
        } ?: false
    }

    private fun checkFirebaseSignedAccount(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        return firebaseUser?.displayName?.let {
            logIn(it)
            true
        } ?: false
    }

    fun addUserToDb(userName: String, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.insertUser(
                User(name = userName, email = userEmail)
            )
        }
    }

    fun logIn(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            configRepository.setLoggedUserEmail(userEmail)
        }
    }
}