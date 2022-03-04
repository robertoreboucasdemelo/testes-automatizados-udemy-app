package br.ce.wcaquino.servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void teste() {
		
		// Booleanos
		Assert.assertTrue(true);	
		Assert.assertFalse(false);
		
		//int - long - String
		Assert.assertEquals(1, 1);
		
		// Negacao do assertEquals
		Assert.assertNotEquals(1, 2);
		
		//double - float - Colocar a Margem de Erro
		Assert.assertEquals(0.51, 0.51, 0.01);
		
		// primitivo e wrapper
		int i = 5;
		Integer x = 5;
		
		Assert.assertEquals(Integer.valueOf(i),x);
		Assert.assertEquals(i,x.intValue());
		
		//String
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));	
		Assert.assertTrue("bola".startsWith("bo"));	
		
		// Objeto - Implementar o metodo equals na classe a ser testatada
		
		Usuario usuario1 = new Usuario("Usuario 1");
		Usuario usuario2 = new Usuario("Usuario 1");
		
		Assert.assertEquals(usuario1, usuario2);
		
		// Verificar se são duas intancias iguais
		
		Assert.assertSame(usuario1, usuario1);
		
		// Verificar se são duas intancias sao diferentes
		
		Assert.assertNotSame(usuario1, usuario2);
		
		// Verificar Nulo
		
		Usuario usuario3 = null;	
		Assert.assertNull(usuario3);
		
		// Verificar Nulo Negacao
		Assert.assertNotNull(usuario1);
		
		// That - Assert generico - serve para todas as comparaçoes acima
		
		Assert.assertThat(1, CoreMatchers.is(1));
		Assert.assertThat(true, CoreMatchers.is(true));
		Assert.assertThat(true, CoreMatchers.not(false));
	}

}
