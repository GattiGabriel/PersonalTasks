package br.edu.ifsp.scl.ads.pdm.personaltasks.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.sql.SQLException
import br.edu.ifsp.scl.ads.pdm.personaltasks.R
import java.time.LocalDate

class TaskSQLite(context: Context): TaskDAO {
    companion object {
        private val TASK_DATABASE_FILE = "taskList"
        private val TASK_TABLE = "task"
        private val ID_COLUMN = "id"
        private val TITLE_COLUMN = "title"
        private val DESCRIPTION_COLUMN = "description"
        private val DUE_DATE_COLUMN = "dueDate"

        val CREATE_TASK_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $TASK_TABLE ( " +
                "$ID_COLUMN INTEGER NOT NULL PRIMARY KEY, " +
                "$TITLE_COLUMN TEXT NOT NULL, " +
                "$DESCRIPTION_COLUMN TEXT NOT NULL, " +
                "$DUE_DATE_COLUMN TEXT NOT NULL );"
    }

    // Criando uma instância de SQLite
    private val taskDatabase: SQLiteDatabase = context.openOrCreateDatabase(
        TASK_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )

    // Criando a tabela de tarefas
    init {
        try {
            taskDatabase.execSQL(CREATE_TASK_TABLE_STATEMENT)
        }
        catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }

    override fun createTask(task: Task) =
        taskDatabase.insert(TASK_TABLE, null, task.toContentValues())

    override fun retrieveTask(id: Int): Task {
        val cursor = taskDatabase.query(
            true,
            TASK_TABLE,
            null,
            "$ID_COLUMN = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            cursor.toTask()
        }
        else {
            Task()
        }
    }

    override fun retrieveTasks(): MutableList<Task> {
        val taskList: MutableList<Task> = mutableListOf()

        val cursor = taskDatabase.rawQuery("SELECT * FROM $TASK_TABLE;", null)

        while (cursor.moveToNext()) {
            taskList.add(cursor.toTask())
        }

        return taskList
    }

    override fun updateTask(task: Task) = taskDatabase.update(
        TASK_TABLE,
        task.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(task.id.toString())
    )

    override fun deleteTask(id: Int) = taskDatabase.delete(
        TASK_TABLE,
        "$ID_COLUMN = ?",  // Remove o "WHERE"
        arrayOf(id.toString())
    )


    private fun Task.toContentValues() = ContentValues().apply {
        put(ID_COLUMN, id)
        put(TITLE_COLUMN, title)
        put(DESCRIPTION_COLUMN, description)
        put(DUE_DATE_COLUMN, dueDate.toString())
    }

    private fun Cursor.toTask() = Task(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(TITLE_COLUMN)),
        getString(getColumnIndexOrThrow(DESCRIPTION_COLUMN)),
        LocalDate.parse(getString(getColumnIndexOrThrow(DUE_DATE_COLUMN)))
    )
}