package com.ozancanguz.instagram.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.databinding.FragmentFeedBinding
import com.ozancanguz.instagram.model.Post


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding?=null

    private val binding get() = _binding!!

    private lateinit var auth:FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var db: FirebaseFirestore

    private lateinit var postsArrayList:ArrayList<Post>

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
        storage=Firebase.storage
        db=Firebase.firestore

        // init post array list
        postsArrayList= ArrayList<Post>()


        // get all data from firebase firestore
        getDataFromFirebaseDb()

        return view
    }

    private fun getDataFromFirebaseDb() {
        db.collection("Posts").addSnapshotListener { value, error ->

            if(error !=null){
                Toast.makeText(requireContext(),error.localizedMessage, Toast.LENGTH_LONG).show()

            }else{

                if(value!=null){
                    if(!value.isEmpty){
                        val documents= value.documents
                        for(document in documents){
                            val comment=document.get("comment") as String
                            val email=document.get("e-mail") as String
                            val downloadUrl=document.get("downloadUrl") as String
                            Log.d("feed", "comment:$comment")
                            val post= Post(email,comment,downloadUrl)
                            postsArrayList.add(post)

                        }
                    }
                }
            }

        }
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