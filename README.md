Курс по Spring от "Специалист". Внутри папок свои README.md
1. SpringHelloXML
2. SpringHello
3. SpringDIXML (Spring project Dependence Injection (DI) на xml)
4. SpringDIAnnotation (Spring project Dependence Injection (DI) на анотациях)
5. SpringDIJava (Spring project Dependence Injection (DI) на классах Java)
6. dbJDBC (работа с БД на низком уровне)
7. dbHibernate (ORM)
8. dbJPA  (добавляем абстракцию между spring и Hibernate для стандартизации кода и не зависимости от реализации ORM)
9. dbSpringData (Spring Data)
10. WebTest (Servlet, JSP)

Виды scope: 

-singleton (по умолчанию)

-prototype  (каждый раз новый обьект)

-session (для http для сесии)

-request (для http для одного запроса)

-global-session (глобальный http контекст)

hibernate одна из реализаций ORM - обьектно реализационных отображений.

hibernate можно использовать напрямую, а можно как имплиментацию JPA

entity - сущность

HQL обьектный язык запросов

Для работы с гиберней используем интерфейс сесий (Session) который получается через фабрику сесий (SessionFactory) которую мы сконфигурируем как компаненту спринга

JPA - java persistence API. Стандартный интерфейс для реализации API различных ORM

JPA поддерживает PersistenceContext, EntityManager, JPQL (Java Persistence QUERY Language)

Spring поддерживает JPA для разных поставщиков JPA

---------------
**ТРАНЗАКЦИИ**

Видео 4. Общий обзор.

Транзакции (ACID) (Atomic атомарность, Consistent непротиворичивость, Isolated изолированность, Durable долговечность)

Транзакции в java уже существовали в EJB (enterpise Java beans)

JTA диспетчер распределенных транзакций.

Есть протокол транзакций.

В некоторых "Серверах" уже есть встроенные транзакции, в том кат надо дополнительно встраивать.

**ТРАНЗАКЦИИ в spring**

AbstractPlatformTransactionManager (абстрактный класс) использует два интерфейса, TransactionDefinition и TransactionStatus

Есть много реализаций его, для разных видов (хибернейт, БД и т.д.)
 
TransactionDefinition есть методы... (в презентации)

Transaction Isolation Level  (4 уровня)

READ_UNCOMMITTED (чтение не закомиченных), READ_COMMITTED (чтение только закомиченных), ISOLATION_REPEATABLE_READ (если данные изменились, то прочтет те, которые были до изменения, за исключением новых), ISOLATION_SERIALIZABLE (все выполняется последовательно), ISOLATION_DEFAULT

Интерфейс TransactionDefinition

getPropagationBehavior() Поведение распространения TransactionDefinition. (и тут куча вариантов) Задает поведение транзакций.
TransactionDefinition.PROPAGATION_REAUIRED, TransactionDefinition.PROPAGATION_SUPPORTS, TransactionDefinition.PROPAGATION_MANDATORY, TransactionDefinition.PROPAGATION_REQUIRES_NEW

Интерфейс TransactionStatus. 

Позволяет понять откуда взялась транзкция (новая, существующая и т.д.)

В Spring транзакции можно настроить тремя способами. В коде, анотации, xml. Стараются в анотациях. 

Для работы транзакций в Spring нужна компонента 

	<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
          <property name="entityManagerFactory" ref="emf" />
    </bean>
    
А также spring-tx

    <tx:annotation-driven transaction-manager="transactionManager" />
    
Сами транзакции навешиваются анотацией @Transactional    

---------------------------------------------

**WEB - приложение**

Servlet'ы компоненты которые обрабатывают запросы на сервере. Технически это классы которые реализуют интерфейсы из JavaEE. Работает через web - сервер. 
Сервер должен реализовывать определенные интерфейсы, которые описаны в JavaEE. 

Основные команды http. GET POST PUT DELETE.

JSP Java server page надстройка на Servlet. html страница с вкраплениями java кода.

JSTL развитие JSP

MVC  Представление <-> Контролер -> Модель ->

MVC создан что бы разделить код на три вида сущности, тем самым упростив подход к программированию

Модель - Это структура данных.

V Представление - описание того что увидит пользователь. Используют например html, JSP/JSTL

Контролер связывает воедины V и M. Задача выдать по запросу клиента, пердставление

DispatcherServlet - главный сервлет в SpringMVC который получает запросы и перенаправляет их контролерам. Их может быть много. 
У каждого экземпляра есть конфигурация в WebApplicationContext

**Server TomCat** 

... для запуска в виндовс должны быть указаны переменные 
среды JRE и JDK. JRE версии выше 8 может не зарабоать?

Что бы настроить `"Manager App"` нажимаем туда, выводит зарос Л П.
Отменяем и нам пишет где и что настраивать (conf/tomcat-users.xml)

    <role rolename="manager-gui"/>
    <user username="admin" password="admin" roles="manager-gui"/>		
    
На этой стронице мы можем вручную деплоить наши преложения использую WAR файлы    

Создаем новый проект. Выбираем maven-archetype-webapp. 
Создается новые проект с определенной структурой папок

Добавляю плагин `smart tomcat` https://anoopchiplunkar.wordpress.com/2019/07/31/configure-apache-tomcat-server-on-intellij-idea-community-edition/

web.xml - это диплоймент дискриптор. С помощью него можно связать сервлет с контейнером. Или другой вариант анотации.
    
    	



