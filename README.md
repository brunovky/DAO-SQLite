DAO-SQLite
==========

Códigos para manipulação de camada de dados, utilizando SQLite para Android.

> DBHelper
  
  Possui uma variável DB_NAME, onde deve conter o caminho do arquivo .db (SQLite).
  
> AbstractDAO

  Possui as principais funcionalidades do CRUD:
  
    :: insert(Object model)
      Envie uma entidade (Model) para persistir, e retorna um long com o ID da entidade persistida
    :: update(Object model, String where)
      Envie uma entidade (Model) para atualizar e uma String contendo a condição para a atualização, e retorna um int com o número de linhas atualizadas
    :: delete(Class<?> modelClass, String where)
      Envie a classe do Model a ser excluido e uma String contendo a condição para a exclusão, e retorna um int com o número de linhas excluidas
    :: selectOne(Class<?> modelClass, String where, String orderBy)
      Envie a classe do Model a ser consultado e uma String contendo a condição para a consulta, e retorna uma entidade (Object) devidamente preenchida
    :: selectMany(Class<?> modelClass, String where)
      Envie a classe do Model a ser consultado e uma String contendo a condição para a consulta, e retorna uma lista de entidades (List<?>) devidamente preenchida
      
> Convenção
  
  Os nomes dos atributos das entidades devem estar IDÊNTICAS com as que estão no banco de dados.
