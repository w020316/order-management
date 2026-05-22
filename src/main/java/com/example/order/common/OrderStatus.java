package com.example.order.common;

import com.example.order.exception.BusinessException;

public enum OrderStatus {

    PENDING(0, "待支付"),
    PAID(1, "已支付"),
    CANCELLED(2, "已取消"),
    COMPLETED(3, "已完成");

    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new BusinessException("无效的订单状态码: " + code);
    }
}
