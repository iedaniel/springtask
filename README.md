# springtask

После запуска Spring необходимо открыть в браузере адрес localhost:8080 -- там всё необходимое. Если есть желание искать, удалять, добавлять пользователя http-запросами, надо написать в строке браузера или curl-запросом следующее:

localhost:8080/email=[email]

localhost:8080/delete-user?id=[id]

localhost:8080/create-user?name=[name]&surname=[surname]&birth=[birth]&email=[email]&password=[password]

также можно очистить всю таблицу, введя localhost:8080/drop-table
