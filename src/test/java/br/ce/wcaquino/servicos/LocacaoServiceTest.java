package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	//private static Integer contador = 0;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		//System.out.println("Before");
		//contador++;
		//System.out.println("Quantidade de Passagens: " + contador);
	}
	
	@After
	public void finish() {
		//System.out.println("After");
	}
	
	@BeforeClass
	public static void setupClass() {
		//System.out.println("BeforeClass");
	}
	
	@AfterClass
	public static void finishClass() {
		//System.out.println("AfterClass");
	}

	@Test
	public void testeLocacao() throws Exception {
		//cenario
		
		Double locacaoFilme01 = 10.00;
		
		Usuario usuario = new Usuario("Roberto Melo");
		List<Filme> filmes = Arrays.asList(new Filme("Poderoso Chefão", 2, 10.00));
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(locacao.getValor(),locacaoFilme01, 0.01);	
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		
		Assert.assertThat(locacaoFilme01, CoreMatchers.is(locacao.getValor()));
		
		
		// Error - Verifica todas as Falhas, não para na primeira
		error.checkThat(locacaoFilme01, CoreMatchers.is(locacao.getValor()));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), 
				CoreMatchers.is(true));
	}
	
	// Forma Elegante de Tratar a Exception
	
	// Solucao 01
	//@Test(expected=Exception.class)
	@Test(expected=FilmeSemEstoqueException.class)
	public void testeLocacaoFilmeSemEstoqueException01() throws Exception {
		//cenario
		Usuario usuario = new Usuario("Roberto Melo");
		List<Filme> filmes = Arrays.asList(new Filme("Poderoso Chefão", 0, 10.00));
		
		//acao
		service.alugarFilme(usuario, filmes);
	
	}
	
//	// Forma Robusta de Tratar a Exception
//
//	@Test
//	public void testeLocacaoFilmeSemEstoqueException02() {
//		// cenario
//		Usuario usuario = new Usuario("Roberto Melo");
//		List<Filme> filmes = Arrays.asList(new Filme("Poderoso Chefão", 0, 10.00));
//
//		// acao
//		try {
//			service.alugarFilme(usuario, filmes);
//			Assert.fail("Deveria Ter Lançado uma Excecao");
//		} catch (Exception e) {
//			Assert.assertThat(e.getMessage(),CoreMatchers.is("Filme Sem Estoque"));
//		}
//
//	}
//	
//	// Nova Forma de Tratar a Exception
//
//	@Test
//	public void testeLocacaoFilmeSemEstoqueException03() throws Exception {
//		//cenario
//		Usuario usuario = new Usuario("Roberto Melo");
//		List<Filme> filmes = Arrays.asList(new Filme("Poderoso Chefão", 0, 10.00));
//		
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme Sem Estoque");
//		
//		//acao
//		service.alugarFilme(usuario, filmes);
//		
//		
//	
//	}
	
	 // Robusta
	
	@Test
	public void testeLocacaoUsuarioVazioException() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(new Filme("Poderoso Chefão", 1, 10.00));

		// acao
		
		try {
			service.alugarFilme(null, filmes);
			Assert.fail("Deveria Ter Lançado uma Excecao");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(),CoreMatchers.is("Usuario Vazio"));
		}
	
	}
	
	// Robusta
	
	@Test
	public void testeLocacaoFilmeVazioException() throws FilmeSemEstoqueException {
		// cenario
		Usuario usuario = new Usuario("Roberto Melo");

		// acao
		
		try {
			service.alugarFilme(usuario, null);
			Assert.fail("Deveria Ter Lançado uma Excecao");
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(),CoreMatchers.is("Filme Vazio"));
		}
	}
	
	// Nova Forma
	
	@Test
	public void testeLocacaoFilmeVazioException02() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Roberto Melo");
		
		exception.expect(LocadoraException.class);
 		exception.expectMessage("Filme Vazio");

		// acao		
		service.alugarFilme(usuario, null);
		
	}

}
