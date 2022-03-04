package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	private LocacaoService service;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	private static Filme filme01 = new Filme("Titanic", 1, 4.00);
	private static Filme filme02 = new Filme("Star Wars", 1, 4.00);
	private static Filme filme03 = new Filme("Jurassic Park", 1, 4.00);
	private static Filme filme04 = new Filme("Avengers", 1, 4.00);
	private static Filme filme05 = new Filme("Batman", 1, 4.00);
	private static Filme filme06 = new Filme("Homem Aranha", 1, 4.00);
	
	@Before
	public void setup() {
		service = new LocacaoService();
	}
	
	@Parameters(name="Teste {index} = {2}")
	public static Collection<Object[]> getParametros(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme01,filme02,filme03), 11.00, "Terceiro Filme: 25% de Desconto"},
			{Arrays.asList(filme01,filme02,filme03, filme04), 13.00, "Quarto Filme: 50% de Desconto"},
			{Arrays.asList(filme01,filme02,filme03, filme04, filme05), 14.00, "Quinto Filme: 75% de Desconto"},
			{Arrays.asList(filme01,filme02,filme03, filme04, filme05, filme06), 14.00, "Sexto Filme: 100% de Desconto"}
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Roberto Melo");
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valorLocacao,resultado.getValor());	
		
	}

}
