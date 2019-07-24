package com.yomogi.smoky.Firebase

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import com.yomogi.smoky.R
import kotlinx.android.synthetic.main.authentication_fragment.*

class AuthenticationFragment : Fragment() {

    companion object {
        val TAG: String = AuthenticationFragment::class.java.simpleName
        fun newInstance() = AuthenticationFragment()

        //FirebaseAuthインスタンス
        private lateinit var auth : FirebaseAuth
    }

    private lateinit var viewModel: AuthenticationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.authentication_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)

        //FirebaseAuthインスタンスの初期化
        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        val tag = String.format(getString(R.string.log_tag), TAG) + "\n currentUser : ${currentUser?.displayName}"
        auth_text.text = tag
        Log.d(tag,"currentUse : ${currentUser?.displayName}")
    }

}
