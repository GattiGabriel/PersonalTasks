package br.edu.ifsp.scl.ads.pdm.personaltasks.controller

import br.edu.ifsp.scl.ads.pdm.personaltasks.model.Task
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.TaskDAO
import br.edu.ifsp.scl.ads.pdm.personaltasks.model.TaskSQLite
import br.edu.ifsp.scl.ads.pdm.personaltasks.ui.MainActivity

class MainController(mainActivity: MainActivity) {
    private val taskDAO: TaskDAO = TaskSQLite(mainActivity)

    fun insertTask(task: Task) = taskDAO.createTask(task)
    fun getTask(id: Int) = taskDAO.retrieveTask(id)
    fun getTasks() = taskDAO.retrieveTasks()
    fun modifyTask(task: Task) = taskDAO.updateTask(task)
    fun removeTask(id: Int) = taskDAO.deleteTask(id)
}