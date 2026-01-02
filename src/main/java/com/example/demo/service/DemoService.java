package com.example.demo.service;

import com.example.demo.dto.DemoResponse;

public interface DemoService {
    DemoResponse getDemo();
    DemoResponse getDemoByName(String name);
}
