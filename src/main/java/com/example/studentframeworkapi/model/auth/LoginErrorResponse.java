package com.example.studentframeworkapi.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
public class LoginErrorResponse {
    private String error;
}
