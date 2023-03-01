package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("/helloWorld")
    public String testController() {
        return "Hello World!";
    }

    @GetMapping("/{id}")
    public String testControllerWithPathVariable(
            @PathVariable(required = false) int id)
    {
        return "Hello World! ID " + id;
    }

    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(
            @RequestParam(required = false) int id) {
        return "Hello World! ID " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(
            @RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello world! ID " + testRequestBodyDTO.getId()
                + " Message : " + testRequestBodyDTO.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I`m ResponseDTO");
        return ResponseDTO.<String>builder().data(list).build();
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I`m ResponseEntity. And you got 200!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        //http status를 200으로 설정.
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/testResponseEntityBadReq")
    public ResponseEntity<?> testControllerResponseEntityBadReq() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I`m ResponseEntity. And you got 404!");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        //http status를 404로 설정.
        return ResponseEntity.badRequest().body(response);
    }
}
