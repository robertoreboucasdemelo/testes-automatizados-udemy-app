package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;


public class OrdemExecucaoForma01Test {
	
	public static int contador = 0;
	
	public void inicia() {
		contador = 1;
	}
	
	public void verifica() {
		Assert.assertEquals(1, contador);
	}
	
	@Test
	public void executaTeste() {
		this.inicia();
		this.verifica();
	}
	
	

}
