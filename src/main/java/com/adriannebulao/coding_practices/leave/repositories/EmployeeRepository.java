package com.adriannebulao.coding_practices.leave.repositories;

import java.sql.*;

import com.adriannebulao.coding_practices.leave.domain.Employee;

public class EmployeeRepository extends Repository {

	public Employee findById(int filerEmployeeId) {
		String sql = "select * from employees where employee_id = ?";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filerEmployeeId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int vlCredits = rs.getInt("vl_credits");
			int slCredits = rs.getInt("sl_credits");
			int approverEmployeeId = rs.getInt("approver_employee_id");
			return new Employee(filerEmployeeId, vlCredits, slCredits, new Employee(approverEmployeeId, 0, 0, null));
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}

}
