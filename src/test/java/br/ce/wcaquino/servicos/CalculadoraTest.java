package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NãoPodeDividirPorZeroException;

public class CalculadoraTest {
	
	private Calculadora calculadora;
	
	@Before
	public void setup() {
		calculadora = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		// cenario
		int x = 5;
		int y = 3;

		//acao
		int resultado = calculadora.somar(x,y);
		
		//verificacao
		Assert.assertEquals(8, resultado);
		
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		// cenario
		int x = 8;
		int y = 5;
	
		//acao
		int resultado = calculadora.subtrair(x,y);
		
		//verificacao
		Assert.assertEquals(3, resultado);
		
	}
	
	@Test
	public void deveDividirDoisValores() throws NãoPodeDividirPorZeroException {
		// cenario
		int x = 6;
		int y = 3;
		
		//acao
		int resultado = calculadora.dividir(x,y);
		
		//verificacao
		Assert.assertEquals(2, resultado);
		
	}
	
	@Test(expected= NãoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NãoPodeDividirPorZeroException {
		// cenario
		int x = 10;
		int y = 0;
	
		//acao
		int resultado = calculadora.dividir(x,y);
		
	}

}
