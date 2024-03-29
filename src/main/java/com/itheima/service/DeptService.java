package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
public interface DeptService {
    //查询全部数据
    List<Dept> list();

    void deleteById(Integer id) throws Exception;

    void add(Dept dept);

    Dept getById(Integer id);

    void update(Dept dept);
}
