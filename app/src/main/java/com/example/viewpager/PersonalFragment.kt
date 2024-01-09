package com.example.viewpager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream


class PersonalFragment : Fragment() {
    private val saveOrEditBtn by lazy {
        requireView().findViewById<Button>(R.id.saveOrEditButton)
    }

    private val sharedPref by lazy {
        requireActivity().getSharedPreferences(getString(R.string.personalpreference), Context.MODE_PRIVATE)
    }

    private val nameEdit by lazy {
        requireView().findViewById<EditText>(R.id.nameEdit)
    }

    private val nameView by lazy {
        requireView().findViewById<TextView>(R.id.nameTextView)
    }

    private val avatarImage by lazy {
        requireView().findViewById<ImageView>(R.id.avatarImage)
    }

    private var avatarLauncher: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var isEditing: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiSetupEditState()
        setupComponentsListeners()
        isEditing = sharedPref.getString("person_name", "") == ""
        uiSetupEditState()
        loadPersonDataFromPreference()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        avatarLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            avatarImage.setImageURI(it)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    private fun saveAvatarToString(): String {
        val btm: Bitmap = avatarImage.drawable.toBitmap(avatarImage.width, avatarImage.height)
        val stream = ByteArrayOutputStream()
        btm.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val b = stream.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun loadAvatarFromPreference() {
        val imageText = sharedPref.getString("person_photo", "")
        if (imageText?.isNotEmpty() == true) {
            val imageBytes = Base64.decode(imageText, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            avatarImage.setImageBitmap(bitmap)
        }
    }

    private fun loadPersonDataFromPreference() {
        loadAvatarFromPreference()
        if (isEditing) {
            nameEdit.setText(sharedPref.getString("person_name", ""))
        } else {
            nameView.text = sharedPref.getString("person_name", "")
        }
    }

    private fun setupComponentsListeners() {
        saveOrEditBtn.setOnClickListener {
            if (isEditing) {
                sharedPref.edit()
                    .putString("person_photo", saveAvatarToString())
                    .putString("person_name", nameEdit.text.toString())
                    .apply()

            }
            isEditing = !isEditing
            uiSetupEditState()
            loadPersonDataFromPreference()
        }
        avatarImage.setOnClickListener {
            avatarClickHandler()
        }
    }

    private fun avatarClickHandler() {
        avatarLauncher?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun uiSetupEditState() {
        if (isEditing) {
            saveOrEditBtn.text = getString(R.string.save)
            nameView.visibility = View.GONE
            nameEdit.visibility = View.VISIBLE
            avatarImage.setOnClickListener{avatarClickHandler()}
        } else {
            nameView.visibility = View.VISIBLE
            nameEdit.visibility = View.GONE
            saveOrEditBtn.text = getString(R.string.edit)
            avatarImage.setOnClickListener{}
        }
    }
}