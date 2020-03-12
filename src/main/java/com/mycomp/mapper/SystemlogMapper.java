package com.mycomp.mapper;

import com.mycomp.domain.Systemlog;

import java.util.List;

public interface SystemlogMapper {

    void insert(Systemlog record);

    List<Systemlog> selectAll();

}