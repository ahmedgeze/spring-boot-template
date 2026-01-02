package com.example.demo.service;

import com.example.demo.dto.DemoResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public DemoResponse getDemo() {
        return DemoResponse.builder()
                .message("Hello from Demo Service!")
                .timestamp(getCurrentTimestamp())
                .status("SUCCESS")
                .build();
    }

    @Override
    public DemoResponse getDemoByName(String name) {
        return DemoResponse.builder()
                .message("Hello, " + name + "!")
                .timestamp(getCurrentTimestamp())
                .status("SUCCESS")
                .build();
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
