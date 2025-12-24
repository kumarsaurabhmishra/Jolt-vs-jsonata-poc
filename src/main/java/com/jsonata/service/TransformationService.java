package com.jsonata.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface TransformationService {

    public JsonNode transform(JsonNode input, String specFile) throws Exception;

}
