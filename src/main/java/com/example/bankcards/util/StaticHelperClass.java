package com.example.bankcards.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class StaticHelperClass {

	/**
	 * Метод, возвращающий username авторизованного пользователя
	 */
	public static String getAuthUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
