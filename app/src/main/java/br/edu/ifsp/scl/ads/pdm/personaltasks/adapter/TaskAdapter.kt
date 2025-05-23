package br.edu.ifsp.scl.bes.prdm.tasklist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.ads.pdm.personaltasks.databinding.TileTaskBinding
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Task
import br.edu.ifsp.scl.ads.pdm.personaltasks.ui.OnClickTaskListener
import java.time.format.DateTimeFormatter
import br.edu.ifsp.scl.ads.pdm.personaltasks.R

class TaskRvAdapter(
    private val taskList: MutableList<Task>,
    private val onTaskClickListener: OnClickTaskListener
) : RecyclerView.Adapter<TaskRvAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(ttb: TileTaskBinding) : RecyclerView.ViewHolder(ttb.root) {
        val titleTv: TextView = ttb.titleTv
        val dueDateTv: TextView = ttb.dueDateTv

        init {
            // Menu de contexto (clique longo)
            ttb.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                (onTaskClickListener as AppCompatActivity).menuInflater.inflate(R.menu.context_menu_main, menu)

                menu.findItem(R.id.edit_task).setOnMenuItemClickListener {
                    onTaskClickListener.onEditTaskMenuItemClick(adapterPosition)
                    true
                }

                menu.findItem(R.id.remove_task).setOnMenuItemClickListener {
                    onTaskClickListener.onRemoveTaskMenuItemClick(adapterPosition)
                    true
                }
            }

            // Clique curto na célula
            ttb.root.setOnClickListener {
                onTaskClickListener.onTaskClick(adapterPosition)
            }
        }
    }

    // Chamado somente quando um novo holder (e consequentemente uma nova célula) precisa ser criado.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder = TaskViewHolder(
        TileTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    // Chamado sempre que os dados de um holder (ou seja, da célula) precisam ser preenchidos ou trocados.
    override fun onBindViewHolder(
        holder: TaskViewHolder,
        position: Int
    ) {
        taskList[position].let { task ->
            with(holder) {
                titleTv.text = task.title
                dueDateTv.text = task.dueDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            }
        }
    }

    override fun getItemCount(): Int = taskList.size
}
