package br.com.ciclismo.repository;

import java.util.List;

import br.com.ciclismo.model.Pessoa;

public interface PessoaRepository {

	Pessoa criar(Pessoa pessoa);
	
	Pessoa achar(long id) throws Exception;
	
	List<Pessoa> listar();
	
	void alterar(long id, Pessoa pessoa) throws Exception;
	
	void deletar(long id) throws Exception;
}
