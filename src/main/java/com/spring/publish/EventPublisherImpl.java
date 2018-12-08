package com.spring.publish;

import com.spring.event.PublishEvent;
import com.spring.publish.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    public void publisher(PublishEvent name) {
        applicationEventPublisher.publishEvent(name);
    }



}
