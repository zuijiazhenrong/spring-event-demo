package com.spring.test;

import com.spring.Application;
import com.spring.event.PublishEvent;
import com.spring.publish.EventPublisher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit4.SpringRunner;


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
