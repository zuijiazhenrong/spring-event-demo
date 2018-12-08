package com.spring.publish;

import com.spring.event.PublishEvent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;


@Component
public interface EventPublisher {

    public void  publisher(PublishEvent name);


}
