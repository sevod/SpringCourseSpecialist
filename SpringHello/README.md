Маленький демо проект с использованием spring. (продолжение SpringHelloXml)

В основе SpringHelloXml. Будем делать тоже самое, но без xml.

С сайта https://mvnrepository.com/. Берем код для maven и вставляем в pom.xml
spring core, spring beans, spring context

Создаем класс AppContext.java. Он будет возвращать нам компоненту. Для этого будем использовать анотации. `@Configuration, @Bean`
После этого мы подключаем это все в App.java. Уже используем другой класс для этого.
`AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);`

**Умные слова**: 

_Компонента_ берется из _Контейнера_. _Контейнер_ это фактически фабрика копонентов. _ApplicationContext_ это контейнер.
 _POJO_ обьект. Анотации.

`spring.io` оффициальный сайт