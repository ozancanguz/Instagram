package com.ozancanguz.instagram.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private  lateinit var auth:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        FirebaseApp.initializeApp(requireContext())
        // Initialize Firebase Auth
        auth = Firebase.auth


        signIn()

        return view
    }

    private fun signIn() {
        binding.signInBtn.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            binding.progressBar.visibility=View.VISIBLE
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(),"E-mail or password empty", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility=View.INVISIBLE
            } else {

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {

                    // navigate to feed fragment
                     findNavController().navigate(R.id.action_loginFragment_to_feedFragment)

                    binding.progressBar.visibility=View.INVISIBLE


                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }


            }
        }
    }


}