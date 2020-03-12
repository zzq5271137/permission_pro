package com.mycomp.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Menu implements Serializable {

    private Long id;
    private String text;
    private String url;
    private Menu parent;
    private List<Menu> children = new ArrayList<>();

}