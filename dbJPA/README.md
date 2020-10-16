**Java persistence API.**

Делаем аналогично предыдущему примеру, но через JPA, что бы не было привязки к конкретной ORM. 
Привязка только в xml, где все можно быстро изменить.

JPA - java persistence API. Стандартный интерфейс для реализации API различных ORM

JPA поддерживает PersistenceContext, EntityManager, JPQL (Java Persistence QUERY Language)

Spring поддерживает JPA для разных поставщиков JPA

1. Подключаем библиотеки Maven. Копируем все из прошлого проекта.

1.1. Поключаем `Java Persistence API, Version 2.1`

2. Копируем код из предыдущего проекта

3. Правим applicationContext.xml.

3.1. Создаем наш основной бин `class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean" `

3.2. Создаем transactionManager

4. Создаем новый класс JPACourseDAO

4.1. Навешиваем транзакции

    @Service("jpaCourseService") //Это метод создания бина
    @Repository
    @Transactional
    
4.2. Добавляем лог

    private Log log = LogFactory.getLog(JPACourseDAO.class);
    
4.3. Создаем EntityManager который будет управлять PersistenceContext

    @PersistenceContext
    private EntityManager em;   
    
5. Реализовываем методы findById() findAll()

5.1. Навешиваем транзакции

    @Transactional(readOnly = true)
    
5.2. Реализуем метод findAll(). JPA запрос, не sql. "Course" это класс, а не имя таблицы.

    public List<Course> findAll() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }    
    
5.3. Реализуем метод findById()

    public Course findById(int id) {
        TypedQuery<Course> query = em.createQuery("select c from Course c where c.id=:id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }    
    
5.4. Проверяем

5.6. Доделываем оставшиеся методы и проверяем еще раз.

5.7. Можно использовать "именованный запрос". Навешиваем еще одну анотацию на `Course.java` и переписываем `findAll()`.

    @NamedQueries({
        @NamedQuery(name = "Course.findAll", query="select c from Course c")
    })
    
    //return em.createQuery("select c from Course c", Course.class).getResultList();
    return em.createNamedQuery("Course.findAll", Course.class).getResultList();

    