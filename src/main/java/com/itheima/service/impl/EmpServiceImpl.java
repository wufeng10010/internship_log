package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.pojo.Emp;
import com.itheima.service.EmpService;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
//    @Override
//    public PageBean page(Integer page, Integer pageSize) {
//        //1.调用mapper接口获取总记录数
//        Long count = empMapper.count();
//
//        //2.获取分页查询结果列表
//        Integer start = (page-1)*pageSize;
//        List<Emp> empList = empMapper.page(start, pageSize);
//
//        PageBean pageBean = new PageBean(count, empList);
//
//        return pageBean;
//    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, Short gender,
                         LocalDate begin, LocalDate end) {

        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Emp> empList = empMapper.list(name, gender,begin, end);
        Page<Emp> p = (Page<Emp>) empList;  //强转

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult()); //参数：(总数，列表)

        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        empMapper.delete(ids);
    }

    @Override
    public void save(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);
    }

    @Override
    public Emp login(Emp emp) {

        return empMapper.getByUsernameAndPassword(emp);
    }

    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());

        empMapper.update(emp);
    }

    @Override
    public Emp getById(Integer id) {
        Emp emp = empMapper.getById(id);
        return emp;
    }
}
