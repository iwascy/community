package com.community.provider;

import com.alibaba.fastjson.JSON;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GihubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            //重复调用response.body()会报错
            //System.out.println("responseBody:"+response.body().string());
            String[] strSplit = str.split("&");
            String[] tokens = strSplit[0].split("=");
            String accessToken = tokens[1];
            return accessToken;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //传入用户的accessToken，用于获取用户的信息
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            //url的返回，是一个JSON格式
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            //将JSON格式的数据封装成一个对象
            GithubUser githubUser = JSON.parseObject(responseBody, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
