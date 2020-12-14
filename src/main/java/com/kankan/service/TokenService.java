package com.kankan.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.kankan.module.User;
import com.kankan.util.GsonUtil;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Service
public class TokenService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String createUserToken(User user) {
        String userToken = UUID.randomUUID().toString();
        Map<String, String> tokenMap = user.tokenMap();
        redisTemplate.boundHashOps(userToken).putAll(tokenMap);
        return userToken;
    }

    public User findUserByToken(String verifyToken) {
        if (redisTemplate.hasKey(verifyToken)) {
            Map cacheMap = redisTemplate.boundHashOps(verifyToken).entries();
            String gsonMap = GsonUtil.toGson(cacheMap);
            User user = GsonUtil.parseJson(gsonMap, User.class);
            return user;
        }
        return User.emptyUser();
    }

    public void refreshToken(String userToken, User user) {
        Map<String, String> tokenMap = user.tokenMap();
        redisTemplate.boundHashOps(userToken).putAll(tokenMap);
    }
}
