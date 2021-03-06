package com.learnithardway.mybatis;

import com.learnithardway.mybatis.model.Department;
import com.learnithardway.mybatis.model.Employee;
import com.learnithardway.mybatis.repo.DepartmentMapper;
import com.learnithardway.mybatis.repo.EmployeeMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

@SpringBootTest
class MybatisExampleApplicationTest {

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	DepartmentMapper departmentMapper;

	@BeforeEach
	public void setupDatabase() {
		try {
			Connection connection = sqlSessionFactory.openSession().getConnection();
			Statement statement = connection.createStatement();
			URL resource = ClassLoader.getSystemClassLoader().getResource("setup.sql");
			List<String> lines = Files.readAllLines(Path.of(resource.toURI()));
			for (String line : lines) {
				if (!line.trim().isEmpty()) {
					statement.execute(line);
				}
			}
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetEmployee() {
		Employee employee = employeeMapper.selectEmployee(101);
		Assertions.assertEquals("ABCD", employee.getName(), "Employee name should match");
	}

	@Test
	public void testCreatemployee() {
		Employee employee1 = new Employee();
		employee1.setName("test");
		Integer employee = employeeMapper.createEmployee(employee1);
		Assertions.assertEquals(1, employee, "Employee id should be 1");
	}

	@Test
	public void testFindEmployee() {
		Employee employee1 = new Employee();
		employee1.setName("ABCD");
		Employee employee = employeeMapper.findEmployee(employee1);
		Assertions.assertEquals(101, employee.getId(), "Employee id should be 1");

		employee1.setName(null);
		employee1.setId(101);
		employee = employeeMapper.findEmployee(employee1);
		Assertions.assertEquals("ABCD", employee.getName(), "Employee id should be 1");
	}

	@Test
	public void testNPlusOneQueryRelation() {
		Department department = departmentMapper.selectDepartment(1);
		Assertions.assertEquals(4, department.getEmployeeList().size());
	}


}