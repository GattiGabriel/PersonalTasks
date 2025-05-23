*Aplicativo Avaliativo 1 - PersonalTasks*

Aplicativo de gerenciamento de tarefas desenvolvido em Kotlin, utilizando como base a API 26 do Android.

Conta com as seguintes funcionalidades:

- Adicionar Tarefa: Ao clicar no ícone de adicionar no canto superior direito, presente na toolbar, será aberto uma segunda tela com um formulário para o preenchimento do título da tarefa, sua breve descrição e também é oferecido um DatePicker para determinar qual a data limite para o cumprimento de tal tarefa. Se o botão Salvar é clicado, os dados são persistidos no banco, o aplicativo retorna para a tela inicial e nela um card interativo é criado contendo as informações previamente descritas. Se o botão Cancelar é clicado, os dados não serão salvos e o usuário retorna para a tela inicial.

- Detalhes da Tarefa: Se um card é clicado diretamente, o usuário é redirecionado para uma tela idêntica ao formulário de adição de tarefa, mas agora seus campos estão preenchidos pelos dados da tarefa clicada e são desabilitados para edição. Ao clicar no botão Cancelar, o usuário retorna para a tela inicial.

- Menu de Contexto: Ao manter pressionado o clique em algum card, é exibido um menu de contexto com as opções de Edição e Exclusão da tarefa.

- Editar Tarefa: A opção de Editar tem o mesmo comportamento que a opção de Detalhes, porém com a possibilidade de editar os campos livremente. Ao clicar no botão Salvar, os dados são sobrescritos, caso sejam mudados, retornando assim o usuário para a tela inicial, onde os dados editados já estão disponíveis, e ao clicar no botão Cancelar, o usuário retorna para a tela inicial.

- Excluir Tarefa: A opção de Excluir remove os dados de determinada tarefa do banco e retira o card relacionado a ela da tela inicial.
