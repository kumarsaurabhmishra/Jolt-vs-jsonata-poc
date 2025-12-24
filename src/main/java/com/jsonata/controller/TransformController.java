package com.jsonata.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransformController {

    private final ProducerTemplate producerTemplate;

    public TransformController(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    @PostMapping("/transform")
    public Object transform(@RequestBody Object body, @RequestHeader(required = false)Map<String,Object> headers) {
        String routeName = "direct:jsonataTransform";
        if(headers!= null) {
            String jsonataOrJolt = (String) headers.get("x-transformer");
            if("JOLT".equalsIgnoreCase(jsonataOrJolt)) {
                routeName = "direct:joltTransform";
            }
        }
        return producerTemplate.requestBodyAndHeaders(routeName, body, headers);
    }

}
