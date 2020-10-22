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

1. Создаем пакет и клас для сервлета HelloServlet.java. Наследуемся от класса HttpServlet.

2. В майвен добавляем javaee-api

3. В этом классе навешиваем анотации для сервлета

4. Определяем метод doGet. В нем мы будем обрабатывать гет запросы. Из `HttpServletRequest req` получаем параметры запроса.
В `HttpServletResponse resp` помещаем ответ.
```
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          resp.setCharacterEncoding("UTF-8"); //это http заголовки
          resp.setContentType("text/html"); //это http заголовки
    
          PrintWriter out = resp.getWriter();
          out.println("<!doctype html>");
          out.println("<html>");
          out.printf("Server time: %tR ", LocalTime.now());
          out.println("</html>");   
    }
```

5. Страница будет доступна по адресу http://localhost:8080/WebTest/Hello


Commit 2
----------

**JSP**

1. За основу беру уже существующую index.jsp

<%    %> Серверные теги, внутри них код на jave

<%@    %> Директивы.

Community Edition не поддерживает jsp. Вбиваем все вручную.

<%= s%> сокращенный вовод. Результат будет выведен в это место.

Tag Library для упрощения jsp можно подключить. Можно написать свою TL можно исопользовать стандартную. Например

JSTL джава стендарт таг лайбрири.

2. Подключим JSTL. Нам нужна реализация для том кэта. Можно скачать из самого томкэта https://tomcat.apache.org/download-taglibs.cgi. Можно из мэйвана.

2.1. Добавляем в Maven `taglibs-standard-impl`, `taglibs-standard-spec`, `taglibs-standard-jstlel`, `taglibs-standard-compat` 

2.2. Подключаем теги к jsp странице

    <!-- Основные теги создания циклов, определения условий, вывода информации на страницу и т.д.  -->
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
2.3. Полностью не заработало (Заработало!). Пробемы с ${...} 

     <%@ page isELIgnored="false" %>







     