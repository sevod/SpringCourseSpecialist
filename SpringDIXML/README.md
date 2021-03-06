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

Commit 8
-----------------
Работаем со scope (зона видимости) и вложенная компонента

Виды scope:

-singleton (по умолчанию)

-prototype  (каждый раз новый обьект)

-session (для http для сесии)

-request (для http для одного запроса)

-global-session (глобальный http контекст)

1.1 зададим для кирпича и дерева в applicationContext.xml scope prototype

`scope="prototype"`

2. Вложенная компонента. Пример
```
<property name="wall" >
    <bean class="ru.specialist.spring.Brick"/>
</property>
```
Эта вложенная компонента не видна в других компонентах

Commit 9
------------------

Упрощаем как задавать данные (property) через пространство имен (nameSpace) 

1. возвращаем назад вложенную компоненту.
2. в заголовок добавляем `xmlns:p="http://www.springframework.org/schema/p"`
3. теперь мы можем писать так `p:height="4"`

Commit 10
-----------------

Внедрение колекций

1.1. Добавляем двери. Door, MetalDoor, WoodDoor
1.2. Добавляем двери в Home `private Collection<Door> doors;` и метод installDoors(), который отображает установленные двери.
1.3. В applicationContext.xml создаем два бина для дверей
```
   <bean id="outDoor"
          class="ru.specialist.spring.MetalDoor">
    </bean>

    <bean id="innerDoor"
          class="ru.specialist.spring.WoodDoor"
          scope="prototype">
    </bean> 
```
1.4 Добавляем в houseBean коллекцию дверей
```
        <property name="doors">
            <list>
                <ref bean="outDoor"/>
                <ref bean="innerDoor"/>
            </list>
        </property>
```
1.5 В App.java добавляем уставновку дверей и проверяем
`house.installDoors();`

В контексте можно использовать аналоги коллекций

`<list>` список значений допускающий повторения

`<set>` множество, не допускающий повторения

`<map>` ассоативные коллекции

`<props>` аналог map но для строк, можно делать ссылку

Commit 11.
------------------
Усложним предыдущий пример. Сделаем что бы у дврей были ключи. Задавать это будем парами ключ - дверь, с помощью map

1.1. Меняем в House двери на map `private Map<String, Door> doors;`
1.2. Меняем метод
```
    public void installDoors(){
        for (Map.Entry<String, Door> e: doors.entrySet()) {
            System.out.printf("Ключ %s. ", e.getKey());
            e.getValue().install();
        }
    }
```
1.3. Меняем в applicationContext.xml нашу коллекцию дверей на map
```
<map>
    <entry key="A" value-ref="outDoor"/>
    <entry key="B" value-ref="innerDoor"/>
</map>
```

Commit 12. SpEL спринг экспрешен ленгвич
----------
SpEL работа с коллекциями. Будет коллекция городов с информацией по населению.

0.1 Эквиваленты операторов сравнения: < lt / > gt / <= le / >= lt / == eq / and / or / ! not
0.2 Тернарный оператор (условия) ? (выполняется) : (невыполняется)
0.3 Проверка на регулярное выражение  `"#{windowBean.str matches '(тут регулярное выражение)'}"` 

1.1. Добавляем класс City
1.2. В applicationContext.xml подключим префикс util и schemaLocation для него 
1.3. опередлим коллекцию в applicationContext.xml
```
<util:list id="cities">
        <bean class="ru.specialist.spring.City" p:name="Chicago" p:state="IL" p:population="2853114"/>
        <bean class="ru.specialist.spring.City" p:name="Atlanta" p:state="GA" p:population="323"/>
        <bean class="ru.specialist.spring.City" p:name="Dallas" p:state="TX" p:population="45234324"/>
        <bean class="ru.specialist.spring.City" p:name="Houston" p:state="TX" p:population="53453d"/>
        <bean class="ru.specialist.spring.City" p:name="Odessa" p:state="TX" p:population="623333"/>
</util:list>
```
1.4.1. Если нам нужно обратиться к какому то конктретному элементу коллекции внутри бина можно сделать так

`<property name="chosenCity" value="#{cities[2]}">`

Это был пример, код не работает.

1.4.2. Если нужно получить рендомный город

`<property name="chosenCity" value="#{cities[T(java.lang.Math).random()*cities.size()]}"/>`

1.4.3. Это если бы было map коллекция

`<property name="chosenCity" value="#{cities['Dallas']}"/>`

1.4.4. С помощью такой конструкции и файла jdbc.properties. Мы получаем бин в котором будет коллекция из файла.

`<util:properties id="jdbcSettings" location="classpath:jdbc.properties"/>`

1.4.5. Две встроенных коллекции `systemEnvironment` / `systemProperties`

 `<entry key="#{systemEnvironment['JAVA_HOME']}" value-ref="innerDoor"/>`
 
  `<entry key="#{systemProperties['application.name']}" value-ref="innerDoor"/>`
  
1.4.6. С помощью выражения. Мы получим не один город, а коллекцию городов где `population > 100000`.

`<property name="bigCities" value="#{cities.?[population gt 100000]}"/>`  

1.4.7. С помощью выражения. Мы получим один город. '^' означает первый, '$' означает последний
```
 <property name="firstBigCity" value="#{cities.^[population gt 100000]}"/>
 <property name="lastBigCity" value="#{cities.$[population gt 100000]}"/>
```

1.4.8. На выходе будет строковая коллеция из поля `name`

`<property name="cityNames" value="#{cities.![name]}"/>`

1.4.9. На выходе будет строковая коллеция из полей `name, state`

`<property name="cityNames" value="#{cities.![name + ', ' + state]}"/>`

1.4.9. Коллекция городов удволетворяющих условию

`<property name="cityNames" value="#{cities.?[population gt 100000].![name + ', ' + state]}"/>`

  
2. Продолжаем работу со SpEL. Сделаем так, что бы ключ сам подставлялся в зависимости от двери.

2.1. Создаем класс KeySelector

2.2. Создаем bean `<bean id="keySelector" class="ru.specialist.spring.KeySelector"/>`

2.3. Вставляем этот бин/класс для получения ключа для двери `<entry key="#{keySelector.getKey(outDoor)}" value-ref="outDoor"/>`

2.4. Варинат с upperCase с использованием '?' что бы обойти null если будет

`<entry key="#{keySelector.getKey(outDoor)?.toUpperCase()}" value-ref="outDoor"/>`

Commit 13. Наследование бинов
----------

1.1. Наследование просиходит так

`<bean id="houseBean2" class="ru.specialist.spring.House" parent="houseBean"/>`

1.2. Бин может быть абстрактным. Используется свойство `abstract="true"`
1.3. Можно сбросить настройки в null. Или использовать другие значения property (переопределение property)

Commit 14. 
-----------

0.1. автоматическое связывание бинов когда сопадает тип или наименование.

0.2. автоматическое определение бина.
 
0.3. определение компоенент через анотации

1. автоматическое связывание бинов. `autowire` имеет 4 значения.

1.1. autowire = "byName"

1.1.1. В applicationContext.xml создаем компаненту. Её id будет совпадать со свойством..... 

`<bean id="wall" class="ru.specialist.spring.Brick" scope="prototype" />`

1.1.2. в бине House укажем autowire="byName" и можем убрать строку <property name="wall" ref="brickBean"/>. Эти данные теперь подтянутся автоматически по имени бина.

1.2. autowire = "byType". 

Должна быть одна компонента с данным типом. Но можно использовать свойство `primary="true"`. Но по умолчанию они все true.

Можно всем другим выставить `false` или  `autowire-candidate="false"`

1.3. autowire="constructor" 

нужен для подбора пораметров конструктору, работает по анологии byType для параметров конструктора.

1.3.1 Добавляем это и убираем <constructor-arg ref="windowBean"/>. Не заработало! Добавил дефолтный конструктор.

1.3.2. Если конструкторов несколько будет выбираться тот который подходит и требует минимальное количество связей

1.4. autowire="default" по сути ничего не дает, поскольку и так default. Но может быть это совойство определено у вышестоящего элемента.

Можно свойство `default-autowire=""` определить у корневого элемента <beans>

Так же в корень можно прописать
```
default-init-method=""
default-destroy-method=""
```



  
 
 