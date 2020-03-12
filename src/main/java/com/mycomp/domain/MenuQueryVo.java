package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuQueryVo {

    private Integer page;
    private Integer rows;
    private String keyword;

}
