package br.com.ciclismo.service;

import javax.ws.rs.core.Response;

public interface LoginService {

	Response login(String login, String senha);
}
