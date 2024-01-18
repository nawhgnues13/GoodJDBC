package com.kh.jdbc.day04.pstmt.employee.controller;

import java.util.List;

import com.kh.jdbc.day04.pstmt.employee.model.dao.EmployeeDAO;
import com.kh.jdbc.day04.pstmt.employee.model.vo.Employee;

public class EmployeeController {
	private EmployeeDAO eDao;

	public EmployeeController() {
		eDao = new EmployeeDAO();
	}

	public int registerEmployee(Employee emp) {
		int result = eDao.insertEmployee(emp);
		return result;
	}

	public int modifyEmployee(Employee emp) {
		int result = eDao.updateEmployee(emp);
		return result;
	}

	public int removeEmployee(String empId) {
		int result = eDao.deleteEmployee(empId);
		return result;
	}

	public List<Employee> printAllEmployees() {
		List<Employee> eList = eDao.selectAllEmployee();
		return eList;
	}
}
