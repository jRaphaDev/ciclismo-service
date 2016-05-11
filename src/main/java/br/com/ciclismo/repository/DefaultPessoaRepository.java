package br.com.ciclismo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.security.Criptografia;
import br.com.ciclismo.util.StatusException;

public class DefaultPessoaRepository implements PessoaRepository {
	
	@PersistenceContext
	public EntityManager entityManager;
	private static final Logger logger = LoggerFactory.getLogger(DefaultPessoaRepository.class);

	@Override
	public Pessoa criar(Pessoa pessoa) {
		try {
			pessoa.setSenha(Criptografia.criptografar(pessoa.getSenha()));
			entityManager.persist(pessoa);
			logger.debug("Pessoa criada com sucesso.");
			return pessoa;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public Pessoa achar(long id) throws Exception {
		try {
			Pessoa pessoa = entityManager.find(Pessoa.class, id);
			if (pessoa == null) {
				logger.error("O id : " + id + " não foi encontrado.");
				throw new StatusException(Status.NOT_FOUND.getStatusCode(), "O id : " + id + " não foi encontrado.");
			}
			return pessoa;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> listar() {
		try {
			Query query = entityManager.createQuery("FROM Pessoa");
			List<Pessoa> lista = query.getResultList();
			return lista;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public void alterar(long id, Pessoa pessoa) throws Exception {
		try {
			achar(id);
			entityManager.merge(pessoa);
			logger.debug("Pessoa alterada com sucesso.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		
	}

	@Override
	public void deletar(long id) throws Exception {
		try {
			Pessoa pessoa = achar(id);
			entityManager.remove(pessoa);
			logger.debug("Pessoa removida com sucesso.");
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

}
