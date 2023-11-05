package com.grud.lojavirtualfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.grud.lojavirtualfirebase.Modal.Task
import com.grud.lojavirtualfirebase.R
import com.grud.lojavirtualfirebase.databinding.ItemAdapterBinding


class TaskAdapter (

    private val  context: Context,
    private  val taskList: List<Task>,  //repassando trablaho construtor
    val taskSelect: (Task, Int) -> Unit   //calback - usando no Afazer

): RecyclerView.Adapter<TaskAdapter.MyViewHolder>(){

    companion object{    //POSIÇÕES QUE IRA NÓS RETORNAR AO CLICAR EM CADA ELEMENTO  firebase,
        val SELECT_BACK: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_EDIT: Int = 3
        val SELECT_NEXT: Int = 5

    }

    //No contexto de um RecyclerView.Adapter, o método correto que você deve sobrescrever é onCreateViewHolder
    override  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder{
        return  MyViewHolder (
            ItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: TaskAdapter.MyViewHolder, position: Int) {

        val task = taskList[position]

        holder.binding.txtTitulo.text = task.titulo   //setar o titulo da tarefa
        holder.binding.txtDescricao.text = task.anotacao
        holder.binding.etDate.text = task.data

        holder.binding.btnRemover.setOnClickListener{taskSelect(task, SELECT_REMOVE)}
        holder.binding.btnEditar.setOnClickListener{taskSelect(task, SELECT_EDIT)}


        when(task.status){ //monitoramento referente a posição   /setas e direcionamento e eventos de click das mesmas
            0 -> {
                holder.binding.ibBack.isVisible = false    //qaundo estiver na posição 0 a seta vai ficar invisivel

                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.fazendo)
                )

                holder.binding.ibNext.setOnClickListener{taskSelect(task, SELECT_NEXT)} //evento click

            }
            1 -> {
                holder.binding.ibBack.setColorFilter(
                    ContextCompat.getColor(context, R.color.afazer)
                )
                holder.binding.ibNext.setColorFilter(
                    ContextCompat.getColor(context, R.color.feito)
                )
                holder.binding.ibBack.setOnClickListener{taskSelect(task, SELECT_BACK)} //evento click
                holder.binding.ibNext.setOnClickListener{taskSelect(task, SELECT_NEXT)} //evento click

            }else -> {

            holder.binding.ibNext.isVisible = false

            holder.binding.ibBack.setColorFilter(
                ContextCompat.getColor(context, R.color.fazendo)
            )

            holder.binding.ibBack.setOnClickListener{taskSelect(task, SELECT_BACK)}
        }
        }
    }



    override fun getItemCount() = taskList.size

    inner class  MyViewHolder(val binding: ItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)
}