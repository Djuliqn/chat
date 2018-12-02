package com.chat.server.core;

import org.springframework.validation.BindingResult;

public class ErrorBuilder {

	public static String buildErrorMessage(final BindingResult result) {
		StringBuilder errorMessageBuilder = new StringBuilder();

		if (result.getFieldErrorCount() > 1) {

			result.getAllErrors().forEach(error -> {
				errorMessageBuilder.append(error.getDefaultMessage());
				errorMessageBuilder.append(System.lineSeparator());
			});

		} else {
			errorMessageBuilder.append(result.getFieldError().getDefaultMessage());
		}

		return errorMessageBuilder.toString();
	}
}
