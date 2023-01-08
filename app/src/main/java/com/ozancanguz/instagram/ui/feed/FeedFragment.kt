package com.ozancanguz.instagram.ui.feed

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding?=null

    private val binding get() = _binding!!
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        // set menu
        setHasOptionsMenu(true)

        // init firebase
        auth= Firebase.auth



        return view
    }


    // create menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signoutmenu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.signout){
            auth.signOut()
            findNavController().navigate(R.id.action_feedFragment_to_loginFragment)
        }
        else if(item.itemId==R.id.addPost){
            findNavController().navigate(R.id.action_feedFragment_to_uploadActivity)
        }
        return super.onOptionsItemSelected(item)
    }

}