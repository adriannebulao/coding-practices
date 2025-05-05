package com.adriannebulao.coding_practices.leave.service;

import com.adriannebulao.coding_practices.leave.exceptions.LeaveApplicationException;

public class InsufficientLeaveCreditsException extends LeaveApplicationException {

	public InsufficientLeaveCreditsException() {
	}

	public InsufficientLeaveCreditsException(String message) {
		super(message);
	}

	public InsufficientLeaveCreditsException(Throwable cause) {
		super(cause);
	}

	public InsufficientLeaveCreditsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientLeaveCreditsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
