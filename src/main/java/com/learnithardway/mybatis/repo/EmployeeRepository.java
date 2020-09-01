package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Employee;

public interface EmployeeRepository {

	public Employee getEmployee(Integer empId);

	public Integer createEmployee(Employee employee);
}
