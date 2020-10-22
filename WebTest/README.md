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



     