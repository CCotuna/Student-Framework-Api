package com.example.studentframeworkapi.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter @Getter @ToString
public class RegisterResponse {
    private Integer id;
    private String token;
}
