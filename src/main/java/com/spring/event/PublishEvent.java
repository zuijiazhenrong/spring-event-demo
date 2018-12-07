package com.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;


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
