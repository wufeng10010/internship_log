package com.itheima.controller;

import com.itheima.anno.Log;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Emp;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理Controller
 */

//@Scope("prototype") //设置为非单例，每次实例化新对象，默认是singleto(单例)
//@Lazy //将类的实例化延迟到使用时（默认启动就实例化）
@RestController
@Slf4j
@RequestMapping("depts")
public class DeptController {

//    private static Logger log = LoggerFactory.getLogger(DeptController.class);
//    @RequestMapping(value = "/depts", method = RequestMethod.GET) //指定请求方式为get
    @Autowired
    private DeptService deptService;

    @Log
    @GetMapping
    public Result list(){
        log.info("查询全部部门数据");

        //调用service查询部门数据
        List<Dept> deptList = deptService.list();
        log.info("查询完成");
        return Result.success(deptList);
    }

    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) throws Exception {
        log.info("根据id删除部门{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    @Log
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门：{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据id查询部门信息，id:{}", id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    @Log
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("更新部门信息：{}", dept);
        deptService.update(dept);
        return Result.success();
    }

//    @PutMapping("/{id}")
//    public Result update()
}
