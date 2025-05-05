package com.adriannebulao.coding_practices.leave.repositories;

import java.sql.*;
import java.time.*;
import java.util.*;

import com.adriannebulao.coding_practices.leave.domain.Employee;
import com.adriannebulao.coding_practices.leave.domain.LeaveApplication;
import com.adriannebulao.coding_practices.leave.domain.LeaveType;

public class LeaveApplicationRepository extends  Repository {

	public Collection<LeaveApplication> findPendingByFilerEmployeeId(
			int filerEmployeeId) {
		String sql = "select * from leave_applications "
				+ " where filer_employee_id = ? AND status = 'PENDING'";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filerEmployeeId);
			ResultSet rs = stmt.executeQuery();
			return applicationsFromResultSet(rs);
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}

	public LeaveApplication findById(UUID leaveApplicationId) {
		String sql = "select * from leave_applications where leave_application_id = ?";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, leaveApplicationId.toString());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return applicationFromResultSet(rs);
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}

	public Collection<LeaveApplication> findPendingByApprover(
			int approverEmployeeId) {
		String sql = "select * from leave_applications "
				+ " where approver_employee_id = ? AND status = 'PENDING'";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, approverEmployeeId);
			ResultSet rs = stmt.executeQuery();
			return applicationsFromResultSet(rs);
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}

	public void updateStatus(LeaveApplication application) {
		String sql = "update leave_applications set status = ? where leave_application_id = ?";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, application.getStatus().toString());
			stmt.setString(2, application.getLeaveApplicationId().toString());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Rows updated not 1, was: "
						+ rowsUpdated);
			}
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}
	
	public void create(LeaveApplication newLeaveApplication) {
		String sql = "insert into leave_applications "
				+ " (leave_application_id, filer_employee_id, approver_employee_id, leave_type, start_date,	end_date, status) "
				+ " values (?, ?, ?, ?, ?, ?, ?) ";
		try (Connection conn = super.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newLeaveApplication.getLeaveApplicationId()
					.toString());
			stmt.setInt(2, newLeaveApplication.getFiler().getEmployeeId());
			stmt.setInt(3, newLeaveApplication.getApprover().getEmployeeId());
			stmt.setString(4, newLeaveApplication.getType().toString());
			stmt.setDate(5,
					java.sql.Date.valueOf(newLeaveApplication.getStart()));
			stmt.setDate(6, java.sql.Date.valueOf(newLeaveApplication.getEnd()));
			stmt.setString(7, newLeaveApplication.getStatus().toString());
			int rowsUpdated = stmt.executeUpdate();
			if (rowsUpdated != 1) {
				throw new SQLException("Rows updated not 1, was: "
						+ rowsUpdated);
			}
		} catch (SQLException e) {
			throw new DataAccessException("DataAccessException", e);
		}
	}

	private LeaveApplication applicationFromResultSet(ResultSet rs) {
				 try {
					 UUID leaveApplicationId = UUID.fromString(rs
							 .getString("leave_application_id"));
					 int filerEmployeeId = rs.getInt("filer_employee_id");
					 int approverEmployeeId = rs.getInt("approver_employee_id");
					 LeaveType leaveType = LeaveType.valueOf(rs.getString("leave_type"));
					 LocalDate start = rs.getDate("start_date").toLocalDate();
					 LocalDate end = rs.getDate("end_date").toLocalDate();
					 LeaveApplication.Status status = LeaveApplication.Status.valueOf(rs
							 .getString("status"));
					 return new LeaveApplication(leaveApplicationId, new Employee(
							 filerEmployeeId, 0, 0, null), new Employee(approverEmployeeId,
							 0, 0, null), leaveType, start, end, status);
				 } catch (SQLException e) {
					 throw new DataAccessException("DataAccessException", e);
				 }
			 }

	private Collection<LeaveApplication> applicationsFromResultSet(ResultSet rs) {
		Collection<LeaveApplication> applications = new ArrayList<>();
		while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                throw new DataAccessException("DataAccessException", e);
            }
            applications.add(applicationFromResultSet(rs));
		}
		return applications;
	}
}
