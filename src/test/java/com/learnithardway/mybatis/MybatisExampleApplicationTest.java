package com.learnithardway.mybatis;

import com.learnithardway.mybatis.model.Department;
import com.learnithardway.mybatis.model.Employee;
import com.learnithardway.mybatis.repo.DepartmentMapper;
import com.learnithardway.mybatis.repo.EmployeeMapper;
import org.apache.ibatis.session.SqlSessionFactory;
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

import static org.junit.jupiter.api.Assertions.*;

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
		assertEquals("ABCD", employee.getName(), "Employee name should match");
	}

	@Test
	public void testCreatemployee() {
		Employee employee1 = new Employee();
		employee1.setName("test");
		Integer updatedRows = employeeMapper.createEmployee(employee1);
		assertNotNull(employee1.getId());
		assertEquals(1, updatedRows, "number of updated rows should be 1");
	}

	@Test
	public void testFindEmployee() {
		Employee employee1 = new Employee();
		employee1.setName("ABCD");
		Employee employee = employeeMapper.findEmployee(employee1);
		assertEquals(101, employee.getId(), "Employee id should be 101");

		// when name is null
		employee1.setName(null);
		employee1.setId(101);
		// then the id should be used
		employee = employeeMapper.findEmployee(employee1);
		assertEquals("ABCD", employee.getName(), "Employee name should be ABCD");
		// when name is empty
		employee1.setName("");
		employee1.setId(101);
		// then the id should be used
		employee = employeeMapper.findEmployee(employee1);
		assertEquals("ABCD", employee.getName(), "Employee name should be ABCD");
		List<Employee> employees = employeeMapper.findAll();
		assertEquals(4, employees.size());
		Employee employee2 = new Employee();
		employee2.setName("test new");
		employeeMapper.createEmployee(employee2);

		// find checks id and name
		Employee retrieved = employeeMapper.findEmployee(employee2);
		assertNotNull(retrieved);
		// find by id by setting the name to null
		employee2.setName(null);
		 retrieved = employeeMapper.findEmployee(employee2);
		assertNotNull(retrieved);
		// find by id by setting the name to empty
		employee2.setName("");
		 retrieved = employeeMapper.findEmployee(employee2);
		assertNotNull(retrieved);

		// auto_increment column has worked
		assertNotNull(retrieved.getId());
		employees = employeeMapper.findAll();
		assertEquals(5, employees.size());

		// delete the new one
		Employee employeeToDelete = new Employee();
		employeeToDelete.setName("test new");

		employeeMapper.deleteEmployee(employeeToDelete);
		// now the one 2 delete should be gone
		assertNull(employeeMapper.findEmployee(employee2));
		employees = employeeMapper.findAll();
		assertEquals(4, employees.size());

	}

	@Test
	public void testNPlusOneQueryRelation() {
		Department department = departmentMapper.selectDepartment(1);
		assertEquals(4, department.getEmployeeList().size());
	}


}