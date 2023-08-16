package com.example.primenumberproducer.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RandomNumberProducerImpl implements RandomNumberProducer {

    private static final String NUMBERS_DELIMITER = ",";

    @Value("${randomNumbers.maxGeneratedPerSecond}")
    private int maxGeneratedPerSecond;

    @Value("${randomNumbers.streamSize}")
    private int streamSize;

    private final RandomNumberGenerator randomNumberGenerator;
    private final CsvGenerator csvGenerator;
    private final KafkaSender kafkaSender;

    @Override
    public void produce() {
        RateLimiter rateLimiter = RateLimiter.create(this.maxGeneratedPerSecond);
        List<Integer> randomNumbers = new ArrayList<>();

        while (randomNumbers.size() <= this.streamSize) {
            rateLimiter.acquire();
            randomNumbers.add(this.randomNumberGenerator.generate());
        }
        log.info("Generation of random numbers stream complete.");
        String message = randomNumbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(NUMBERS_DELIMITER));

        this.csvGenerator.generate(message);
        this.kafkaSender.send(message);
    }
}
