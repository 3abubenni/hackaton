Перед компиляцией в папке https://github.com/3abubenni/hackaton/blob/master/src/main/resources/application.properties необходимо указать данные базы данных.
Используемая база данных - postgres

Некоторое пояснение о spring.jpa.hibernate.ddl-auto - при запуске можно оставить как create-drop, но при таком сценарии база данных каждый раз будет очищаться.
Меняем параметр на update, и, как следствие, все данные при запуске на базе данных остаются невредимыми
