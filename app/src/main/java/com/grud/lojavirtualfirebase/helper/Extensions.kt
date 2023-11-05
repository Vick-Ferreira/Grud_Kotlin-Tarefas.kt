package com.grud.lojavirtualfirebase.helper

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

//NAVEGAÇÕES TOBAR SÃO UTILIZADAS APARTIR DE FRAGMENTES NESSE CASO


fun Fragment.initToolbar(toolbar: Toolbar){
    //inicialização das toobal nos arquivos fragments (comportamento , ativando titulo, btn, )

    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = "" //já nomeamos com textview na toobar
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)//habilitando o btn de voltar
    toolbar.setNavigationOnClickListener{ activity?.onBackPressed()}//voltando para pagina anterior
    toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))//color do text toobar

}