package br.edu.ifsp.scl.ads.pdm.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.pdm.personaltasks.databinding.ActivityTaskFormBinding
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Constant.EXTRA_TASK
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Constant.EXTRA_VIEW_TASK
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Task
import java.time.LocalDate

class TaskActivity : AppCompatActivity() {
    private val atfb: ActivityTaskFormBinding by lazy {
        ActivityTaskFormBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(atfb.root)

        setSupportActionBar(atfb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "New task"

        val receivedTask = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TASK, Task::class.java)
        }
        else {
            intent.getParcelableExtra<Task>(EXTRA_TASK)
        }
        receivedTask?.let {
            supportActionBar?.subtitle = "Edit task"
            with(atfb) {
                titleEt.setText(it.title)
                descriptionEt.setText(it.description)
                // Atualiza o DatePicker com a data da tarefa
                val today = LocalDate.now()
                val date = it.dueDate ?: today

                dueDateDp.updateDate(
                    date.year,
                    date.monthValue - 1,
                    date.dayOfMonth
                )

                // Verificando se é só uma visualização de contato
                val viewTask = intent.getBooleanExtra(EXTRA_VIEW_TASK, false)
                if (viewTask) {
                    supportActionBar?.subtitle = "View task"
                    titleEt.isEnabled = false
                    descriptionEt.isEnabled = false
                    dueDateDp.isEnabled = false
                    saveBtn.visibility = View.GONE
                }
            }
        }

        with(atfb) {
            saveBtn.setOnClickListener {
                val dueDate = LocalDate.of(
                    dueDateDp.year,
                    dueDateDp.month + 1, // Corrige o mês (DatePicker começa em 0)
                    dueDateDp.dayOfMonth
                )

                Task(
                    receivedTask?.id?:hashCode(),
                    titleEt.text.toString(),
                    descriptionEt.text.toString(),
                    dueDate
                ).let { task ->
                    Intent().apply {
                        putExtra(EXTRA_TASK, task)
                        setResult(RESULT_OK, this)
                    }
                }
                finish()
            }
        }

        with(atfb) {
            cancelBtn.setOnClickListener {
                finish()
            }
        }
    }
}