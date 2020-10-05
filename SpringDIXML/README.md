Учебный Spring project Dependence Injection (DI) на xml.
-------------- 

Commit 1.
---------
1. В pom.xml вставляем Dependencies.
2. Создаем окружение из классов Window, Wood... Plastic..
3. Создаем бин в applicationContext.xml
```    
    <bean id="windowBean"
          class="ru.specialist.spring.PlasticWindow">
    </bean>
```
4. В App.java создаем `context`
`ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");`
4.1. У контекста через метод getBean получаем компоненту/бин окно
`Window window = context.getBean("windowBean", Window.class);`
4.2 Передаем в House window 
4.3 Запускаем, проверяем.

В итоге мы добились что мы можем менять окна в доме, меняя настройки в applicationContext.xml. 
Но это было вручное внедрение окна.

Commit 2.
-----------
Усложним предыдущую программу. Будем создавать дом в applicationContext.xml и там внедрять окно.

1. В applicationContext.xml добавляем Home.
```
    <bean id="houseBean"
          class="ru.specialist.spring.House">
        <constructor-arg ref="windowBean"/>
        <constructor-arg value="3"/>
    </bean>
```
1.1 `<constructor-arg ref="windowBean"/>` ссылается на другой бин в этом файле. этот бин должен быть выше.
1.2 В App.java теперь сразу вызываем House 
`House house = context.getBean("houseBean", House.class);`


