**Hibernate** 

1. Подключаем зависимости в Maven.

1.1. Подключаем  Spring Transaction Hibernate работает всегда с транзакциями, поэтому нужен этот модуль. 
Рекомендовали взять версию 4.3. Попробую с 5

1.2. Подключаем Spring Object/Relational Mapping. Используется не только с Hibernate, но и сдругими ORM

1.3. Подключаем JavaEE API. Для разметки сущностей нужен этот модуль.

1.4. Hibernate EntityManager но я попробую Hibernate EntityManager Relocation. Этот пакет должен подтянуть и другие зависимости Hibernate.

2. Копируем часть файлов из предыдущиего проекта.

2.1. В applicationContext.xml оставляем только `<bean id="webDataSource"` и `<context:property-placeholder`

2.2. Добавляем схему для транзакций и ... 

    xmlns:tx = "http://www.springframework.org/schema/tx"
   
    http://www.springframework.org/schema/tx
    http://www.springframework.org/schema/tx/spring-tx.xsd
    
2.3. Добавляем строку, которая означает что будем конфигурировать анотациями

    <tx:annotation-driven />    
    
    
3 Из предыдущего проекта переносим файлы из пакета ru.specialist.dbHibernate

4 Что бы знал где искать "репозиторий"

    `<context:component-scan base-package="ru.specialist.dbHibernate" />`
    
5.0. Начинаем настраивать hibernate `<util:properties id="hibernateProperties">`

5.1. Указываем диалект SQL с которым он будет работать.

    <prop key="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</prop>  
    
5.2. Максимальная глубина одновременного извлечения данных по сылкам. 

    <prop key="hibernate.max_fetch_depth">3</prop>

5.3. Максимальное количество извлекаемых строк из запроса.   

    <prop key="hibernate.max_fetch_size">50</prop>
    
5.4. Максимальное количество отправляемых строк.

    <prop key="hibernate.max_batch_size">10</prop>
    
5.5. Показывать в лог или не показывать sql команды которые генерирует hibernate

    <prop key="hibernate.show_sql">false</prop>    
    
5.6 В итоге

        <util:properties id="hibernateProperties">
            <prop key="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</prop>
            <prop key="hibernate.max_fetch_depth">3</prop>
            <prop key="hibernate.max_fetch_size">50</prop>
            <prop key="hibernate.max_batch_size">10</prop>
            <prop key="hibernate.show_sql">false</prop>
        </util:properties>
        
6.0 Необходимо сконфигурировать два бина. SessionFactory и 
 
6.1.1 SessionFactory Он будет выдавать нам сесии с БД.    

    <bean id="sessionFactory"
          class="org.springframework.orm.hibernate5.LocalSessionFactoryBean"
          p:dataSource-ref="webDataSource"
          p:packagesToScan="ru.specialist.dbHibernate"
          p:hibernateProperties-ref="hibernateProperties"
    />   

6.2. Transaction Manager. Принципиально название ID = "transactionManager". По нему будет идти поиск спрингом.

        <bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager"
              p:sessionFactory-ref="sessionFactory" />
              
7. Разметим класс сущности Course.java. Будем использовать разметку jpa из нэймспэйса `javax.persistence`.

7.1. На сам класс навешиваем анотацию `@Entity` (сущность)

7.2. @Table(name="courses") Таблица sql которой соответствует данный класс.

7.2. На метод getId() (а может на переменную id ?) навешиваем анотации

        @Id //означает что это первичный ключ
        @GeneratedValue(strategy = GenerationType.IDENTITY) //означает что автогенерируется и стратегию генерации
        @Column(name="id") //название колонки в БД

7.3. На остальные навешиваем только 

    @Column(name="nameColumn")
    
8. Делаем класс который будет реализовывать наш репозиторий `HibernateCourseDAO`

8.1. Создаем переменну `private SessionFactory sessionFactory;`  

8.2. Гетеры и Сетеры. В сетер будем передавать из applicationContext.xml  `sessionFactory`. Делаем это

    @Resource(name = "sessionFactory")
    
8.3. Сам класс размечаем

    @Repository("courseDao")
    
8.4. Так же на класс добавляем анотоцию @Transactional. Это работает, потому что в xml есть <tx:annotation-driven/>
    Транзакцию берем из спринга import org.springframework.transaction.annotation.Transactional;

    @Transactional      
    
8.5. Так же навешиваем транзакцию на методы получения данных из БД. С параметром readOnly=true

    @Transactional(readOnly=true)    
    
8.6. Для получения логов используем готовые функции

    private static final Log LOG = LogFactory.getLog(HibernateCourseDAO.class);
    
8.7. Реализуем метод findAll()

    return getSessionFactory().getCurrentSession().createQuery("from Course c").list();    
    
8.8. Реализуем метод findById(int id) 
    
    return (Course) getSessionFactory().getCurrentSession().byId(Course.class).load(id);     
    
9. Копируем файл App из предыдущего приложения. Все работает.

10. Можно поэксперементировать со строкой <prop key="hibernate.show_sql">false</prop>

11. В классе Course.java все анотации, перевесил на пременные. Были над методами.           
    
      
    
               
    
        
        