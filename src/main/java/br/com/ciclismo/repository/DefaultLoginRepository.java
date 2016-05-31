package br.com.ciclismo.repository;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response.Status;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.security.Criptografia;
import br.com.ciclismo.util.StatusException;

@Named
@Transactional
public class DefaultLoginRepository implements LoginRepository {

	@PersistenceContext
	public EntityManager entityManager;
	
	@Override
	public Pessoa login(String login, String senha) throws Exception {
		try {
			senha = Criptografia.criptografar(senha);
			Query query = entityManager.createQuery("SELECT p FROM Pessoa p WHERE p.login =:login AND p.senha =:senha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			Pessoa pessoa = (Pessoa) query.getSingleResult();
			if (pessoa == null) {
				
				throw new StatusException(Status.UNAUTHORIZED.getStatusCode(), "Acesso negado");
			}
			return pessoa;
		} catch (Exception e) {
			throw e;
		}
	}

}
