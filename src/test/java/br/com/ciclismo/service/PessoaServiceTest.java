package br.com.ciclismo.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.impl.UriBuilderImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.repository.PessoaRepository;

public class PessoaServiceTest {

	private static PessoaRepository pessoaRepository;
	private static PessoaService pessoaService;
	private Pessoa pessoa;
	
	@BeforeClass
	public static void mockarClasses() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
		pessoaRepository = mock(PessoaRepository.class);
		pessoaService = new DefaultPessoaService(validator, pessoaRepository);
	}
	
	@Before
	public void popularPessoa() {
		pessoa = new Pessoa();
		pessoa.setId(1);
		pessoa.setNome("Fulando");
		pessoa.setLogin("Login");
		pessoa.setSobreNome("Beutrano");
		pessoa.setCelular("(XX) XXXXX-XXXX");
		pessoa.setCpf("384.211.928-33");
		pessoa.setEmail("email@test.com");
		pessoa.setDisponivel(true);
		pessoa.setSenha("Teste md5");
	}
	
	@Test
	public void testeCriar() throws Exception {
		UriInfo uriInfo = mock(UriInfo.class);
		when(uriInfo.getRequestUriBuilder()).thenReturn(new UriBuilderImpl(new URI(Matchers.anyString())));
		when(pessoaRepository.criar(pessoa)).thenReturn(pessoa);
		Response response = pessoaService.criar(uriInfo, pessoa);
		assertEquals(201, response.getStatus());
	}

	@Test
	public void testeAchar() throws Exception {
		when(pessoaRepository.achar(pessoa.getId())).thenReturn(pessoa);
		Response response = pessoaService.achar(pessoa.getId());
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void testeListagem() {
		List<Pessoa> list = new ArrayList<>();
		list.add(pessoa);
		when(pessoaRepository.listar()).thenReturn(list);
		Response response = pessoaService.listar();
		assertEquals(200, response.getStatus());
	}

	@Test
	public void testeAlterar() throws Exception {
		when(pessoaRepository.achar(pessoa.getId())).thenReturn(pessoa);
		Response response = pessoaService.alterar(pessoa.getId(), pessoa);
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void testeDeletar() throws Exception {
		when(pessoaRepository.achar(pessoa.getId())).thenReturn(pessoa);
		Response response = pessoaService.deletar(pessoa.getId());
		assertEquals(204, response.getStatus());
	}
}
