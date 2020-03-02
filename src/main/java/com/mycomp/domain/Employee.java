package com.mycomp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class Employee {
    private Long id;

    private String username;

    /*
     * 日期的格式化:
     * 1. @JsonFormat: 从数据库查询后, 格式化日期, 返回给页面;
     * 2. @DateTimeFormat: 从页面提交表单时, 格式化日期, 存到domain中的属性;
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputtime;

    private String tel;

    private String email;

    private Boolean state;

    private Boolean admin;

    private Department department;

}