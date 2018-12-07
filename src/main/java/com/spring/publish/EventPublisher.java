package com.spring.publish;

import com.spring.event.PublishEvent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

/**
 * @program: spring-event-demo
 * @description:
 * @author: pangxuegang@ruijie.com.cn
 * @create: 2018-10-19 17:17
 **/
@Component
public interface EventPublisher {

    public void  publisher(PublishEvent name);


}
