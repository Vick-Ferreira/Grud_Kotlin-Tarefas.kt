package com.grud.lojavirtualfirebase

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grud.lojavirtualfirebase.databinding.FragmentLoginBinding
import com.grud.lojavirtualfirebase.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    //implementação do view Binding Fragment
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        return binding.root



    }

    //chamado apos que a ui for exibida.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //codigo que determina quando tempo esse fragment vai ficar aberto e apos isso chamar a função checklog
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, 8000)



    }





    //IMPORTANTE: Verificação de authentication
    private fun checkAuth() {// id do caminho do grafico para o fragment que deseja que abra apos o tempo determinado
        auth = Firebase.auth
        if(auth.currentUser == null) { //se der nullo -> NÃO ESTÁ AUTENTICADO
            findNavController().navigate(R.id.action_splashFragment_to_authentication)
        }else{
           findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


