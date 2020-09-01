package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Employee;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	public Employee getEmployee(Integer empId) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Employee emp = session.getMapper(EmployeeMapper.class).selectEmployee(empId);
			return emp;
		}
	}

	@Override
	public Integer createEmployee(Employee employee) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			int insertkey = session.getMapper(EmployeeMapper.class).createEmployee(employee);
			return insertkey;
		}
	}

}
