package com.jsonata.service;

import com.bazaarvoice.jolt.Chainr;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class JoltTransformationService implements TransformationService {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode transform(JsonNode input, String specFile) throws Exception{
        InputStream is = getClass().getClassLoader().getResourceAsStream("jolt/" + specFile);

        if (is == null) {
            throw new IllegalArgumentException("File not found: " + specFile);
        }

        String specStr = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // Parse the Jolt specification
        List<Object> joltSpec = mapper.readValue(specStr, List.class);

        // Create a Jolt transformer
        Chainr chainr = Chainr.fromSpec(joltSpec);

        // Transform the input JSON
        Object inputObject = mapper.convertValue(input, Object.class);
        Object transformedOutput = chainr.transform(inputObject);

        return mapper.valueToTree(transformedOutput);
    }

}
