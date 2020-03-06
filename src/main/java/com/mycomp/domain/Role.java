package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Role {

    private Long rid;
    private String rnum;
    private String rname;

    // 一个Role可以有多个Permission
    private List<Permission> permissions = new ArrayList<>();

}