Spring MVC

1. Создаем новый проект. Выбираем maven-archetype-webapp.

2. Копируем maven зависимости из двух предыдущих проектов. 

2.1. Дополнительно. `spring-webmvc` и `guava`- вспомогательная библиотека для преобразовании коллекции в List.

3. Конфигурируем диплоймент дискриптор. web.xml. Указываем где будет распологатся `contextConfigLocation` (в итоге копи паст)
```
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring/root-context.xml</param-value>
    </context-param>
```

3.1. Добавляем фильтры спринга.

3.1.1. Обрабатывает методы отличные от get и post. Это уже функционал Spring MVC
    
    <filter>
            <filter-name>HttpMethodFilter</filter-name>
            <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
    </filter>
    
3.1.2. Фильтр для кодировки 

    `<filter-name>CharacterEncodingFilter</filter-name>`
    
3.1.3 Филтер для привязки диспетчера сущностей JPA

      <filter>
        <filter-name>Spring OpenEntityManagerinViewFilter</filter-name>
        <filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
      </filter>    
      
3.1.4 Фильтр мапинг. Указать для каких URL какие фильтры применять. Назавание фильтра и правила

    <filter-mapping>
        <filter-name>Archetype Created Web Application</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    
3.2. Контекст лисенера (назнаю что это)
    
    <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>        
    
3.3. Диспетчер сервлетов. Указываем в каком файле будет сконфигурирован. `load-on-startup` указывает порядок загрузки, 
порядок обработки пришедшего запроса сервлетом если их несколько.

    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup> 
    </servlet> 
    
3.3.1 Сервлет мапинг. Какие адерса сервлет обрабатывает.

    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>    

4. Создаем и конфигурируем контекст servlet-context.xml (взял готовый)

    
    
4.1. Внутри один новый нэймспэс

    xmlns:mvc="http://www.springframework.org/schema/mvc"
    
4.1 Указываем по каким адресам лежат статические ресуры

    <mvc:resources mapping="/resources/**" location="/resources/" />
    
4.2. Указываем что основную информацию нужно читать из анотации, это указание для spring mvc

    <mvc:annotation-driven>
    
4.3. Где искать компаненты

    <context:component-scan base-package="ru.specialist.controllers"/>
    
4.4. Указываем где будет вью

    <bean id="viewResolver"
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>                
    
5. Копируем папку resources из предыдущего проекта, меняем название файла на datasource-tx-jpa.xml, и в нем меняем

    `<context:property-placeholder location="/resources/jdbc.properties"/>`    
    
6. Создаем папку DAO копируем туда Course.java и создаем еще 3 файла.

6.1. Файл CourseServiceImpl.java навешиваю анотации.

6.1.1. Создаем переменную типа CourseRepository, создаем seter и с помощью @Autowired связываем с класом  CourseRepository.java

    private CourseRepository courseRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }    
    
6.1.2. Определяем методы для работы с БД.

7. Создаем клас CourseController     

7.1. Навешиваем на него анотации 

    @Controller
    @RequestMapping("/courses") //Связываем этот контролер с URL
    
Что бы спринг понимал что нужно смотреть на эту анотации, в настройках servlet-context.xml. Мы писали

    <mvc:annotation-driven/>
    
7.2. Для подключения данных используем автосвязывание над сетером

    private CourseService courseService;

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }
    
7.3. Добавляем методы которые будут являться реализацией экшенов контролера. 
Model -  туда мы в контроллере занесем данные, которые нам будут доступны в представлении. Он приходит от Spring MVC, а мы заполняем (может прийти пустым, а может и нет).
@RequestMapping(method = RequestMethod.GET) - Эта анотация делает это все контролером.

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model uiModel){
        List<Course> courses = courseService.findAll();
        uiModel.addAttribute("courses", courses);
        return "courses/list";
    }      
    
8. Создаем корневую конфигурацию контролеров root-context.xml (сделал копи паст)

9. Создаем папку views

9.1. Делаем файл list.jsp

9.2. Заполнил копи пастом.

3.35.00      
    
        
    
    