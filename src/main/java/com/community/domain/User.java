package com.community.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
 *用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer accountId;
    private String token;
    private String avatar;
    private long createTime;
    private long updateTime;
}