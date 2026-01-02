package com.example.demo.controller;

import com.example.demo.dto.DemoResponse;
import com.example.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    private final DemoService demoService;

    @GetMapping
    public ResponseEntity<DemoResponse> getDemo() {
        return ResponseEntity.ok(demoService.getDemo());
    }

    @GetMapping("/{name}")
    public ResponseEntity<DemoResponse> getDemoByName(@PathVariable String name) {
        return ResponseEntity.ok(demoService.getDemoByName(name));
    }
}
