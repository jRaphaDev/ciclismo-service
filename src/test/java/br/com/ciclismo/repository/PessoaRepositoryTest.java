package br.com.ciclismo.repository;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

import br.com.ciclismo.model.Pessoa;

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
		pessoa.setNome("Fulando");
		pessoa.setSobreNome("Beutrano");
		pessoa.setCelular("(XX) XXXXX-XXXX");
		pessoa.setCpf("XXX.XXX.XXX-XX");
		pessoa.setEmail("email@test.com");
		pessoa.setDisponivel(true);
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
	
	

}
