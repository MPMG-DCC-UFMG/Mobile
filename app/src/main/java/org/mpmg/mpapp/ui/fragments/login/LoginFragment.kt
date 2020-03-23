package org.mpmg.mpapp.ui.fragments.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.GOOGLE_SIGIN_CLIENT_ID
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by sharedViewModel()

    private val RC_GOOGLE_SIGN_IN = 607
    private val TAG = LoginFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSignInButton()
    }

    private fun setupSignInButton() {
        button_loginFragment_googleSignIn.setSize(SignInButton.SIZE_STANDARD)
        button_loginFragment_googleSignIn.setOnClickListener {
            signInGoogle()
        }
        setupSignInButtonVisibility()
    }

    private fun setupSignInButtonVisibility() {
        val userSigned = loginViewModel.checkGoogleSignedAccount()
        button_loginFragment_googleSignIn.visibility = if (userSigned) View.GONE else View.VISIBLE
    }

    private fun signInGoogle() {
        context?.let {
            val gso =
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(GOOGLE_SIGIN_CLIENT_ID)
                    .requestEmail()
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(it, gso)

            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_GOOGLE_SIGN_IN -> {
                data ?: return
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleGoogleSignInResult(task)
            }
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            setupSignInButtonVisibility()
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}