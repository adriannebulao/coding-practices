package com.adriannebulao.coding_practices.leave.service;

import java.time.*;
import java.util.*;

import com.adriannebulao.coding_practices.leave.domain.Employee;
import com.adriannebulao.coding_practices.leave.domain.LeaveApplication;
import com.adriannebulao.coding_practices.leave.domain.LeaveType;
import com.adriannebulao.coding_practices.leave.repositories.EmployeeRepository;
import com.adriannebulao.coding_practices.leave.repositories.LeaveApplicationRepository;

public class LeaveFilingService {

	private EmployeeRepository employeeRepo = new EmployeeRepository();
	private LeaveApplicationRepository leaveApplicationRepo = new LeaveApplicationRepository();

	/** For simplicity, only whole-day leaves are supported. No half-day leaves. **/
	public void fileLeave(int filerEmployeeId, LeaveType leaveType, LocalDate start, LocalDate end) {
		// Validate dates
		if (end.isBefore(start)) {
			throw new IllegalArgumentException("End date before start date: Start: " + start + " End: " + end);
		}

		// Retrieve the filer employee
		Employee filer = employeeRepo.findById(filerEmployeeId);

		// Create a LeaveApplication instance
		LeaveApplication leaveApplication = LeaveApplication.newLeaveApplication(filer, filer.getLeaveApprover(), leaveType, start, end);

		// Calculate the number of leave days, excluding weekends and holidays
		int leaveDays = leaveApplication.getLeaveDays();

		// Check if the filer has enough leave credits
		int availableLeaveCredits = filer.getLeaveCreditsOfType(leaveType);
		if (availableLeaveCredits < leaveDays) {
			throw new InsufficientLeaveCreditsException("Employee " + filer + " tried to file " + leaveDays + " days leave of type " + leaveType + " but the employee only has " + availableLeaveCredits + ' ' + leaveType + " leave credits.");
		}

		// Proceed to create the leave application record in the repository
		leaveApplicationRepo.create(leaveApplication);
	}

	public Collection<LeaveApplication> getPendingLeaveApplications(
			int filerEmployeeId) {
		return leaveApplicationRepo
				.findPendingByFilerEmployeeId(filerEmployeeId);
	}

	public void cancelLeaveApplication(int filerEmployeeId,
			UUID leaveApplicationId) {
		Employee filer = employeeRepo.findById(filerEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		filer.cancel(application);
		leaveApplicationRepo.updateStatus(application);
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public void setLeaveApplicationRepository(
			LeaveApplicationRepository leaveApplicationRepo) {
		this.leaveApplicationRepo = leaveApplicationRepo;
	}

}
