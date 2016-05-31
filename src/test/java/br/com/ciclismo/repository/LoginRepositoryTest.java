package br.com.ciclismo.repository;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

import br.com.ciclismo.model.Pessoa;
import br.com.ciclismo.util.StatusException;

public class LoginRepositoryTest {

	private Pessoa pessoa;
	private static EntityManager entityManager;
	private static DefaultLoginRepository defaultLoginRepository;

	@BeforeClass
	public static void mockarClasses() {
		entityManager = mock(EntityManager.class);
		defaultLoginRepository = new DefaultLoginRepository();
		defaultLoginRepository.entityManager = entityManager;
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
	public void loginTesteVerdadeiro() throws Exception {
		Query query = mock(Query.class);
		when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(pessoa);
		Pessoa pessoaR = defaultLoginRepository.login("Fulando", "Teste md5");
		assertEquals(pessoa, pessoaR);
	}
	
	@Test(expected=StatusException.class)
	public void loginTesteFalso() throws Exception {
		Query query = mock(Query.class);
		when(entityManager.createQuery(Matchers.anyString())).thenReturn(query);
		when(query.getSingleResult()).thenReturn(null);
		defaultLoginRepository.login("Fulando 2", "Teste md5 2");
	}
}
