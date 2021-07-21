package com.community.service;

import com.community.domain.Collect;
import com.community.mapper.CollectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CollectService {
    @Autowired
    private CollectMapper collectMapper;

    List<Collect> findCollectByAccountId(int accountId){
        return collectMapper.findCollectByAccountId(accountId);
    }
}
