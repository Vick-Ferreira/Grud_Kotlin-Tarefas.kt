package com.grud.lojavirtualfirebase.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grud.lojavirtualfirebase.R
import com.grud.lojavirtualfirebase.databinding.FragmentLoginBinding
import com.grud.lojavirtualfirebase.helper.BaseFragment
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import com.grud.lojavirtualfirebase.helper.initToolbar

class LoginFragment :  BaseFragment() {


    //implementação do view Binding Fragment
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth //inicialisar o auth

        initClick()
    }

    //função de navegação com botões LOGIN
    private fun initClick() {

        binding.btnLogin.setOnClickListener {
            validarDados()
        }

        binding.btnCriar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registroFragment)
        }
        binding.btnRecuperar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recuperarFragment)

        }
    }


    //CRIANDO FUNÇÃO PARA VALIDAR DADOS LOGIN
    private fun validarDados() {
        //recuperar email e senha que o user colocou

        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString().trim()

        //se não tiver vazio é por que o user já digitou algo
        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {

                hideKeyboard()
                binding.prog.isVisible = true
                loginUser(email, senha)

            } else {
                Toast.makeText(requireContext(), "Poxa, precisamos que  informe seu senha", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "Por favor, informe seu e-mail", Toast.LENGTH_SHORT).show()
        }
    }


    //FUNÇÃO que quando chamada cofere credenciais
    private fun loginUser(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha) //criando um usuario que recebe email e senha
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Login bem sucedido, massa! Seja bem vindo!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    val errorMsg = task.exception?.message ?: "Erro desconhecido"
                    Log.e("TAG", "Erro no login: $errorMsg") // Adiciona esta linha para ver a mensagem de erro no log

                    Toast.makeText(
                        requireContext(),
                        "Email não cadastrado!",
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
