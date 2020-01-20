package com.yomogi.smoky.Biometric.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.lifecycle.Observer
import com.yomogi.smoky.R
import kotlinx.android.synthetic.main.biometric_fragment.*

class BiometricFragment : Fragment() {

    companion object {
        fun newInstance() = BiometricFragment()
    }

    private lateinit var viewModel: BiometricViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.biometric_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BiometricViewModel::class.java)
        biometric_button.setOnClickListener {
            when (context?.let { BiometricManager.from(it).canAuthenticate() }) {
                BiometricManager.BIOMETRIC_SUCCESS ->
                    viewModel.updateCallbackMessage("BIOMETRIC_SUCCESS", "生体認証が利用可能")
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    viewModel.updateCallbackMessage("BIOMETRIC_ERROR_NONE_ENROLLED", "生体情報が端末に登録されていない")
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                    viewModel.updateCallbackMessage("BIOMETRIC_ERROR_HW_UNAVAILABLE", "BIOMETRIC_ERROR_HW_UNAVAILABLE")
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    viewModel.updateCallbackMessage("BIOMETRIC_ERROR_NO_HARDWARE", "生体認証ハードウェアが利用不可")
                else -> throw IllegalStateException("UnSupport Value")
            }
        }

        // Create the observer which updates the UI.
        val messageObserver = Observer<Pair<String, String>> { it ->
            message.text = it.first
            textView.text = it.second
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.biometricCallback.observe(this, messageObserver)
    }

}