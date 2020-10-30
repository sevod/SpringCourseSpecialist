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

Commit 2
--------------------

Реализуем операцию удаления. Отработаем получение дополнительных данных в контролер из запроса.

1. Добавим в CourseController.java метод delete. 
```
       @RequestMapping(value = "delete/{id}", method = RequestMethod.GET )
        public String delete(@PathVariable("id") int id, Model uiModel){
            courseService.delete(id);
            return "redirect:/courses";
        }
```    

через строку `value = "delete/{id}"` мы получим дзначение из запроса. 

А с помощью `@PathVariable("id")` мы передадим его в код.

Вот так выглядит запрос на удаление из браузера `http://localhost:8080/WebMVC/courses/delete/2`

А это редирект на страницу `courses` `return "redirect:/courses";`


2. Отработаем редактирование/добавление новой строки.

2.1. Для этого делаются два экшена. Первый отображает форму (метод get), а другой реальное удаление (post)

2.2. Делаем get. Все аналогично предыдущим методам.

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET )
    public String updateForm(@PathVariable("id") int id, Model uiModel){
        uiModel.addAttribute("course", courseService.findById(id));
        return "courses/edit";
    }   
    
2.2.1. Создаем edit.jsp страницу (копи паст)

2.3. Добавим такую же форму, но без id

    @RequestMapping(value = "update", method = RequestMethod.GET )
    public String updateForm(Model uiModel){
        return "courses/edit";
    }
    
2.4. Создаем метод POST для отправки данных на сервер. Названия сделаны так, что бы само подцепилось.

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST )
    public String update(Course course, BindingResult bindingResult, Model uiModel){
        if (bindingResult.hasErrors()) {
            //bindingResult.getAllErrors() если нужно найти ошибки.
            uiModel.addAttribute("course", course);
            return "courses/update";
        }
        courseService.save(course);
        return "redirect:/courses/";
    }   
    
3. Создадим метод newForm, для добавления новых курсов. И добавим кнопки на форму lest.jsp
```
    @RequestMapping(value = "update/0", method = RequestMethod.GET )
    public String newForm(Model uiModel){
        return "courses/edit";
    }             
```
    
   
Commit 3. Spring Security
-----------------------------

Будем делать разграничение доступа.

Аутентификация и авторизация. В спринге включается в фильтры. В файл web.xml. 

Будем создавать форму аутентификации, контроллер.
На формах к которым будем делать доступ, нужно будет поменять JSP разметку.
Акшен контроллерах, что бы пользователь на прямую не попал туда.

Будет аутентификейшен провайдер, который будет отвечать за хранение паролей. Бывают разные.

1. Подключаем модули через Maven (с самыми последними не смог запустить, какие то особенности не учтены)

1.1. spring security core основной модуль.

1.2. Spring Security Web мобуль который будет содрежать наш фильтр.

1.3. Spring Security Config что бы мы могли все конфигурировать в конфигурациооных файлах.

1.4. Spring Security Taglibs что бы мы могли на JSP использовать дополнительную библиотеку тегов для связи с спринг секюрити.

2. Правим web.xml

2.1. Добавляем фильтр `springSecurityFilterChain` 

    <filter>
        <filter-name>springSecurityFilterChain</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    </filter>   
    
2.2. Подключаем filter-mapping. В самом фильтре мы еще сможем сконфигурировать, что закрыто и открыто. Эта настройка просто "на что распространяется".

    <filter-mapping>
        <filter-name>springSecurityFilterChain</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>    

3. Создаем конфигурационный файл для Spring Security. `security-context.xml`. Потом его подключим.

3.1. Подключем Spring expression langwitch. Можно использовать в JSP так и в анотациях. По умолчанию доступ разрешаем всем.
Так же добавляем странцу логина, страницу куда редиректи при ошибке, страницу куда редиректи при правильном логине. 
И логаут страницу, куда попадет разлогинившийся пользователь.

    <s:http use-expressions="true">
        <s:intercept-url pattern="/*" access = "permitAll"/>
       <s:form-login login-page="/courses/"
            authentication-failure-url="/security/loginfail"
            default-target-url="/courses/"/>
        />
        <s:logout logout-success-url="/courses/" />
    </s:http>
    
3.2.  Аутентификейшен менеджер. Будет хранить логины, пароли, роли. Вместо `authentication-provider` возможно применять другие провайдеры.

        <s:authentication-manager>
            <s:authentication-provider>
                <s:user-service>
                    <s:user name="user" password="user" authorities="ROLE_USER" />
                </s:user-service>
            </s:authentication-provider>
        </s:authentication-manager>
        
`<s:user name="user" password="user" authorities="ROLE_USER" />`    Имя пользователя, пароль, роль.

3.3. Подключем созданный файл к корневой конфигурации вэб апликейшен контекст root-context.xml

    <import resource="../../META-INF/spring/security-context.xml" />
    
4. Применим роли на страницу list.jsp.

4.1. Добавим taglib библиотеку (библиотека тэгов)

    <%@ taglib prefix="s" uri="http://springframework.org/security/tags"%>
    
4.2. Если пользователь авторизован срабоатет условие `<s:authorize access="isAuthenticated()">` 
и можем получить имя пользователя `<s:authentication property="principal.username" />`
`<a href="../j_spring_security_logout">Выход</a>` это обработчик, который нам добавил фильтр и мы можем его использовать.

		<s:authorize access="isAuthenticated()">
		    Привет, <s:authentication property="principal.username" />!
			<br/>
			<a href="../j_spring_security_logout">Выход</a>
		</s:authorize>    
		
4.3. Если пользователь не авторизован, сработает другое условие `<s:authorize access="isAnonymous()">`
Внутри него html форма и атрибут `action="../j_spring_security_check"` который использует готовый обработчик.

Для логина используем импут с определенным именем `<input type="text" name="j_username">`

Для паспорта так же `<input type="password" name="j_password">`

	<s:authorize access="isAnonymous()">
    		<form id="login" name="loginForm" action="../j_spring_security_check"
    			method="POST">
    			<label>Логин: </label>&nbsp;
    			<input type="text" name="j_username">
    			<label>Пароль: </label>&nbsp;
    			<input type="password" name="j_password">
    			<input type="submit" name="submit" value="Войти">
    		</form>
    </s:authorize>		
    
То что мы используем форму для логина задано в файле `security-context.xml`    `<s:form-login login-page="/courses/" ...`

5. Защитим наши экшен контроллеры от прямого запроса не авторизованных пользователей.

5.1 В файле `servlet-context.xml` добавляем дополнительные нэймспейсы для spring security и

    <s:global-method-security pre-post-annotations="enabled"/>
    
5.2. В контроллере `CourseController.java` добавляем анотации. Этот метод будет доступен только тем, у кого есть данная роль.

    @PreAuthorize("hasRole('ROLE_USER')")   