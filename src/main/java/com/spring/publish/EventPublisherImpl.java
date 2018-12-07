package com.spring.publish;

import com.spring.event.PublishEvent;
import com.spring.publish.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @program: spring-event-demo
 * @description:
 * @author: pangxuegang@ruijie.com.cn
 * @create: 2018-10-19 17:21
 **/
@Component
public class EventPublisherImpl implements EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public void publisher(PublishEvent name) {
        applicationEventPublisher.publishEvent(name);
    }



}
