package br.edu.ifsp.scl.ads.pdm.personaltasks.model

import android.os.Parcelable
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Constant.INVALID_TASK_ID
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Task(
    var id: Int? = INVALID_TASK_ID,
    var title: String = "",
    var description: String = "",
    var dueDate: LocalDate? = null,
    var status: String = "Incompleto"
): Parcelable