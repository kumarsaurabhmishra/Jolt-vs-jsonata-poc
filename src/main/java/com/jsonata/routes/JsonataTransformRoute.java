package com.jsonata.routes;

import com.fasterxml.jackson.databind.JsonNode;
import com.jsonata.service.JsonataTransformationService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class JsonataTransformRoute extends RouteBuilder {

    private final JsonataTransformationService jsonataTransformationService;

    public JsonataTransformRoute(JsonataTransformationService jsonataTransformationService) {
        this.jsonataTransformationService = jsonataTransformationService;
    }




    @Override
    public void configure() {

        onException(Exception.class)
                .handled(true)
                .setHeader("Content-Type", constant("application/json"))
                .setBody(simple("{\"error\": \"${exception.message}\"}"));

        from("direct:jsonataTransform")
                .routeId("jsonataTransformRoute")
                .log("Received body: ${body}")
                .process(exchange -> {
                    JsonNode input = exchange.getIn().getBody(JsonNode.class);

                    String mapping = exchange.getIn().getHeader("X-MAPPING-NAME", "source-to-target.jsonata", String.class);

                    JsonNode output = jsonataTransformationService.transform(input, mapping);
                    exchange.getMessage().setBody(output);
                });
    }
}
