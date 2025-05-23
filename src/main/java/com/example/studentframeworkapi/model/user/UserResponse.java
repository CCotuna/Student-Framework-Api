package com.example.studentframeworkapi.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
public class UserResponse {
    private String name;
    private String job;
    private int id;
    private String createdAt;
    private String updatedAt;
}
