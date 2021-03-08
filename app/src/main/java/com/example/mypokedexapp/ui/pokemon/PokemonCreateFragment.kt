package com.example.mypokedexapp.ui.pokemon

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mypokedexapp.MainActivity
import com.example.mypokedexapp.PokemonApplication
import com.example.mypokedexapp.R
import com.example.mypokedexapp.models.Pokemon
import com.example.mypokedexapp.models.Type
import com.example.mypokedexapp.ui.camera.CameraFragment
import com.example.mypokedexapp.utils.ImageHelper
import java.io.*


class PokemonCreateFragment : Fragment(), AdapterView.OnItemSelectedListener {
    companion object{
        const val PICK_IMAGE = 1
    }

    private val pokemonCreateViewModel: PokemonCreateViewModel by viewModels {
        PokemonCreateViewModelFactory((activity?.application as PokemonApplication).repository)
    }

    private var nextCustomPokemonId = 0
    private var pokemonPrimaryType = ""
    private var pokemonSecondaryType: String? = null
    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonFileName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_pokemon_create, container, false)
        pokemonImage = root.findViewById(R.id.create_pokemon_image)
        if(MainActivity.bitmap != null) {
            pokemonImage.setImageBitmap(MainActivity.bitmap)
            pokemonFileName = ImageHelper.bitmapToBase64(MainActivity.bitmap!!)
            MainActivity.bitmap = null
        }

        val saveButton: Button = root.findViewById(R.id.create_pokemon_save_button)
        pokemonCreateViewModel.nextCustomPokemonId.observe(viewLifecycleOwner){
            nextCustomPokemonId = it ?: -1
        }
        saveButton.setOnClickListener{
            val pokemon = createPokemon(root)
            if(pokemon != null) {
                pokemonCreateViewModel.saveCustomPokemon(pokemon)
                findNavController().popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.create_pokemon_error_toast),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val primaryTypeSpinner: Spinner = root.findViewById(R.id.pokemon_primary_type_input)
        ArrayAdapter.createFromResource(root.context, R.array.pokemon_type_array, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            primaryTypeSpinner.adapter = adapter
        }
        primaryTypeSpinner.onItemSelectedListener = this

        val secondaryTypeSpinner: Spinner = root.findViewById(R.id.pokemon_secondary_type_input)
        ArrayAdapter.createFromResource(root.context, R.array.pokemon_type_array, android.R.layout.simple_spinner_item).also{ adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            secondaryTypeSpinner.adapter = adapter
        }
        secondaryTypeSpinner.onItemSelectedListener = this

        val imageFromGallery: Button = root.findViewById(R.id.image_from_directory_button)
        imageFromGallery.setOnClickListener {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"

            val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickIntent.type = "image/*"

            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

            startActivityForResult(chooserIntent, PICK_IMAGE)
        }

        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                findNavController().navigate(R.id.action_navigation_pokemon_create_to_fragment_camera)
            } else {
                Toast.makeText(requireContext(), resources.getString(R.string.camera_permission_error), Toast.LENGTH_SHORT ).show()
            }
        }

        val imageFromCamera: Button = root.findViewById(R.id.image_from_camera_button)
        imageFromCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                findNavController().navigate(R.id.action_navigation_pokemon_create_to_fragment_camera)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
        return root
    }

    private fun createPokemon(root: View) : Pokemon? {
        try{
            val image = pokemonFileName
            val id = nextCustomPokemonId
            val name = root.findViewById<EditText>(R.id.pokemon_name_input).text.toString()
            val hp = root.findViewById<EditText>(R.id.pokemon_hp_input).text.toString().toInt()
            val attack = root.findViewById<EditText>(R.id.pokemon_attack_input).text.toString().toInt()
            val defence = root.findViewById<EditText>(R.id.pokemon_defence_input).text.toString().toInt()
            val specialAttack = root.findViewById<EditText>(R.id.pokemon_special_attack_input).text.toString().toInt()
            val specialDefence = root.findViewById<EditText>(R.id.pokemon_special_defence_input).text.toString().toInt()
            val speed = root.findViewById<EditText>(R.id.pokemon_speed_input).text.toString().toInt()
            val primaryType = Type.getType(pokemonPrimaryType)
            var secondaryType: Type? = null
            if(pokemonSecondaryType != null){
                secondaryType = Type.getType(pokemonSecondaryType!!)
            }

            validateNumber(arrayOf(hp, attack, defence, specialAttack, specialDefence, speed))

            return Pokemon(id, name, image, hp, attack, defence, specialAttack, specialDefence, speed, primaryType!!, secondaryType, false)
        } catch (e: Exception){
            Log.w("PokemonCreateFragment", "Failed to create pokemon: ${e.message}", e)
            return null
        }
    }

    private fun validateNumber(array: Array<Int>) {
        array.forEach {
            if (it < 0 || it > 255) {
                throw Exception()
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id){
            R.id.pokemon_primary_type_input -> pokemonPrimaryType = parent.getItemAtPosition(position) as String
            R.id.pokemon_secondary_type_input -> pokemonSecondaryType = parent.getItemAtPosition(position) as String
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        when (parent?.id){
            R.id.pokemon_primary_type_input -> pokemonPrimaryType = "water"
            R.id.pokemon_secondary_type_input -> pokemonSecondaryType = null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            when(requestCode){
                PICK_IMAGE -> setCustomPokemonImage(data)
            }
        }
    }

    private fun setCustomPokemonImage(data: Intent?){
        if(data == null){
            return
        }

        val uri = data.data
        var bitmap = getBitmap(uri!!)!!
        val matrix = Matrix()
        matrix.postRotate(90F)
        bitmap = Bitmap.createBitmap(bitmap, bitmap.width / 2 - 250, bitmap.height / 2 - 250, 500, 500, matrix, false)
        pokemonImage.setImageBitmap(bitmap)
        pokemonFileName = ImageHelper.bitmapToBase64(bitmap)
    }

    private fun getBitmap(uri: Uri): Bitmap? {
        var bitmap: Bitmap ?= null
        try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            bitmap = BitmapFactory.decodeStream(inputStream)
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }catch (e: FileNotFoundException){}

        return bitmap
    }
}