package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Systemlog {

    private Long id;
    private Date optime;
    private String ip;
    private String func;
    private String params;

}