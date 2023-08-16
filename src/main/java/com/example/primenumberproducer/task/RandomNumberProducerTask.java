package com.example.primenumberproducer.task;

import com.example.primenumberproducer.service.RandomNumberProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomNumberProducerTask {

    private final RandomNumberProducer randomNumberProducer;

    @EventListener(ApplicationReadyEvent.class)
    void executeTask() {
        this.randomNumberProducer.produce();
    }
}
