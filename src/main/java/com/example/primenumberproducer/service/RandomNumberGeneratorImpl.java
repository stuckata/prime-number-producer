package com.example.primenumberproducer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RandomNumberGeneratorImpl implements RandomNumberGenerator {

    private static final int MIN_VALUE = 0;

    @Override
    public int generate() {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int randomNumber = randomDataGenerator.nextInt(MIN_VALUE, Integer.MAX_VALUE);
        log.info("Random number generated: {}", randomNumber);
        return randomNumber;
    }
}
