package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.DoubleStream;

@RestController
public class InfoController {
    @Value("${server.port}")

    private Integer port;

    @GetMapping("/port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/sum")
    public long getOptimizedSum() {
        return java.util.stream.IntStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }
    @GetMapping("/sum-math")
    public long getSumMath() {
        long n = 1_000_000L;
        return n * (n + 1) / 2;
    }


}
