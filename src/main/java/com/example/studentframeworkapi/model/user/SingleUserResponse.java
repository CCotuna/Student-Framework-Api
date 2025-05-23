package com.example.studentframeworkapi.model.user;

import com.example.studentframeworkapi.model.resource.SupportData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
public class SingleUserResponse {
    private UserData data;
    private SupportData support;
}
