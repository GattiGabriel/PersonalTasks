package br.edu.ifsp.scl.ads.pdm.personaltasks.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.ads.pdm.personaltasks.controller.MainController
import br.edu.ifsp.scl.ads.pdm.personaltasks.databinding.ActivityMainBinding
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Constant.EXTRA_TASK
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Constant.EXTRA_VIEW_TASK
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Task
import br.edu.ifsp.scl.bes.prdm.tasklist.adapter.TaskRvAdapter

class MainActivity : AppCompatActivity(), OnClickTaskListener {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val taskList: MutableList<Task> = mutableListOf()

    // Adapter
    private val taskAdapter: TaskRvAdapter by lazy {
        TaskRvAdapter(taskList, this)
    }

    private lateinit var carl: ActivityResultLauncher<Intent>

    //Contoller
    private val mainController: MainController by lazy {
        MainController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.toolbarIn.toolbar)
        supportActionBar?.subtitle = "Task list"

        carl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(EXTRA_TASK, Task::class.java)
                }
                else {
                    result.data?.getParcelableExtra<Task>(EXTRA_TASK)
                }
                task?.let { receivedTask ->
                    // Verificar se é um novo contato ou se é um contato editado.
                    val position = taskList.indexOfFirst { it.id == receivedTask.id }
                    if (position == -1) {
                        taskList.add(receivedTask)
                        taskAdapter.notifyItemInserted(taskList.lastIndex)
                        mainController.insertTask(receivedTask)
                    }
                    else {
                        taskList[position] = receivedTask
                        taskAdapter.notifyItemChanged(position)
                        mainController.modifyTask(receivedTask)
                    }
                }
            }
        }

        amb.taskRv.adapter = taskAdapter
        amb.taskRv.layoutManager = LinearLayoutManager(this)

        fillTaskList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(br.edu.ifsp.scl.ads.pdm.personaltasks.R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            br.edu.ifsp.scl.ads.pdm.personaltasks.R.id.new_task -> {
                carl.launch(Intent(this, TaskActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onTaskClick(position: Int){
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            putExtra(EXTRA_VIEW_TASK, true)
            startActivity(this)
        }
    }

    override fun onRemoveTaskMenuItemClick(position: Int) {
        mainController.removeTask(taskList[position].id!!)
        taskList.removeAt(position)
        taskAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "Task removed!", Toast.LENGTH_SHORT).show()
    }

    override fun onEditTaskMenuItemClick(position: Int) {
        Intent(this, TaskActivity::class.java).apply {
            putExtra(EXTRA_TASK, taskList[position])
            carl.launch(this)
        }
    }


    private fun fillTaskList(){
        taskList.clear()
        Thread {
            taskList.addAll(mainController.getTasks())
            taskAdapter.notifyDataSetChanged()
        }.start()
    }
}