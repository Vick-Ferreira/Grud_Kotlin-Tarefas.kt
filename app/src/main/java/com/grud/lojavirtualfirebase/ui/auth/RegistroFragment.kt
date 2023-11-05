package com.grud.lojavirtualfirebase.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grud.lojavirtualfirebase.R
import com.grud.lojavirtualfirebase.databinding.FragmentRegistroBinding
import com.grud.lojavirtualfirebase.helper.BaseFragment
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import com.grud.lojavirtualfirebase.helper.initToolbar

class RegistroFragment :  BaseFragment(){


    //implementação do view Binding Fragment
    private var _binding: FragmentRegistroBinding? = null
    private val binding get() = _binding!!


    private lateinit var auth: FirebaseAuth  //obejto alfa firebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toobar)//inicializando toobar

        auth = Firebase.auth //inicialisar o auth

        //metodo ouve ao click
        initClick()
    }

    private fun initClick(){  //ao clicar vamos chamar o metodo
        binding.btnCadastrar.setOnClickListener{validarDados()}
    }


    //CRIANDO FUNÇÃO PARA VALIDAR LOGIN
    private fun validarDados() {
        //recuperar email e senha que o user colocou

        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString().trim()

        //se não tiver vazio é por que o user já digitou algo
        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {

                hideKeyboard()
                binding.prog.isVisible = true
                registrarUser(email, senha)

            } else {
                Toast.makeText(requireContext(), "Poxa, precisamos que informe seu senha!", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(requireContext(), "Por favor,informe seu e-mail!", Toast.LENGTH_SHORT).show()
        }
    }


    //função para registar em banco de dados o user
    private fun registrarUser(email:String, senha:String){
        auth.createUserWithEmailAndPassword(email, senha) //criando um usuario que recebe email e senha
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Cadastro concluido! Obrigada por entrar para familia, valeu!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_global_homeFragment)
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