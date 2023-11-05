package com.grud.lojavirtualfirebase.Modal

import android.os.Parcelable
import com.grud.lojavirtualfirebase.helper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
class Task (
    //objeto task
    var id: String = "",
    var titulo: String = "",
    var anotacao: String = "",
    var data: String = "",
    var status: Int = 0

): Parcelable  {
    init{            //contrutor inicial   - .push() gera id  unico para ficar no firebase
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}