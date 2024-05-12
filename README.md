<ul>
<li><a name="backend">Backend</a> </li>
<li><a name="backend">Frontend</a> </li>
</ul>

# Запуск Backend части(#backend)

## Основные команды

### Шаг 1
Для запуска jar файла и создания базовых настроек БД необходимо ввести следующую команду:
>```java -jar {путь до файла}/hackaton1.0.0SNAPSHOT --spring.datasource.password={пароль} --spring.datasource.username={user} --spring.datasource.url=jdbc:postgresql://{ссылка на базу}```

#### Замечания
<ul>
<li>Версия java должна быть не меньше 21.</li>
<li>Используемая БД должна быть postgres</li>
</ul>

### Шаг 2
Для включения preview режима с пред заполнеными пользователями и уже созданным кланом с задачами и предметами исполнить необходимо следующкую команду:

>```java -jar {путь до файла}/hackaton1.0.0SNAPSHOT --spring.datasource.password={пароль} --spring.datasource.username={user} --spring.datasource.url=jdbc:postgresql://{ссылка на базу} -preview```


#### Пояснение
После ввода команды <i>preview</i>, в базе данных появятся несколько задач, пользователей, а также предметов в магазине.

#### Далее описаны <i>email</i> адреса пользователей, которые также являются <i>login'ом</i>:

<ul>
<li>alexandr@zadorozniy.ru</li>
<li>vadim@smirnov.ru</li>
<li>arseniy@korolev.ru</li>
<li>ivan@kuznetsov.ru</li>
</ul>

#### Замечание
Пароль от всех аккаунтов один: ```12345678```


#### Дополнительная информация
При запуске приложения имеется автоматически сгенерированный swagger по адрессу: http://localhost:8080/swagger-ui/index.html

Для авторизации пользователя в user controller есть метод api/user/login. 
В теле ответа придёт токен, который необходимо внести в authorize (Он находится сверху сайта, с правой стороны, знак замочек)


#### Замечание
jar file находится в zip файле hackaton1.0.1SNAPSHOT.zip
