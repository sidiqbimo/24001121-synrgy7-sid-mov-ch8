package com.bimobelajar.mymovie.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bimobelajar.mymovie.R
import com.bimobelajar.data.worker.BlurWorker
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var usernameInput: EditText
    private lateinit var fullNameInput: EditText
    private lateinit var birthdateInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var saveChangesButton: Button
    private lateinit var logoutButton: Button
    private lateinit var accountImage: ImageView
    private val pickImageRequestCode = 1001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        profileViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ProfileViewModel::class.java)

        usernameInput = view.findViewById(R.id.usernameInput)
        fullNameInput = view.findViewById(R.id.fullNameInput)
        birthdateInput = view.findViewById(R.id.birthdateInput)
        addressInput = view.findViewById(R.id.addressInput)
        saveChangesButton = view.findViewById(R.id.saveChangesButton)
        logoutButton = view.findViewById(R.id.logoutButton)
        accountImage = view.findViewById(R.id.accountImage)

        profileViewModel.userName.observe(viewLifecycleOwner) { username ->
            usernameInput.setText(username)
        }

        profileViewModel.fullName.observe(viewLifecycleOwner) { fullName ->
            fullNameInput.setText(fullName)
        }

        profileViewModel.birthdate.observe(viewLifecycleOwner) { birthdate ->
            birthdateInput.setText(birthdate)
        }

        profileViewModel.address.observe(viewLifecycleOwner) { address ->
            addressInput.setText(address)
        }

        saveChangesButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val fullName = fullNameInput.text.toString()
            val birthdate = birthdateInput.text.toString()
            val address = addressInput.text.toString()
            profileViewModel.saveChanges(username, fullName, birthdate, address)
            Toast.makeText(context, "Berhasil mengubah data!", Toast.LENGTH_SHORT).show()
        }

        logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, pickImageRequestCode)
        }

//        load foto terupdate
        val sharedPreferences = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val profileImagePath = sharedPreferences?.getString("profile_image_path", null)
        if (profileImagePath != null) {
            val bitmap = BitmapFactory.decodeFile(profileImagePath)
            accountImage.setImageBitmap(bitmap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequestCode && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            imageUri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, it)
                val outputFile = File(activity?.filesDir, "profile_image.jpg")
                FileOutputStream(outputFile).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                }

                val sharedPreferences = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                sharedPreferences?.edit()?.putString("profile_image_path", outputFile.absolutePath)?.apply()

                val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
                    .setInputData(workDataOf("image_path" to outputFile.absolutePath))
                    .build()

                WorkManager.getInstance(requireContext()).enqueue(blurRequest)

                WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(blurRequest.id)
                    .observe(viewLifecycleOwner, Observer { workInfo ->
                        if (workInfo != null && workInfo.state.isFinished) {
                            val blurredBitmap = BitmapFactory.decodeFile(outputFile.absolutePath)
                            accountImage.setImageBitmap(blurredBitmap)
                            val homeImageView = activity?.findViewById<ImageView>(R.id.accountImage)
                            homeImageView?.setImageBitmap(blurredBitmap)
                        }
                    })
            }
        }
    }
}
