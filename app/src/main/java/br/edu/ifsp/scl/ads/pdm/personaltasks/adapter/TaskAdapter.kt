package br.edu.ifsp.scl.bes.prdm.tasklist.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import br.edu.ifsp.scl.ads.pdm.personaltasks.R
import br.edu.ifsp.scl.ads.pdm.personaltasks.databinding.TileTaskBinding
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Task
import java.time.format.DateTimeFormatter

class TaskAdapter(context: Context, private val taskList: MutableList<Task>) :
    ArrayAdapter<Task>(
        context,
        R.layout.tile_task,
        taskList
    ) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Recuperar a task que será usada para preencher os dados da célula
        val task = taskList[position]

        // Verificar se existe uma célula reciclada ou se é necessário inflar uma nova
        var taskTileView = convertView
        if (taskTileView == null) {
            // Infla uma nova célula
            TileTaskBinding.inflate(
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            ).apply {
                val tileTaskViewHolder = TileTaskViewHolder(titleTv, dueDateTv, statusTv)
                taskTileView = root
                (taskTileView as LinearLayout).tag = tileTaskViewHolder
            }
        }

        // Preencher a célula com os dados da task
        val viewHolder = taskTileView?.tag as TileTaskViewHolder
        viewHolder.titleTv.text = task.title
        viewHolder.dueDateTv.text = task.dueDate?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        viewHolder.statusTv.text = task.status

        // Devolver a célula preenchida para o ListView
        return taskTileView as View
    }

    private data class TileTaskViewHolder(
        val titleTv: TextView,
        val dueDateTv: TextView,
        val statusTv: TextView
    )
}
