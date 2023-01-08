package com.ozancanguz.instagram.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    private lateinit var auth:FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root

        // init firebase
        auth=Firebase.auth



        //register -> sign up
        register()


        return view

    }

    private fun register() {
        binding.signUpBtn.setOnClickListener {
            val email = binding.SignUpemailET.text.toString()
            val password = binding.SignUpPassword.text.toString()
            val username=binding.SignUpuserNameET.text.toString()
            binding.progressBar.visibility=View.VISIBLE

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(),"E-mail or password empty", Toast.LENGTH_LONG).show()
                binding.progressBar.visibility=View.INVISIBLE
            } else {

                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

                    // navigate to feed screen
                    findNavController().navigate(R.id.action_registerFragment_to_feedFragment)

                    binding.progressBar.visibility=View.INVISIBLE

                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
                }

            }
        }
    }


}