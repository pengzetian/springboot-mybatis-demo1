package com.test.advice;

import com.google.common.base.Throwables;
import com.test.exception.JsonResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


@ControllerAdvice
public class JsonExceptionResolver {
	
	private static final Logger log = LoggerFactory.getLogger(JsonExceptionResolver.class);
	private final MessageSource messageSource;
	
	@Autowired
	public JsonExceptionResolver(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler({JsonResponseException.class})
	public void OPErrorHandler(JsonResponseException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Locale locale = LocaleContextHolder.getLocale();
		log.debug("JsonResponseException happened,locale={}, cause={}", locale, Throwables.getStackTraceAsString(e));
		String message = e.getMessage();
		
		try {
			message = this.messageSource.getMessage(e.getMessage(), (Object[])null, e.getMessage(), locale);
		} catch (Exception var7) {
			log.warn("translate json response exception message fail, message = {}, cause {}", e.getMessage(), Throwables.getStackTraceAsString(var7));
		}
		
		response.sendError(e.getStatus(), message);
	}
}
