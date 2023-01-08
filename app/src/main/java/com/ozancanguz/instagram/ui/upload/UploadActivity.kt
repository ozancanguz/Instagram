package com.ozancanguz.instagram.ui.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

import com.ozancanguz.instagram.databinding.ActivityUploadBinding
import java.io.IOException
import java.util.*

class UploadActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUploadBinding

    private lateinit var auth:FirebaseAuth
    private lateinit var storage:FirebaseStorage
    private lateinit var firestore: FirebaseFirestore

    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // register launcher init
        registerLauncher()

        // init firebase variables
        auth= Firebase.auth
        storage=Firebase.storage
        firestore=Firebase.firestore



        // select image fun
        loadImage()


        // upload to firebase store
        upload()

    }

    private fun upload() {
        binding.uploadBtn.setOnClickListener {
            var uuid= UUID.randomUUID()
            var imageName="$uuid.jpg"
            val reference=storage.reference
            val imageReference=reference.child("images").child(imageName)
            if(selectedPicture !=null){
                imageReference.putFile(selectedPicture!!).addOnSuccessListener {

                    // -> Download url save fire store

                }
            }
        }
    }

    private fun loadImage() {
        binding.selectImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {

                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                val intentToGallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }
        }
    }
        private fun registerLauncher() {
            activityResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        selectedPicture = intentFromResult.data
                        try {
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(
                                    this@UploadActivity.contentResolver,
                                    selectedPicture!!
                                )
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(
                                    this@UploadActivity.contentResolver,
                                    selectedPicture
                                )
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { result ->
                if (result) {
                    //permission granted
                    val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    //permission denied
                    Toast.makeText(this@UploadActivity, "Permisson needed!", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
