package com.grud.lojavirtualfirebase.ui.Tabs

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

// criando um fragmento que exibe um diálogo de seleção de data (um calendário) para o usuário.
//ouvinte recebe o valor que queremos retornar, portanto aqui temos uma classe, que vai receber uma função com três paramêtros
class DatePickerFragment(val listener: (dia: Int, mes: Int, ano: Int) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    //função de click se seleção do user
    override fun onDateSet(view: DatePicker?, ano: Int, mes: Int, dia: Int) {
        val mesAjustado = mes + 1 // Ajuste do valor do mês
        listener(dia, mesAjustado, ano)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendario = Calendar.getInstance()
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val mes = calendario.get(Calendar.MONTH)
        val ano = calendario.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context, this, ano, mes, dia)
        return picker
    }
}