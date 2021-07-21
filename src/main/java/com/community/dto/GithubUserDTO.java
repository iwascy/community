package com.community.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GithubUserDTO {
    private String login;
    private String name;
    private Integer id;
    private String bio;

    @JSONField(name = "avatar_url")
    private String avatarUrl;
}
