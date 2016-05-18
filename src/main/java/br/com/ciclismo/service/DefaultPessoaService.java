package br.com.ciclismo.service;

import java.net.URI;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.repository.PessoaRepository;
import br.com.ciclismo.util.FailureResponseBuilder;
import br.com.ciclismo.util.StatusException;

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
			Set<ConstraintViolation<Pessoa>> erros = validator.validate(pessoa);
			if (!erros.isEmpty()) {
				String error = ServiceUtil.getErrors(erros);
				logger.error(error);
				return new FailureResponseBuilder().toResponse(new StatusException(Status.BAD_REQUEST.getStatusCode(), error));
			}
			pessoa = pessoaRepository.criar(pessoa);
			logger.debug("Pessoa criada com sucesso");
			URI uri = uriInfo.getRequestUriBuilder().path(String.valueOf(pessoa.getId())).build();
			return Response.created(uri).entity(pessoa.getId()).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response achar(long id) {
		try {
			Pessoa pessoa = pessoaRepository.achar(id);
			if (pessoa == null) {
				logger.error("");
				return new FailureResponseBuilder().toResponse(new StatusException(Status.NOT_FOUND.getStatusCode(), ""));
			}
			logger.debug("");
			return Response.ok().entity(pessoa).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response listar() {
		try {
			List<Pessoa> list = pessoaRepository.listar();
			logger.debug("");
			return Response.ok().entity(list).build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response alterar(long id, Pessoa pessoa) {
		try {
			achar(id);
			Set<ConstraintViolation<Pessoa>> erros = validator.validate(pessoa);
			if (!erros.isEmpty()) {
				String error = ServiceUtil.getErrors(erros);
				logger.error(error);
				return new FailureResponseBuilder().toResponse(new StatusException(Status.BAD_REQUEST.getStatusCode(), error));
			}
			pessoaRepository.alterar(id, pessoa);
			logger.debug("");
			return Response.noContent().build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response deletar(long id) {
		try {
			achar(id);
			pessoaRepository.deletar(id);
			logger.debug("");
			return Response.noContent().build();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

}
