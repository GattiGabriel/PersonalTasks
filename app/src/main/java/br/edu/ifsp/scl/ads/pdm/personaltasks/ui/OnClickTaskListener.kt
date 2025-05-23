package br.edu.ifsp.scl.ads.pdm.personaltasks.ui

sealed interface OnClickTaskListener {
    fun onTaskClick(position: Int)

    // Funções abstratas para o menu de contexto
    fun onRemoveTaskMenuItemClick(position: Int)
    fun onEditTaskMenuItemClick(position: Int)
    fun onDetailsTaskMenuItemClick(position: Int)
}