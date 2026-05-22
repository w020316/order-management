package com.example.order.dto;

import lombok.Data;

@Data
public class PageRequest {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public Integer getOffset() {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        return (pageNum - 1) * getPageSize();
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }
        return pageSize;
    }

    public Integer getPageNum() {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        return pageNum;
    }
}
