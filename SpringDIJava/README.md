Commit 1
----------

Тут мы проходим как конфигурировать Spring с помощью Java классов. xml не будет.

**Основная идея.** Будет класс с анотацией `@Configuration`. 
Этот класс будет корневой для спринга и в нем будут фабричные методы, которые будут создвать бины. 
Будем помечать их анотацией `@Bean`
 
1.1. Создаем дополнительный пакет builder и копируем туда файлы из предыдущего пакета.

1.2. Создаем класс BuilderConfig. Помечаем его анотацией `@Configuration`. Внутри него будем создавать бины.

1.2.1. Создаем окно
   
    @Bean
    public Window woodFrameWindow(){
        return new WoodFrameWindow();
    } 

1.2.2. Создаем дом и передаем в него ранее созданное окно. 
Обращаем внимание, что woodFrameWindow() это не просто вызов метода, а бин и тут работает синглтон.

    @Bean
    public House house(){
        House house = new House(woodFrameWindow());
        house.setHeight(2);
        return house;
    }
    
1.2.3. Добавляем кирпичи. Сдесь бину задано имя  `@Bean("brick")`. Так же задан скоуп `@Scope("prototype")`

    @Bean("brick")
    @Scope("prototype")
    public Brick bricks(){
        return new Brick();
    }
    
1.2.4. Добавляем дерево.

    @Bean("wood")
    @Scope("prototype")
    public Wood wood(){
        return new Wood();
    }    

1.2.5. В House для подключения Material оставляем `@Autowired` поскольку у нас несколько материалов, добавляем @Qualifier("brick")

    @Autowired
    @Qualifier("brick")
    private Material wall;

2.0 В App.java создаем context

`AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BuilderConfig.class);`   

2.1. Получаем бин

`House house = context.getBean(House.class);`

2.2. дописываем код в App 