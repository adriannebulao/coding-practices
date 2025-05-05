package com.adriannebulao.coding_practices.leave.service;

import java.util.*;

import com.adriannebulao.coding_practices.leave.domain.Employee;
import com.adriannebulao.coding_practices.leave.domain.LeaveApplication;
import com.adriannebulao.coding_practices.leave.repositories.EmployeeRepository;
import com.adriannebulao.coding_practices.leave.repositories.LeaveApplicationRepository;

public class LeaveApprovalService {

	private EmployeeRepository employeeRepo = new EmployeeRepository();
	private LeaveApplicationRepository leaveApplicationRepo = new LeaveApplicationRepository();

	public Collection<LeaveApplication> getPendingApplicationsFiledToApprover(
			int approverEmployeeId) {
		return leaveApplicationRepo.findPendingByApprover(approverEmployeeId);
	}

	public void approveLeaveApplication(int approverEmployeeId,
			UUID leaveApplicationId) {
		Employee approver = employeeRepo.findById(approverEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		approver.approve(application);
		leaveApplicationRepo.updateStatus(application);
	}

	public void disapproveLeaveApplication(int approverEmployeeId,
			UUID leaveApplicationId) {
		Employee approver = employeeRepo.findById(approverEmployeeId);
		LeaveApplication application = leaveApplicationRepo
				.findById(leaveApplicationId);
		approver.disapprove(application);
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
