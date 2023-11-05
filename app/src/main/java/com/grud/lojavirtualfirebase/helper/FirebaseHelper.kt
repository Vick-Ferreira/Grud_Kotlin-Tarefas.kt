package com.grud.lojavirtualfirebase.helper

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.grud.lojavirtualfirebase.R

class FirebaseHelper {

    companion object {
        fun getDatabase() = FirebaseDatabase.getInstance().reference //retornar referencia de banco de dados sempre que chamarmos essa função

        fun getAuth() = FirebaseAuth.getInstance()//recuperar a estancia do user(saber se ta logado, pegar ID)

        fun getIdUser() = getAuth().uid //id do user autenticado

        fun isAutenticacao() = getAuth().currentUser != null //user está autenticado no app




        //tratando erros
        fun validErro(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> { //nenhuma conta
                    R.string.account_not_registered_redister_fragment
                }
                error.contains("The email address is badly formatted.") -> {  //email
                    R.string.invalid_email_register_fragment
                }
                error.contains(" The given password is invalid.") -> {  //senha
                    R.string.invalid_password_register_fragment
                }
                error.contains("email_in_use_register_fragment") -> {  //e-mail em uso
                    R.string.email_in_use_register_fragment
                }
                error.contains("strong_password_register_fragment") -> { //senha mais forte
                    R.string.strong_password_register_fragment
                }
                error.contains("We have blocked all requests from this device due to unusual activitvo Android Crud Firebase - #1y. Try again later.") -> {
                    R.string.We_have_blocked_all_requests_from_this_device_due_to_unusual_activity
                }
                error.contains("An internal error has occurred.") -> {
                    R.string.erro_generico
                }
                else ->{
                    R.string.erro_generico
                }
            }
            //erros do firabesa e exibir ao user
        }
    }
}