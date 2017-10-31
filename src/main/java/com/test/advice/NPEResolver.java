package com.test.advice;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Intellij IDEA
 * Author: xionggao
 * Desc:
 * Date: 2017/9/29
 * Email: xionggao@terminus.io
 */
@ControllerAdvice
public class NPEResolver {
	
	private static final Logger log = LoggerFactory.getLogger(NPEResolver.class);
	private final MessageSource messageSource;
	
	@Autowired
	public NPEResolver(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler({NullPointerException.class})
	public void OPErrorHandler(NullPointerException e, HttpServletResponse response) throws Exception {
		Locale locale = LocaleContextHolder.getLocale();
		log.error("NPE happened, locale={}, cause={}", locale, Throwables.getStackTraceAsString(e));
		String message = this.messageSource.getMessage("npe.error", (Object[])null, "npe.error", locale);
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
	}
}
