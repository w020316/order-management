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
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        return (pageNum - 1) * pageSize;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        return pageSize;
    }
}
