
了解本项目核心代码需要先参考原作者的博文 [徒手撸框架--实现IoC](https://diaozxin007.github.io/2018/01/08/spring-ioc/) 。

### 循环依赖

其实很好理解，A 类依赖 B，B 又依赖 A。

说具体点就是 ，我们要 getBean("a")， A 在实例化时需要为类型为 B 的成员变量赋值，因此去 getBean("b")，而 getBean("b") 的时候又需要为其类型为A 的成员变量赋值，此时又会回过头去实例化 A ，导致无限循环。

用代码展示就是 

```java
public class A {
    @AutoWired
    private B b;
}
public class B {
    @AutoWired
    private A a;
}
```

### 代码改造

最主要的代码改造在于 BeanFactoryImpl 内， 添加了成员变量

```java
private final Map<String, Object> earlySingletonObjects = new HashMap<String, Object>(16);
```

用于缓存正在创建中的，提前暴露出来的单例 bean。

在获取 bean 时，会在创建之前先从此 Map 中尝试获取，而这就是解决循环依赖的关键。

以上面的例子来说，就是一开始 getBean("a") 时，将未完成的 a 放入缓存，getBean("b") 时，需要去获取 a ,会从缓存中获取，而不是再去实例化 a。

![](http://ots7yt7am.bkt.clouddn.com/blog/CyclicDependence.png)

```java
@Override
public Object getBean(String name) throws Exception {
    //查找对象是否已经实例化过
    Object bean = beanMap.get(name);
    if (bean != null) {
        return bean;
    }
    Object earlyBean = earlySingletonObjects.get(name);
    if (earlyBean != null) {
        System.out.println("循环依赖，提前返回尚未加载完成的bean:" + name);
        return earlyBean;
    }
    //如果没有实例化，那就需要调用createBean来创建对象
    BeanDefinition beanDefinition = beanDefineMap.get(name);
    bean = createBean(beanDefinition);
    if (bean != null) {
        earlySingletonObjects.put(name, bean);
        //对象创建成功以后，注入对象需要的参数
        populatebean(bean, beanDefinition);
        //再吧对象存入Map中方便下次使用。
        beanMap.put(name, bean);
        //从早期单例Map中移除
        earlySingletonObjects.remove(name);
    }
    //结束返回
    return bean;
}
```

### Q & A

Q: 构造器循环依赖为什么无法解决？

A: 从上面代码可以看出，需要在 createBean 之后，才能将其放入缓存，而构造过程是在 createBean 之内的，此时尚未构造好一个基本的 bean ,拿什么放入缓存呢？
