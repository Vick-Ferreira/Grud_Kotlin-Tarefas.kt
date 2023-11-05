package com.grud.lojavirtualfirebase.ui.Tabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.grud.lojavirtualfirebase.HomeFragmentDirections
import com.grud.lojavirtualfirebase.Modal.Task
import com.grud.lojavirtualfirebase.R
import com.grud.lojavirtualfirebase.databinding.FragmentAfazerBinding
import com.grud.lojavirtualfirebase.databinding.FragmentFazendoBinding
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import com.grud.lojavirtualfirebase.ui.adapter.TaskAdapter

class FazendoFragment : Fragment() {



    //implementação do view Binding Fragment
    private var _binding: FragmentFazendoBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val taskList = mutableListOf<Task>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFazendoBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTasks()
    }


    private  fun getTasks(){
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser()?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {//tenho registro para recupera?
                        taskList.clear()
                        for (snap in snapshot.children){//percorrer todas as tarefas do banco de dado pelo ID, SALVANDO NA LISTA, QUE VAI SER ENVIADA AO ADAPTER
                            val task =
                                snap.getValue(Task::class.java) as Task//recuperando o dado e vai ser um objeto

                            if (task.status == 1) taskList.add(task)//comparando o statudo e add se for igual a condição
                        }

                        binding.textInfo.text = ""
                        //depois que recuperar toda a lista chama :
                        taskList.reverse()//ultimo add é o primeiro na lista
                        initAdapter()

                    }else{
                        binding.textInfo.text = "Nenhuma Tarefa Cadastrada"
                    }
                    binding.prog.isVisible = false
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(),"erro", Toast.LENGTH_LONG).show()
                }

            })
    }

    private  fun deleteTask(task: Task){
        FirebaseHelper
            .getDatabase()
            .child("task")
            .child(FirebaseHelper.getIdUser()?: "")
            .child(task.id)//recuperando ID
            .removeValue()

        taskList.remove(task)
        taskAdapter.notifyDataSetChanged()

    }
    //carregando adapter com a lista já com as tarefas contidas nela
    private fun initAdapter() {
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.setHasFixedSize(true)
        taskAdapter = TaskAdapter(requireContext(), taskList) { task, select ->
            opcaoSelecao(task, select)

        }
        binding.rvTask.adapter = taskAdapter
    }

    private  fun  opcaoSelecao(task:Task, select:Int){
        when(select){
            TaskAdapter.SELECT_REMOVE -> {
                deleteTask(task)//quando clicar no btn 2 (remove) vai remover o valor (setado no adapter)
            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections //viewpage2 fragment SAFE args cria uma clase de saida e add o direction no fim
                    .actionHomeFragmentToFormTaskFragment(task)//recebendo tarefa
                findNavController().navigate(action)
                //aqui estamos setando para que tela vai, e também passando o objeto task  que é onde tem os dados, vamos mandar a tela ao FORM TASK
            }
            TaskAdapter.SELECT_BACK -> {
                task.status = 0
                upDateTask(task)
            }
            TaskAdapter.SELECT_NEXT -> {
                task.status = 2
                upDateTask(task)
            }

        }
    }


    //se não tiver val em nivel global não esquecer de char (task)
    private  fun upDateTask(task: Task) {
        FirebaseHelper
            .getDatabase() //dados banco
            .child("task") //projeto firebase
            .child(FirebaseHelper.getIdUser() ?: "")//id do usuario que está conectado no app
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Tarefa atualizada com sucesso", Toast.LENGTH_LONG).show()
                } else {
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
