package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Department;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentRepository {

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	public Department getDepartment(Integer department){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			Department dep = session.selectOne(
					"DepartmentMapper.selectDepartment", department);
			return dep;
		}
	}
}
