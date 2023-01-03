package com.david4vilac.legostorefragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment(R.layout.fragment_auth) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val analytics = FirebaseAnalytics.getInstance(requireContext())
        val bundle = Bundle()


        Log.d("Analytics", "${analytics.toString()}")
        bundle.putString("message", "Integración con FB completa")
        analytics.logEvent("InitScreem", bundle)

        // Setup
        setup()
        session()


    }

    private fun session() {
        Log.d("Sesion","iniciando")
    }

    private fun setup(){
        val btnLogin = requireView().findViewById<Button>(R.id.btnLogin)

        val signUpButton: Button = requireView().findViewById(R.id.signUpBtn)

        val editTextEmail: TextInputLayout = requireView().findViewById(R.id.editTextEmail)
        val passwordEditText: TextInputLayout = requireView().findViewById(R.id.passwordEditText)

        signUpButton.setOnClickListener {
            if(editTextEmail.editText!!.text.trim().isEmpty()){
                editTextEmail.error = "Ingrese el email"
            }else if(passwordEditText.editText!!.text.trim().isEmpty()){
                passwordEditText.error = "Ingrese su contraseña"
            }else{
                if(editTextEmail.editText!!.text.toString().isNotEmpty() && passwordEditText.editText!!.text.isNotEmpty() ){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.editText!!.text.toString(),
                        passwordEditText.editText!!.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "")
                        }else{
                            showAlert(editTextEmail.editText!!.text.toString())
                        }
                    }
                }
            }
        }
        //validacion datos vista

        btnLogin.setOnClickListener {
            if(editTextEmail.editText!!.text.trim().isEmpty()){
                editTextEmail.error = "Ingrese el email"
            }else if(passwordEditText.editText!!.text.trim().isEmpty()){
                passwordEditText.error = "Ingrese su contraseña"
            }else{
                if(editTextEmail.editText!!.text.isNotEmpty() && passwordEditText.editText!!.text.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.editText!!.text.toString(),
                        passwordEditText.editText!!.text.toString()).addOnCompleteListener {

                        Log.d("FirebaseAuth","{${FirebaseAuth.getInstance()}}")
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email ?: "")
                        }else{
                            showAlert(editTextEmail.editText!!.text.toString())
                        }
                    }
                }
            }
        }
    }

    private fun showAlert(string: String){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario $string")
        builder.setPositiveButton("Aceptar", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String){
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }
}