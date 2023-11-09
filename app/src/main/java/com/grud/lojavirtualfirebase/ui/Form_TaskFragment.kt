package com.grud.lojavirtualfirebase.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.grud.lojavirtualfirebase.Modal.Task
import com.grud.lojavirtualfirebase.R
import com.grud.lojavirtualfirebase.databinding.FragmentFormTaskBinding
import com.grud.lojavirtualfirebase.databinding.FragmentHomeBinding
import com.grud.lojavirtualfirebase.helper.BaseFragment
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import com.grud.lojavirtualfirebase.helper.initToolbar
import com.grud.lojavirtualfirebase.ui.Tabs.DatePickerFragment
import java.util.Calendar


class Form_TaskFragment :  BaseFragment() {


    //implementação do view Binding Fragment
    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean =
        true //quando o user clicar no btn add sabemos que está criando uma variavel nova e ao EDITAR sera tru portanto saberemos que não é uma materia nova
    private var statusTask: Int = 0

    //destino de recebimento dos dados pelo AGS, declaração de informações
    private val args: Form_TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initToolbar(binding.toobar)//inicializando toobar

        initListners()
        getArgs()

    }



    private fun getArgs() {
        args.task.let {//verifica que o que vier não é nulo
            if (it != null) {//SE for diferente de nullo, dignifica que vamos fazer uma EDIÇÃO
                task = it

                configTask()

            }

        }
    }

    private fun configTask() {
        newTask = false //edição
        statusTask = task.status

        //CHAMANDO CONTEUDO PARA SER EDITADO, CONTEUDO VEM JUNTO
        binding.editTarefa.setText(task.titulo)
        binding.editDescricao.setText(task.anotacao)
        binding.etDate.setText(task.data)

        setStatus()
    }

    //LOGICA AO EDITAR
    private fun setStatus() {
        binding.radioGroup.check(
            when (task.status) {
                0 -> {
                    R.id.rbAfaze
                }

                1 -> {
                    R.id.rbFazendo
                }

                else -> {
                    R.id.rbFeito
                }
            }
        )
    }

    private fun initListners() {
        binding.btnCalendario.setOnClickListener {showDatePickerDialog() }

        binding.btnAdicionar.setOnClickListener { validarTarefa() }

        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            statusTask = when (id) {
                R.id.rbAfaze -> 0
                R.id.rbFazendo -> 1
                else -> 2
            }
        }

    }
    //Essafunção vai "ser" a função da class.kt DatePickerDialog, passando função por paramêtro
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { dia, mes, ano -> onDataSelected(dia, mes, ano) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDataSelected(dia: Int, mes: Int, ano: Int) {
        val dataSelecionada = " $dia / $mes / $ano"
        binding.etDate.setText(dataSelecionada)//saida da data escolhida
    }



    private  fun validarTarefa(){

        val descricao =  binding.editTarefa.text.toString().trim()//não pode ser vazio
        val anotacao = binding.editDescricao.text.toString().trim()
        val data = binding.etDate.text.toString().trim()

        if(descricao.isNotEmpty()){
            if(anotacao.isNotEmpty()){

            hideKeyboard ()
            binding.prog.isVisible = true

            //tarefa está EDITANDO OU ADD
            if(newTask) task = Task()
            task.titulo = descricao
            task.anotacao = anotacao
            task.data = data
            task.status = statusTask //recebendo valor do status ao adicionar

            saveTask()
        }else {
                Toast.makeText(
                    requireContext(),
                    "Informe uam descrição para a tarefa",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    private  fun saveTask() {
        FirebaseHelper
            .getDatabase() //dados banco
            .child("task") //projeto firebase
            .child(FirebaseHelper.getIdUser() ?: "")//id do usuario que está conectado no app
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (newTask) {//nova tarefa
                        findNavController().popBackStack()
                        Log.d("TAG", "Tarefa salva com sucesso")
                        Toast.makeText(requireContext(), "Massa,sua tarefa foi salva!", Toast.LENGTH_LONG).show()
                    } else {//editando tarefa
                        findNavController().popBackStack()
                        binding.prog.isVisible = false
                        Log.d("TAG", "Tarefa atualizada com sucesso")
                        Toast.makeText(requireContext(), "Legal,tarefa atualizada!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("TAG", "Erro ao salvar a tarefa: ${task.exception}")
                    Toast.makeText(requireContext(), "Erro ao salvar a tarefa", Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener { exception ->
                binding.prog.isVisible = false
                Log.e("TAG", "Erro ao salvar a tarefa: $exception")
                Toast.makeText(requireContext(), "Erro ao salvar a tarefa2", Toast.LENGTH_LONG).show()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}