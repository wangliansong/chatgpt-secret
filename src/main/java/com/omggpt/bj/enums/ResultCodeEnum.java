package com.omggpt.bj.enums;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

public enum ResultCodeEnum {


    JWT_EXPIRED("JWT_EXPIRED","JWT_EXPIRED"),
    JWT_SIGNATURE("JWT_SIGNATURE","JWT_SIGNATURE"),
    JWT_ERROR("JWT_ERROR","TOKEN认证错误");
    public String code;
    public String message;
    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getName(String code) {
        if(StringUtils.isBlank(code)){
            return null;
        }
        ResultCodeEnum[] resultCodeEnums = values();
        for (ResultCodeEnum resultCodeEnum : resultCodeEnums) {
            if (resultCodeEnum.code.equals(code)) {
                return resultCodeEnum.getMessage();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
