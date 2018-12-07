package com.spring.listner.bean;

import com.spring.event.PublishEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class EventListener implements ApplicationListener<PublishEvent> {


    public void onApplicationEvent(PublishEvent publishEvent) {

        System.out.println("EventListener>>>>>>>>>>>>"+publishEvent.toString());

    }
}
