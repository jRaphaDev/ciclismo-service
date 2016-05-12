package br.com.ciclismo.repository;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.util.StatusException;

public class PessoaRepositoryTest {

	private Pessoa pessoa;
	private static EntityManager entityManager;
	private static DefaultPessoaRepository defaultPessoaRepository;

	@BeforeClass
	public static void mockarClasses() {
		entityManager = mock(EntityManager.class);
		defaultPessoaRepository = new DefaultPessoaRepository();
		defaultPessoaRepository.entityManager = entityManager;
	}

	@Before
	public void popularPessoa() {
		pessoa = new Pessoa();
		pessoa.setId(1);
		pessoa.setNome("Fulando");
		pessoa.setSobreNome("Beutrano");
		pessoa.setCelular("(XX) XXXXX-XXXX");
		pessoa.setCpf("XXX.XXX.XXX-XX");
		pessoa.setEmail("email@test.com");
		pessoa.setDisponivel(true);
		pessoa.setSenha("Teste md5");
	}

	@Test
	public void testeCriacao() {
		Pessoa pessoa2 = defaultPessoaRepository.criar(pessoa);
		verify(entityManager).persist(pessoa);
		assertNotNull(pessoa2.getId());
	}
	
	@Test(expected=Exception.class)
	public void testeCriacaoException() {
		when(defaultPessoaRepository.criar(pessoa)).thenThrow(new Exception());
		defaultPessoaRepository.criar(pessoa);
	}
	
	@Test
	public void testeAcharPessoa() throws Exception {
		when(entityManager.find(Pessoa.class, pessoa.getId())).thenReturn(pessoa);
		Pessoa pessoa2 = defaultPessoaRepository.achar(pessoa.getId());
		assertNotNull(pessoa2);
		assertEquals(pessoa2, pessoa);
	}
	
	@Test(expected=StatusException.class)
	public void testAcharNaoEncontrado() throws Exception {
		when(entityManager.find(Pessoa.class, 2)).thenReturn(null);
		defaultPessoaRepository.achar(2);
	}
	
	@Test(expected=Exception.class)
	public void testAcharExcecao() throws Exception {
		when(entityManager.find(Pessoa.class, 3)).thenThrow(new Exception());
		defaultPessoaRepository.achar(3);
	}
	
	@Test
	public void testeListar() {
		Query query = mock(Query.class);
		when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		List<Pessoa> lista = new ArrayList<>();
		lista.add(pessoa);
		when(query.getResultList()).thenReturn(lista);
		List<Pessoa> retorno = defaultPessoaRepository.listar();
		assertNotNull(retorno);
		assertTrue(!retorno.isEmpty());
	}
	
	@Test
	public void testeListaVazia() {
		Query query = mock(Query.class);
		when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		List<Pessoa> lista = new ArrayList<>();
		when(query.getResultList()).thenReturn(lista);
		List<Pessoa> retorno = defaultPessoaRepository.listar();
		assertNotNull(retorno);
		assertTrue(retorno.isEmpty());
	}
	
	@Test(expected=Exception.class)
	public void testeListarExecao() {
		when(entityManager.createQuery(Matchers.anyString())).thenThrow(new Exception());
		defaultPessoaRepository.listar();
	}
	
	@Test
	public void testeAlterar() throws Exception {
		when(entityManager.find(Pessoa.class, pessoa.getId())).thenReturn(pessoa);
		defaultPessoaRepository.alterar(pessoa.getId(), pessoa);
	}
	
	@Test(expected=StatusException.class)
	public void testeAlterarNaoEncontrado() throws Exception {
		when(entityManager.find(Pessoa.class, 2)).thenReturn(null);
		defaultPessoaRepository.alterar(2, pessoa);
	}
	
	@Test(expected=Exception.class)
	public void testeAlterarExcecao() throws Exception {
		when(entityManager.find(Pessoa.class, pessoa.getId())).thenThrow(new Exception());
		defaultPessoaRepository.alterar(pessoa.getId(), pessoa);
	}
	
}
