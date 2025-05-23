package com.example.studentframeworkapi.model.resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
public class SingleResourceResponse {
    private ResourceData data;
    private SupportData support;
}
