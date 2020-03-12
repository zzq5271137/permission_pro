package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Permission implements Serializable {

    private Long pid;
    private String pname;
    private String presource;

}