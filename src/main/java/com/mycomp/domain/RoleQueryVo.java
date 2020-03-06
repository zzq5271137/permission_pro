package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleQueryVo {

    private Integer page;
    private Integer rows;
    private String keyword;

}
