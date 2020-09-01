package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Employee;

public interface EmployeeMapper {

	public Employee selectEmployee(Integer id);

	public Integer createEmployee(Employee employee);
}
