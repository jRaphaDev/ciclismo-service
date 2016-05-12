package br.com.ciclismo.service;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.repository.PessoaRepository;

public class DefaultPessoaService implements PessoaService {

	private PessoaRepository pessoaRepository;
	private Validator validator;
	private static final Logger logger = LoggerFactory.getLogger(DefaultPessoaService.class);
	
	@Inject
	public DefaultPessoaService(Validator validator, PessoaRepository pessoaRepository) {
		this.validator = validator;
		this.pessoaRepository = pessoaRepository;
	}
	
	@Override
	public Response criar(UriInfo uriInfo, Pessoa pessoa) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return null;
	}

	@Override
	public Response achar(long id) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return null;
	}

	@Override
	public Response listar() {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return null;
	}

	@Override
	public Response alterar(long id, Pessoa pessoa) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return null;
	}

	@Override
	public Response deletar(long id) {
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
		return null;
	}

}
