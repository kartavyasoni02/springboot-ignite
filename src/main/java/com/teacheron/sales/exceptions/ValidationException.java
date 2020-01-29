package com.teacheron.sales.exceptions;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

import com.teacheron.sales.enumerations.ErrorCodeEnum;

@SuppressWarnings("serial")
public class ValidationException extends TeacheronException {

	private List<ConstraintViolation<?>> violationConstraints;
	private Object source;

	public List<ConstraintViolation<?>> getViolationConstraints() {
		return violationConstraints;
	}

	public ValidationException(String message, ErrorCodeEnum errorCodeEnum) {
		super(message, errorCodeEnum);
	}

	public ValidationException() {
		super();
	}

	public void setViolationConstraints(List<ConstraintViolation<?>> violationConstraints) {
		this.violationConstraints = violationConstraints;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	/*
	 * public String getViolationConstraintsMessage() { List<ConstraintViolation<?>> listViolations = getViolationConstraints(); String
	 * message = ""; message = message + "Number of violations:" + listViolations.size(); for (ConstraintViolation<?> violation :
	 * listViolations) { message = message + "\nViolation:" + violation.getPropertyPath() + " -> " + violation.getMessage()); } return
	 * message; }
	 */

	public List<String> getViolationConstraintsMessage() {
		List<ConstraintViolation<?>> listViolations = getViolationConstraints();
		List<String> violations = new ArrayList<String>(listViolations.size());
		for (ConstraintViolation<?> violation : listViolations) {

			violations.add(violation.getMessage());
		}
		return violations;
	}
}
