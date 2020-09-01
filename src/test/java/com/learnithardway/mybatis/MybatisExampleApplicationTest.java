package com.learnithardway.mybatis;

import com.learnithardway.mybatis.model.Department;
import com.learnithardway.mybatis.model.Employee;
import com.learnithardway.mybatis.repo.DepartmentRepository;
import com.learnithardway.mybatis.repo.EmployeeRepository;
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
	EmployeeRepository employeeRepository;

	@Autowired
	SqlSessionFactory sqlSessionFactory;

	@Autowired
	DepartmentRepository departmentRepository;

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
		Employee employee = employeeRepository.getEmployee(101);
		Assertions.assertEquals("ABCD", employee.getName(), "Employee name should match");
	}

	@Test
	public void testCreatemployee() {
		Employee employee1 = new Employee();
		employee1.setName("test");
		Integer employee = employeeRepository.createEmployee(employee1);
		Assertions.assertEquals(1, employee, "Employee id should be 1");
	}

	@Test
	public void testNPlusOneQueryRelation() {
		Department department = departmentRepository.getDepartment(1);
		Assertions.assertEquals(4, department.getEmployeeList().size());
	}
}