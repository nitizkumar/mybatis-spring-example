package com.learnithardway.mybatis.repo;

import com.learnithardway.mybatis.model.Department;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {

	Department selectDepartment(int i);

}
