package com.foling.community.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private String gmtCreate;
    private String gmtModified;
}
