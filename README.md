# 3.8

使用mabatis操控数据库有两种方式
* 基于注解的方式
* 基于.xml文件

### 使用注解
使用mybatis的注解，主要是用来完成一些简单的增删改查，如使用sql的insert语句，需要在Mapper接口中定义一个方法，并在方法上面添加@Insert注解
```java
@Mapper
public interface EmpMapper {
    //新增员工
    @Options(keyProperty = "id", useGeneratedKeys = true) //自动将生成的主键值赋值给emp对象的id属性
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
            "values (#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insert(Emp emp);
```
在测试类中，定义insert的测试方法，new一个员工对象，将属性赋值给emp，然后调用Mapper接口中的插入方法
```java
@Test
    public void testInsert(){
        Emp emp = new Emp();

        emp.setUsername("Tom2");
        emp.setName("汤姆2");
        emp.setGender((short)1);
        emp.setJob((short)1);
        emp.setImage("1.jpg");
        emp.setEntrydate(LocalDate.of(2021, 1, 1));
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setDeptId(2);

        empMapper.insert(emp);
        System.out.println(emp.getId());
    }
```
