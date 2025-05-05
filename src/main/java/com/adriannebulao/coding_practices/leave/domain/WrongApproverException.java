package com.adriannebulao.coding_practices.leave.domain;

import com.adriannebulao.coding_practices.leave.exceptions.LeaveApplicationException;

public class WrongApproverException extends LeaveApplicationException {

	public WrongApproverException() {
	}

	public WrongApproverException(String message) {
		super(message);
	}

	public WrongApproverException(Throwable cause) {
		super(cause);
	}

	public WrongApproverException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongApproverException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
