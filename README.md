Для запуска файла необходимо исполнить команду в cmd:

    java -jar {путь до файла}/hackaton1.0.0SNAPSHOT --spring.datasource.password={пароль} --spring.datasource.username={user} --spring.datasource.url=jdbc:postgresql://{ссылка на базу}

Версия java должна быть не меньше 21.
Используемая БД должна быть postgres

Для включения preview режима с пред заполнеными пользователями и уже созданным кланом с задачами и предметами исполнить необходимо следующкую команду:

    java -jar {путь до файла}/hackaton1.0.0SNAPSHOT --spring.datasource.password={пароль} --spring.datasource.username={user} --spring.datasource.url=jdbc:postgresql://{ссылка на базу} -preview

Предсозданные аккаунты в режиме preview: 

alexandr@zadorozniy.ru
vadim@smirnov.ru
arseniy@korolev.ru
ivan@kuznetsov.ru

Пароль от всех аккаунтов один: 12345678

При запуске приложения имеется автоматически сгенерированный swagger по адрессу: http://localhost:8080/swagger-ui/index.html

Для авторизации пользователя в user controller есть метод api/user/login. 
В теле ответа придёт токен, который необходимо внести в authorize (Он находится сверху сайта, с правой стороны)

jar file находится в zip файле hackaton1.0.1SNAPSHOT.zip
