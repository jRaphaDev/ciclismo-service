package br.com.ciclismo.repository;

import br.com.ciclismo.model.Pessoa;

public interface LoginRepository {

	Pessoa login(String login, String senha) throws Exception;
}
