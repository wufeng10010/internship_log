package com.itheima.mapper;

import com.itheima.pojo.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {
//
//    @Select("select count(*) from emp")
//    public Long count(); //查询总记录数
//
//    @Select("select * from emp limit #{start}, #{pageSize}")
//    public List<Emp> page(Integer start, Integer pageSize); //分页查询，获取列表数据

    //使用插件来完成分页查询
//    @Select("select * from tlias.emp")
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);

    void delete(List<Integer> ids);

    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "values (#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);


    //根据用户名和密码查询员工，然后将查询的结果返回
    @Select("select * from tlias.emp where username=#{username} and password=#{password}")
    Emp getByUsernameAndPassword(Emp emp);


    //根据部门id删除员工
    @Delete("delete from emp where dept_id = #{deptId}")
    void deleteByDeptId(Integer deptId);

    void update(Emp emp);

    @Select("select * from tlias.emp where id=#{id}")
    Emp getById(Integer id);
}
