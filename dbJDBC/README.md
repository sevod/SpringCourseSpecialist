Commit 1
---------------

Будем вручную работать с базой данных из Java. Будем использовать MySQL. Пользователь spec, логин Spec123456

1. В MySQL создаем Schema web и загрузим туда данные из файла web.sql

2. Добавим зависимости в Maven (pom.xml)
2.1. MySQL Connector/J
2.2. Spring JDBC

3. Создаем новый пакет ru.specialist.DAO

3.1. Создаем клас Course с такими же полями как и в sql. Добавляем гетеры и сетеры.

3.2 Создадим интерфейс CourseDAO. В этом интерфейсе будут заголовки методов для работы с БД.

3.3. Создадим клас JdbcCourseDAO который будет реализовывать интерфейс.

4. Для получения данных в классе JdbcCourseDAO  мы будем использовать getJdbcTemplate. Это метод springframework.

4.1. В `getJdbcTemplate().queryForObject` первый параметр sql запрос, вотрой параметр это параметры sql запроса,
третий параметр это класс который является шаблоном и из приходящих данных от sql будет создавать java обьект.

    public Course findById(int id) { 
         return (Course)getJdbcTemplate().queryForObject(SQL_SELECT_COURSE_BY_ID, new Object[]{id},
                 new CourseRowMapper());
    }
    
5. Для конфигурирования getJdbcTemplate нам понадобится бины. 
5.1. Создаем applicationContext.xml и копируем туда корневые бины из предыдущего проекта.
5.2. Настраиваем соединение с помощью бина
```
    <bean id="webDataSource"
          class="org.springframework.jdbc.datasource.DriverManagerDataSource" >
<!--        тут имя драйвера, берется из док., мы его подключали Maven-->
        <property name="driverClassName" value="com.mysql.jdbc.Driver" />
<!--        адрес подключения и наша база-->
        <property name="url" value="jdbc:mysql://localhost:3306/web" />
        
        <property name="username" value="spec" />
        <property name="password" value="Spec123456" />
    </bean>
```
5.3. Настраиваем jdbc

    <bean id="jdbcTemplate"
          class="org.springframework.jdbc.core.JdbcTemplate">
        <constructor-arg ref="webDataSource" />
    </bean>
    
5.4. Создаем бин для нашго класса  courseDao, в параметрах бин jdbcTemplate, но можно было использовать и autowiring

    <bean id="courseDao"
          class="ru.specialist.DAO">
        <property name="jdbcTemplate" ref="jdbcTemplate" />
    </bean>
    
6. Пишем само приложение App.java
6.1 Вызываем контекст и получаем наш клас с данными.
```
    CourseDAO courseDAO = context.getBean(CourseDAO.class);
```
6.2. Теперь мы можем делать запросы к БД. Например `courseDAO.findById(5)`        

6.3. Вылезла ошбика связанная с временными зонами. Решил `set global time_zone = '-2:00';` в sql. Возможно правильнее было переписать бин.

6.4 В классе Course.java переопределим toString для карсивого отображения.

6.5 Доделывам метод `findAll()`

6.5.1 Сначала делаем это вручную

6.5.2 Второй варинат, если названия колонок в Java и SQL совпадают то используем BeanPropertyRowMapper из spring. Еще можно использовать свой, как выше.

    courses = getJdbcTemplate().query(SQL_SELECT_COURSE, new BeanPropertyRowMapper(Course.class));
    
6.5.3 Используем ранее наме написаный CourseRowMapper. Можно им пользоваться когда имена не совпадают.