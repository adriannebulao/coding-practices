package com.adriannebulao.coding_practices.leave.domain;

import com.adriannebulao.coding_practices.leave.exceptions.LeaveApplicationException;

public class WrongFilerException extends LeaveApplicationException {

	public WrongFilerException() {
	}

	public WrongFilerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongFilerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongFilerException(String message) {
		super(message);
	}

	public WrongFilerException(Throwable cause) {
		super(cause);
	}

}
