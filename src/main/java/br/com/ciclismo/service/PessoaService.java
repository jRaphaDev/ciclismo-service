package br.com.ciclismo.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import br.com.ciclismo.model.Pessoa;

@Path("/pessoa")
public interface PessoaService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response criar(@Context UriInfo uriInfo, Pessoa pessoa);
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response achar(@PathParam("id") long id);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response listar();
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response alterar(@PathParam("id") long id, Pessoa pessoa);
	
	@DELETE
	@Path("/{id}")
	Response deletar(@PathParam("id") long id);
}
