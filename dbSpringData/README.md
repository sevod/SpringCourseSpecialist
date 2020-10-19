**SPRING DATA**

Базовые вещи.
Позволяет автоматизировать создание сервесов CRUD. 
Одной из важных концепций здесь, это абстракция "Репозиторий". В нем есть готые методы findAll findById и т.д.

1. В Maven добавляем модуль `Spring Data JPA`

2. Создаем новый интерфейс `CourseRepository.java`. 
Главное в нем что он унаследован от `CrudRepository<Course, Integer>`, который берется из spring data. 
Он дженерик и типизируется типом сущности которую мы планируем получать и тип первичного ключа.

3. В CourseService находятся методы которые мы будем использовать для работы с БД.

4. Для реализации создаем репозиторий SpringJpaCourseService и имплементим в него CourseService. 
В `courseRepository` с помощью `@Autowired` spring будет инжектить  из spring data. И там будут доступны методы CRUD
```
    @Autowired
    private CourseRepository courseRepository;

```

4.1 В методе findAll() мы получем данные с помощью `courseRepository` по сути с помощью spring data

    @Override
    public List<Course> findAll() {
        return new ArrayList<Course>(courseRepository.findAll());
    }    
    
5. В applicationContext.xml добавляем в шапку
```
    xmlns:jpa="http://www.springframework.org/schema/data/jpa" 
    http://www.springframework.org/schema/data/jpa
    http://www.springframework.org/schema/data/jpa/spring-jpa.xsd
```

5.1. В тело добавляем

        <jpa:repositories
                base-package="ru.specialist.DAO"
                entity-manager-factory-ref="emf"
                transaction-manager-ref="transactionManager"/>
                
6. Дописываем App, запускаем проверяем.                

   