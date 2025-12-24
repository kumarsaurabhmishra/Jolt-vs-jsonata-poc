package com.jsonata.service;

import com.api.jsonata4java.Expression;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class JsonataTransformationService implements TransformationService {

    @Override
    public JsonNode transform(JsonNode input, String jsonFile) throws Exception{
        InputStream is = getClass().getClassLoader().getResourceAsStream("jsonata/" + jsonFile);

        if (is == null) {
            throw new IllegalArgumentException("File not found: " + jsonFile);
        }

        String expressionStr = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        Expression expression = Expression.jsonata(expressionStr);

        return expression.evaluate(input);

    }

}
