package com.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private String name;
    private Integer id;
    private String bio;
}
