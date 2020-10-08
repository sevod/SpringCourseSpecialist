Commit 1
----------
Тут мы будем проходить Анотации Spring. 

Недостаток анотаций, код становится зависимым от спринга.

**Отсутствует 35 мин видео. Востанавливаю как могу (по коду и сатятьям по теме.). Почти получилось.**


1.1 В файле applicationContext.xml добавляем и комментируем все ранее созданные бины, сейчас мы их будем переопределять.

```
<context:component-scan base-package="ru.specialist.springAnnotation">        
</context:component-scan>
```

1.2 Далее в классе House добавляем анотацию `@Component`. У нас создается бин House. Можно использовать имя `@Component("name")`

1.3 В классе House задаем значение поля через  `@Value("2")`

```
    @Value("2")
    private int height;
```

1.4. Добавляем в класс Wood анотацию @Component без имени и в классе House подключаем ее через Autowired
```
    @Autowired
    private Material wall;
```
1.5. Все работает, но есть проблема, если в классе Brick тоже использовать @Component, то выходит ошибка. У нас не однозначность. Есть несколько варинатов решения. 

1.5.1. Меняем анотацию в одном из классов `@Component("wood")` И так же к Autowired добавляем указание на нужный нам бин.
```    
    @Autowired
    @Qualifier("wood")
```

1.5.2 Есть еще вариант, но я пропущу 
```    
    @Inject // javax.inject
    @Named("logsBean") // javax.inject
```

1.6. Подключаем окно используя @Component и @Autowired. Окно подключаем одно, поэтому проблем нет.

1.6.1 Удаляем @Component у окна и раскоментируем бин windowBean в xml, все по прежнему работает. 

_xml и Анотации могут рабоать пралельно_

**Далее уже по лекции.**

2.1. В `context:component-scan` мы можем использовать include и exclude  фильтры. Например:
```
    <context:component-scan base-package="ru.specialist.springAnnotation">
        <context:include-filter type="assignable" expression="ru.specialist.springAnnotation.Window"/>
        <context:exclude-filter type="assignable" expression="ru.specialist.springAnnotation.Brick"/>
    </context:component-scan>
```

````
Тип фильтра(type) 	Описание

annotation 	Отыскивает классы, отмеченные указанной аннотацией
			на уровне типа. Аннотация определяется атрибутом expression

assignable 	Отыскивает классы, экземпляры которого могут присваиваться
			свойствам указанного типа. Или интерфейс или наследники или сам класс. Тип свойств определяется
			атрибутом expression

aspectj 	Отыскивает классы, тип которых соответствует выражению
			типа AspectJ, указанному в атрибуте expression

custom 		Использует пользовательскую реализацию org.springframework.
    		core.type.TypeFilter, указанную в атрибуте expression

regex 		Отыскивает классы, имена которых соответствуют регулярному
			выражению, указанному в атрибуте expression
	
````

2.2. Если мы хотим бины описывать в xml, а связывать анотациями используем в xml `<context:annotation-config />`, а `component-scan` из 1.1 не используем
	 
2.3. Основные анатации spring'а

@Component - определяем бин

@Autowired - анотация для связывания компонентов

@Qualifier - уточняем с чем именно мы связываем

Собственная анотоция. @WoodQualifier. (я не использовал, лежит в соответствующем файле.)

@Value - анотация позволяющая писать простые данные или выражения SpEL

2.4. Анотации могут навешиваться на сеторы, конструкторы, приватные поля... может еще на что...

2.5. Так же можно использовать антоции и не spring'а (анотации стандарта... незнаю какого). Например javax.inject...
	 
	 
Виды анотаций.

@Component - компонент спринга

@Controller - Контролер MVC

@Repository - репозиторий данных

@Service - сервис


