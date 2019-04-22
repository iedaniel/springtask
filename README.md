# springtask

После запуска Spring необходимо открыть в браузере адрес localhost:8080 -- там всё необходимое. Так же стоит установить sqlite expert master для более детального просмотра записей внутри базы (она хранится в файле memory). Если есть желание искать, удалять, добавлять пользователя http-запросами, надо написать в строке браузера или curl-запросом следующее:

localhost:8080/email=[email]

localhost:8080/delete-user?id=[id]

localhost:8080/create-user?name=[name]&surname=[surname]&birth=[birth]&email=[email]&password=[password]

также можно очистить всю таблицу, введя localhost:8080/drop-table
