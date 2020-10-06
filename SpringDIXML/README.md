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

1. В applicationContext.xml добавляем Home. Используем `constructor-arg`
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

Commit 3.
-----------
Продолжаем. У House из конструтора убираем возможность задавать height, только через сеттер

1. В applicationContext.xml вставляем `property`. Он вызывает setHeight и передает туда значение (пишем просто height)
```
<property name="height" value="2" />
```
2. Так же вместо value можно использовать ref как в Commit 2

Commit 4.
--------
Добавляем дому стены.

1. Создаем интерфейс Material

1.1. Класс кирпич Brick

1.3. Класс дерево Wood

1.4. Дому добавляем стену wall

1.5. Добаляем для дома метод для стрительства стен buildWall.

1.6. В applicationContext.xml добавляем материалы для стен
```
    <bean id="brickBean"
          class="ru.specialist.spring.Brick">
    </bean>
    <bean id="woodBean"
          class="ru.specialist.spring.Wood">
    </bean>
```
1.7 И задаем через `ref` материал стен
```
<property name="wall" ref="brickBean"/>
```

Commit 5.
--------
Создадим компоненты. Будут срабатывать методы при инициализации компоненты и уничтожении.
Так же рассмотрим альтернативный способ создания компоненты не через конструктор а через фабричный метод. 
Конструктор будет приватным.

1.1. Добавляем MainWindow

1.2. Делаем MainWindow синглтоном "по алгоритму" "Holder"

1.3. В applicationContext.xml добавляем наш новый класс. Поскольку конструктор приватный используем `factory-method`
```
<bean id="mainWindowBean"
    class="ru.specialist.spring.MainWindow"
    factory-method="getInstance">
</bean>
```
1.4. Создаем это окно в App.java и вызываем его оттуда. Поскольку это синглтон, просто указываем класс. Можно и через id.
```
MainWindow mainWindow = context.getBean(MainWindow.class);
```

2. `init-method` Будет отрабатывать до всего нашего кода. Относится к методам жизненого цикла компоненты.
2.1. Добавим метод openConnection в наш класс.
2.2. В описание бина в xml добавляем init-method="openConnection".
В итого мы сделали:
`Если какие-либо из компонентов реализуют интерфейс InitializingBean, Spring вызывает их методы afterPropertiesSet(). Аналогично, если компонент был объявлен с атрибутом  init-method, вызывается указанный метод инициализации.`

Commit 6
-----------------
Будем использовать DisposableBean() метод destroy() (это из одного этапа жизни компоненты) который будет вызываться при удалении компоненты

1.1. В MainWindow добавляем метод closeConnection()

1.2 В applicationContext.xml добавляем в компоененту 

`destroy-method="closeConnection"`

1.3. проверяем. Метод closeConnection вызывается последним.

Commit 7
-----------------

Альтернатива предыдущим, это реалзивать интерфейсы в MainWindow.java. `InitializingBean` и `DisposableBean`

1.1. Имплементируем `InitializingBean` и опеределяем метод `afterPropertiesSet()`
1.2. Вызываем в нем `openConnection();`

2.1. Имплементируем `DisposableBean` и определяем метод `destroy()`
2.2. Вызываем в нем `closeConnection();`

Проверяем. Теперь уже все вызывается 2 раза.

Методы жизненого цикла по уничтожению работают только для "Синглтона" и не работают для "Прототипа"