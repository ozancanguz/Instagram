package com.ozancanguz.instagram.ui.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozancanguz.instagram.R
import com.ozancanguz.instagram.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUploadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



    }
}