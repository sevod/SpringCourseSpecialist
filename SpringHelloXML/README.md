Маленький демо проект с использованием spring. Будем использовать ApplicationContext. Конфигурировать компоненты будем через xml файл. 

С сайта https://mvnrepository.com/. Берем код для maven и вставляем в pom.xml
spring core, spring beans, spring context

Создаем файл applicationContext.xml.

В App.java подключаем context и прочее... От ApplicationContext много чего наследуется...

`ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");`



**Умные слова**: 

_Компонента_ берется из _Контейнера_. _Контейнер_ это фактически фабрика копонентов. _ApplicationContext_ это контейнер.

`spring.io` оффициальный сайт