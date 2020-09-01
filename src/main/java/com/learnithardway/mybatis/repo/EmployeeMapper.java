package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

	@Select("select id,name from Employee where id = #{id}")
	public Employee selectEmployee(Integer id);

	@Insert(" insert into Employee (id,name) values ( #{id}, #{name} )")
	public Integer createEmployee(Employee employee);

	public Employee findEmployee(Employee employee);
}
