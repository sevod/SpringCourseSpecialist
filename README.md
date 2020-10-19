Курс по Spring от "Специалист". Внутри папок свои README.md
1. SpringHelloXML
2. SpringHello
3. SpringDIXML (Spring project Dependence Injection (DI) на xml)
4. SpringDIAnnotation (Spring project Dependence Injection (DI) на анотациях)
5. SpringDIJava (Spring project Dependence Injection (DI) на классах Java)
6. dbJDBC
7. dbHibernate
8. dbJPA 
9. dbSpringData

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