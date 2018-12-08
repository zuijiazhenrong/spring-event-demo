###      Spring的事件驱动模型  
Spring事件驱动模型的三个概念：事件，事件监听者、事件发布者。

事件：如下图所示,Spring中定义了事件的抽象类ApplicationEvent,该类继承自JDK的 EventObjectl类,ApplicationEvent中的timestamp字段用来存储事件发生的时间戳,父类中的source表示事件发生的对象。当我们要定义时间的时候只需要继承ApplicationEvent,如下面自定义的PublishEvent事件    

![event](3B53DEBF0BF441DF9824C224A6E465FD)    
        
            //自定义事件  
        public class PublishEvent extends ApplicationEvent {  
        private String name;
        public PublishEvent(Object source) {
            super(source);
            this.name = (String) source;
        }
        
        @Override
        public String toString() {
            return "PublishEvent{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }  
    
事件监听者:  事件监听者负责监听和处理事件,如下图所示,Spring定义了事件的监听接口ApplicationListener,该接口继承自JDK的EventListener接口,并且ApplicationListener规定了onApplicatioEvent(E)函数的参数必须为ApplicationEvent的子类,所以每个时间监听者都只是针对某个ApplicationEvent的子类的监听。  
![listener](B3C11AB22FD04071B8A82CBBFFA40422) 

     //自定义时间监听者
     public class EventListener implements   ApplicationListener<PublishEvent> {

         public void onApplicationEvent(PublishEvent publishEvent) {

             System.out.println("EventListener>>>>>>>>>>>>"+publishEvent.toString());

        }
    }
    

 事件发布者：事件发布者的作用显而易见,就是发布事件,将事件交给对应的发布者处理,那么Spring是如何做到的呢? 我们来看下图。Spring定义了ApplicationEventPublisher接口和ApplicationEventMulticaster接口。ApplicationEventPublisher用来发布事件,ApplicationEventMulticaster是用来维护事件监听者和将事件多播给事件的监听者,其实事件的真正发布就是在ApplicationEventPublisher的multicaster方法中实现的。

![publish](878D2BE504684A1EBB4E6C1233E7759D)


实现原理：
ApplicationEventPublisher的publishEvent的实现是在AbstractApplicationContext中事件的，代码如下：

     protected void publishEvent(Object event, @Nullable ResolvableType eventType) {
        Assert.notNull(event, "Event must not be null");
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Publishing event in " + this.getDisplayName() + ": " + event);
        }

        Object applicationEvent;
        if (event instanceof ApplicationEvent) {
            applicationEvent = (ApplicationEvent)event;
        } else {
            applicationEvent = new PayloadApplicationEvent(this, event);
            if (eventType == null) {
                eventType = ((PayloadApplicationEvent)applicationEvent).getResolvableType();
            }
        }

        if (this.earlyApplicationEvents != null) {
            this.earlyApplicationEvents.add(applicationEvent);
        } else {
            //调用ApplicationEventMulticaster的实现类SimpleApplicationEventMulticaster的multicastEvent方法多播事件
            this.getApplicationEventMulticaster().multicastEvent((ApplicationEvent)applicationEvent, eventType);
        }

        if (this.parent != null) {
            if (this.parent instanceof AbstractApplicationContext) {
                ((AbstractApplicationContext)this.parent).publishEvent(event, eventType);
            } else {
                this.parent.publishEvent(event);
            }
        }

    }
    
SimpleApplicationEventMulticaster中的相关代码

    public void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType) {
        ResolvableType type = eventType != null ? eventType : this.resolveDefaultEventType(event);
        //得到event的集合
        Iterator var4 = this.getApplicationListeners(event, type).iterator();
       //遍历集合执行调用
        while(var4.hasNext()) {
            ApplicationListener<?> listener = (ApplicationListener)var4.next();
            Executor executor = this.getTaskExecutor();
            if (executor != null) {
                executor.execute(() -> {
                    this.invokeListener(listener, event);
                });
            } else {
                this.invokeListener(listener, event);
            }
        }

    }

    protected void invokeListener(ApplicationListener<?> listener, ApplicationEvent event) {
        ErrorHandler errorHandler = this.getErrorHandler();
        if (errorHandler != null) {
            try {
                this.doInvokeListener(listener, event);
            } catch (Throwable var5) {
                errorHandler.handleError(var5);
            }
        } else {
            this.doInvokeListener(listener, event);
        }

    }


    private void doInvokeListener(ApplicationListener listener, ApplicationEvent event) {
        try {
            //最终调用listener的onApplicationEvent方法
            listener.onApplicationEvent(event);
        } catch (ClassCastException var6) {
            String msg = var6.getMessage();
            if (msg != null && !this.matchesClassCastMessage(msg, event.getClass().getName())) {
                throw var6;
            }

            Log logger = LogFactory.getLog(this.getClass());
            if (logger.isDebugEnabled()) {
                logger.debug("Non-matching event type for listener: " + listener, var6);
            }
        }

    }
    
编写测试用例,在用例中我们注入ApplicationEventPublisher

    @SpringBootTest(classes = Application.class)
    @RunWith(SpringRunner.class)
    public class EventTest {

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;
    
        @Test
        public void testEvent(){
            PublishEvent publishEvent = new PublishEvent("abc");
            applicationEventPublisher.publishEvent(publishEvent);
        }
}

源码地址:https://github.com/zuijiazhenrong/spring-event-demo