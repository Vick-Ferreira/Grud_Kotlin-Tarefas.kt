package com.grud.lojavirtualfirebase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.grud.lojavirtualfirebase.databinding.FragmentHomeBinding
import com.grud.lojavirtualfirebase.ui.Tabs.AfazerFragment
import com.grud.lojavirtualfirebase.ui.Tabs.FazendoFragment
import com.grud.lojavirtualfirebase.ui.Tabs.FeitoFragment
import com.grud.lojavirtualfirebase.ui.adapter.ViewPageAdapter


class HomeFragment : Fragment() {

    //implementação do view Binding Fragment
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth //reutilizando dados de autenticação

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        configTabLayout()
        initClicks()
    }

    private  fun  initClicks(){
        binding.btnLogout.setOnClickListener{
            logoutApp()
            Toast.makeText(requireContext(), "Obrigada por usar o app. Até mais , valeu!", Toast.LENGTH_LONG).show()
        }

    }

    private fun  logoutApp(){
        auth.signOut()//ao sair vai para...
        findNavController().navigate(R.id.action_homeFragment_to_authentication2)
    }

    //metodo de configurar tab layout
    private fun configTabLayout() {
        val adapter = ViewPageAdapter(requireActivity())
        binding.viewPager.setAdapter(adapter)

        adapter.addFragment(AfazerFragment(), "A Fazer")
        adapter.addFragment(FazendoFragment(), "Fazendo")
        adapter.addFragment(FeitoFragment(), "Feito")

        binding.viewPager.setOffscreenPageLimit(adapter.itemCount)

        val mediator = TabLayoutMediator(
            binding.tab, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = adapter.getTitle(position)
        }
        mediator.attach()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
