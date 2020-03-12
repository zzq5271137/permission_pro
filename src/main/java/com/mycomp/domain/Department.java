package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Department implements Serializable {

    private Long id;

    private String name;

}