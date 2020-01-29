package com.teacheron.sales.enumerations;
import java.util.NoSuchElementException;

/**
 * Set of error Codes that are used for indicating specific error scenarios
 * 
 */
public enum ErrorCodeEnum {

	INVALID_SESSION_LINK_STATUS_CHANGED(996), 
	INVALID_SESSION(997), 
	USER_NOT_FOUND(998), 
	BASE_DB_ERROR(999), 
	UNEXPECTED_ERROR(1000), 
	INSUFFICIENT_PARAMETERS(1001), 
	USER_ALREADY_EXIST(1002), 
	USER_AUTHENTICATION_FAILED(1003), 
	INVALID_USER_INFO(1004), 
	GENERIC_ERROR(1111), 
	FILE_OPERATION_ERROR(1005) ,
	PARSE_EXCEPTION(1006),
	VALIDITY(1009);
	
	private final int codeId;

	private ErrorCodeEnum(final int codeId) {
		this.codeId = codeId;
	}

	public int getCodeId() {
		return this.codeId;
	}

	/**
	 * Converts an int value into an ErrorCode
	 * 
	 * @param errorCode
	 * @return {@link ErrorCodeEnum}
	 */
	public static ErrorCodeEnum getExceptionCode(final int errorCode) {

		ErrorCodeEnum eErrorCode = null;
		for (final ErrorCodeEnum status : ErrorCodeEnum.values()) {
			if (status.getCodeId() == errorCode) {
				eErrorCode = status;
				break;
			}
		}
		if (null == eErrorCode) {
			throw new NoSuchElementException("The received code " + errorCode + " is not valid !!!");
		} else {
			return eErrorCode;
		}

	}
	
}
