package com.kankan.util;

public class GenerateRedisKeyUtil {
    private static final String USER_CACHE_PREFIX = "qiding:jiaguo:user:mail:";
    private static  final String USER_VERIFY_SMS_CODE_PREFIX = "qiding:jiaguo:user:verifySmsCodeToken:";
    private static  final String USER_LOGIN_TOKEN_PREFIX = "qiding:jiaguo:user:userToken:";

    public static  String geneRateActiveCode(String userEmail){
        return  USER_CACHE_PREFIX+userEmail;
    }


    public static  String geneRateVerifyToken(String userEmail){
        return  USER_VERIFY_SMS_CODE_PREFIX+userEmail;
    }


    public static  String geneRateUserToken(String userId){
        return  USER_LOGIN_TOKEN_PREFIX+userId;
    }



}
