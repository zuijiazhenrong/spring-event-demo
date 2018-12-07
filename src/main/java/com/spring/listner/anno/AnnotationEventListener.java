package com.spring.listner.anno;

import com.spring.event.PublishEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class AnnotationEventListener {

    @EventListener
    public void listen(PublishEvent applicationEvent){
        System.out.println("AnnotationEventListener >>>>>>>>>>>>"+applicationEvent.toString());
    }
}
