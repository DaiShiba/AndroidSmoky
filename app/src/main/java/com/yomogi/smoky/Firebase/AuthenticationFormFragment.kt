package com.yomogi.smoky.Firebase

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.yomogi.smoky.R
import kotlinx.android.synthetic.main.authentication_form_fragment.*

class AuthenticationFormFragment : Fragment() {

    companion object {
        fun newInstance() = AuthenticationFormFragment()

        //FirebaseAuthインスタンス
        private lateinit var auth : FirebaseAuth
    }
    private lateinit var TAG : String
    private lateinit var viewModel: AuthenticationFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authentication_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthenticationFormViewModel::class.java)
        // TODO: Use the ViewModel

        TAG = String.format(getString(R.string.log_tag),
            AuthenticationFormFragment::class.java.simpleName)

        auth_button.setOnClickListener {
            val msg = "email : ${auth_email.text} \n password : ${auth_password.text}"
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()

            //FirebaseAuthインスタンスの初期化
            auth = FirebaseAuth.getInstance()

            val email : String = auth_email.text.toString()
            val password = auth_password.text.toString()

            activity?.let {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it) { task ->

                        if(email.isEmpty() || password.isEmpty()){
                            Toast.makeText(activity, "入力欄が空欄のため実行不可.",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            //updateUI(user)
                            Toast.makeText(activity, "$user created Success.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }
                    }
            }
        }

        login_button.setOnClickListener {
            val msg = "email : ${auth_email.text} \n password : ${auth_password.text}"
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
            auth = FirebaseAuth.getInstance()

            val email : String = auth_email.text.toString()
            val password = auth_password.text.toString()

            activity?.let {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it) { task ->

                        if(email.isEmpty() || password.isEmpty()){
                            Toast.makeText(activity, "入力欄が空欄のため実行不可.",
                                Toast.LENGTH_SHORT).show()
                            return@addOnCompleteListener
                        }

                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmailAndPassword:success")
                            val user = auth.currentUser
                            //updateUI(user)
                            Toast.makeText(activity, "$user login Success.",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            //updateUI(null)
                        }
                    }
            }
        }
    }

}
