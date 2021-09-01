package com.community.domain;

import lombok.Data;

@Data
public class Photo {
    private int id;
    private int questionId;
    private String photoUrl;
    private String key;
}
