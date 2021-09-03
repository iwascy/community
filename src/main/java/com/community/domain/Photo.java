package com.community.domain;

import lombok.Data;

@Data
public class Photo {
    private Integer id;
    private Integer questionId;
    private String photoUrl;
    private String key;
}
