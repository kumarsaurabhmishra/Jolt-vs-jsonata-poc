package com.jsonata.routes;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonata.service.JoltTransformationService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JoltRoute extends RouteBuilder {

    private final JoltTransformationService joltTransformationService;

    public JoltRoute(JoltTransformationService joltTransformationService) {
        this.joltTransformationService = joltTransformationService;
    }

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .handled(true)
                .setHeader("Content-Type", constant("application/json"))
                .setBody(simple("{\"error\": \"${exception.message}\"}"));

        from("direct:joltTransform")
                .routeId("joltTransformRoute")
                .log("Received body: ${body}")
                .process(exchange -> {
                    JsonNode input = exchange.getIn().getBody(JsonNode.class);

                    String specFile = exchange.getIn().getHeader("X-MAPPING-NAME", "source-to-target.json", String.class);

                    JsonNode output = joltTransformationService.transform(input, specFile);
                    exchange.getMessage().setBody(output);
                });
    }
}
