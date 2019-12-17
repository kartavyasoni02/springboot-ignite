package com.teacheron.sales.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.teacheron.sales.dto.MessageDTO;
import com.teacheron.sales.dto.MessageType;
import com.teacheron.sales.exceptions.UserAlreadyExistException;
import com.teacheron.sales.exceptions.UserNotFoundException;

@ControllerAdvice
public class ControllerValidationHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<MessageDTO> processValidationError(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();

		return processFieldError(result.getAllErrors());
	}

	private List<MessageDTO> processFieldError(List<ObjectError> error) {
		List<MessageDTO> errorList = new ArrayList<>();

		for (ObjectError objectError : error) {

			Locale currentLocale = LocaleContextHolder.getLocale();

			String msg = messageSource.getMessage(objectError.getDefaultMessage(), null, currentLocale);

			MessageDTO message = new MessageDTO(MessageType.ERROR, msg, objectError.getDefaultMessage());
			errorList.add(message);
		}
		return errorList;
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO userNotFound(UserNotFoundException e) {
		Locale currentLocale = LocaleContextHolder.getLocale();

		String msg = messageSource.getMessage(e.getMessage(), null, currentLocale);
		return new MessageDTO(MessageType.ERROR, msg, e.getMessage());
	}

	@ExceptionHandler(value = UserAlreadyExistException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO userAlreadyExist(UserAlreadyExistException e) {
		Locale currentLocale = LocaleContextHolder.getLocale();

		String msg = messageSource.getMessage(e.getMessage(), null, currentLocale);
		return new MessageDTO(MessageType.ERROR, msg, e.getMessage());
	}
	
	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public MessageDTO missingRequestParameter(MissingServletRequestParameterException e) {
		Locale currentLocale = LocaleContextHolder.getLocale();

		String msg = messageSource.getMessage("spring.bind.missingparameter", null, currentLocale);
		return new MessageDTO(MessageType.ERROR, msg, "spring.bind.missingparameter");
	}

}
