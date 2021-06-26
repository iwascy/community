package com.community.dto;


import lombok.Data; 

/*
 * 开发者
 *
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String redirect_uri;
    private String state;
    private String code;

}
