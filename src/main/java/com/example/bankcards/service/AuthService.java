package com.example.bankcards.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {


	/**
	 * Выполняет выход пользователя из системы
	 *
	 * @param refreshToken Токен обновления
	 * @param request      Запрос HTTP
	 * @param response     Ответ HTTP
	 */
	void logout(String refreshToken, HttpServletRequest request, HttpServletResponse response);


}
