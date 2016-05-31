package br.com.ciclismo.service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.repository.DefaultLoginRepository;

public class DefaultLoginService implements LoginService {

	public DefaultLoginRepository defaultLoginRepository;

	@Inject
	public DefaultLoginService(DefaultLoginRepository defaultLoginRepository) {
		this.defaultLoginRepository = defaultLoginRepository;
	}
	
	@Override
	public Response login(String login, String senha) {
		try {
			Pessoa pessoa = defaultLoginRepository.login(login, senha);
			return Response.accepted().entity(pessoa).build();
		} catch (Exception e) {
			
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
