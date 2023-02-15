package com.geektech.taskappexample.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.geektech.taskappexample.MainApplication
import com.geektech.taskappexample.R
import com.geektech.taskappexample.data.SessionEntity
import com.geektech.taskappexample.databinding.FragmentAuthBinding
import com.geektech.taskappexample.utils.Preferences
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null
    private var phoneNumber = ""
    private var binding: FragmentAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentAuthBinding.inflate(LayoutInflater.from(requireContext()), container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }


    private fun initListeners() {

        binding?.btnAuth?.setOnClickListener {

            if (binding?.layoutVerification?.visibility == View.VISIBLE) {

                checkCode()

            } else {

                phoneNumber =(binding?.etCountryCode?.text.toString()+binding?.etPhoneNumber?.text.toString())
                binding?.containerPhoneNumber?.visibility = View.GONE
                binding?.layoutVerification?.visibility = View.VISIBLE

                sendCode()

            }

        }
    }

    private fun sendCode() {

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(

                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    override fun onCodeAutoRetrievalTimeOut(p0: String) {
                        super.onCodeAutoRetrievalTimeOut(p0)

                        binding?.layoutVerification?.visibility = View.GONE
                        binding?.containerPhoneNumber?.visibility = View.VISIBLE

                    }

                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        // This callback will be invoked in two situations:
                        // 1 - Instant verification. In some cases the phone number can be instantly
                        //     verified without needing to send or enter a verification code.
                        // 2 - Auto-retrieval. On some devices Google Play services can automatically
                        //     detect the incoming verification SMS and perform verification without
                        //     user action.
                        Log.d("TEST", "onVerificationCompleted:$credential")
                        signIn()
                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        // This callback is invoked in an invalid request for verification is made,
                        // for instance if the the phone number format is not valid.
                        Log.w("TEST", "onVerificationFailed", e)

                        if (e is FirebaseAuthInvalidCredentialsException) {

                            Toast.makeText(
                                requireContext(),
                                e.localizedMessage ?: e.message,
                                Toast.LENGTH_LONG
                            ).show()
                            // Invalid request
                        } else if (e is FirebaseTooManyRequestsException) {
                            // The SMS quota for the project has been exceeded

                            Toast.makeText(
                                requireContext(),
                                e.localizedMessage ?: e.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        Toast.makeText(
                            requireContext(),
                            e.localizedMessage ?: e.message,
                            Toast.LENGTH_LONG
                        ).show()

                        binding?.layoutVerification?.visibility = View.GONE
                        binding?.containerPhoneNumber?.visibility = View.VISIBLE
                    }

                    override fun onCodeSent(
                        verificationId: String,
                        token: PhoneAuthProvider.ForceResendingToken,
                    ) {
                        Log.d("TEST", "onCodeSent:$verificationId")

                        storedVerificationId = verificationId
                        resendToken = token

                        Toast.makeText(
                            requireContext(),
                            "Verification code has been sent to phone ",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun checkCode() {

        binding?.animeView?.visibility=View.GONE
        binding?.layoutVerification?.visibility=View.GONE
        binding?.btnAuth?.visibility=View.GONE

        binding?.determinateBar?.visibility=View.VISIBLE

        val credential = PhoneAuthProvider.getCredential(
            storedVerificationId!!, binding?.etVerificationCode?.text.toString()
        )
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("success","success")
                    signIn()
                }
                else Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_LONG)
                    .show()
                binding?.determinateBar?.visibility=View.GONE
            }

    }

    private fun signIn() {
        if(phoneNumber.isBlank()) throw RuntimeException("Please, enter your phone number.")


//        val entity = SessionEntity(phoneNumber)
//
//        lifecycleScope.launch {
//            MainApplication.appDataBase?.sessionDao?.save(entity)
//        }


        if (Preferences(requireContext()).getHaveSeenBoarding()) {
            findNavController().navigate(
                R.id.navigation_home
            )
        } else
            findNavController().navigate(
                R.id.onBoardFragment
            )
    }

}