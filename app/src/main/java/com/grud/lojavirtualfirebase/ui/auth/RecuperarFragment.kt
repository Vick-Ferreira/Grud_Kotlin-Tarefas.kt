package com.grud.lojavirtualfirebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grud.lojavirtualfirebase.databinding.FragmentRecuperarBinding
import com.grud.lojavirtualfirebase.helper.BaseFragment
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import com.grud.lojavirtualfirebase.helper.initToolbar

class RecuperarFragment :  BaseFragment() {

    //implementação do view Binding Fragment
    private var _binding: FragmentRecuperarBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecuperarBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)//inicializando toobar
        auth = Firebase.auth //inicialisar o auth

        initClick()
    }

    //função de navegação com botões LOGIN
    private fun initClick() {
        binding.btnRecupera.setOnClickListener { validarDados() }
    }



        //CRIANDO FUNÇÃO PARA VALIDAR LOGIN
        private fun validarDados() {
            //recuperar email e senha que o user colocou

            val email = binding.edtEmail.text.toString().trim()


            //se não tiver vazio é por que o user já digitou algo
            if (email.isNotEmpty()) {

                hideKeyboard()
                binding.prog.isVisible = true
                recuperaUser(email)

            } else {
                Toast.makeText(requireContext(), "Por favor, informe seu e-mail", Toast.LENGTH_SHORT).show()
            }
        }

        private fun recuperaUser(email:String){
            auth.sendPasswordResetEmail(email) //criando um usuario que recebe email e senha
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                       Toast.makeText(requireContext(),"Legal!Já enviamos um link para seu e-mail", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            FirebaseHelper.validErro(task.exception?.message ?: ""),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.prog.isVisible = false
                    }
                }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }

