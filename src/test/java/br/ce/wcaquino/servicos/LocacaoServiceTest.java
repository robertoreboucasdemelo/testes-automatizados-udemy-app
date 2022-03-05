package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.DiaSemanaMatcher;
import br.ce.wcaquino.matchers.MatchersProprios;
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
	public void deveAlugarFilmeComSucesso() throws Exception {
		
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		//cenario
		
		Double locacaoFilme01 = 10.00;
		
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(10.00).build());
		
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
		
		// Error - Com Matchers Proprios
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDias(1));
		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
		
	}
	
	// Forma Elegante de Tratar a Exception
	
	// Solucao 01
	//@Test(expected=Exception.class)
	@Test(expected=FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
		//cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		
		//Modo 01
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().build());
		
		//Modo 02
		//List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().build());
		
		//acao
		service.alugarFilme(usuario, filmes);
	
	}
	
//	// Forma Robusta de Tratar a Exception
//
//	@Test
//	public void testeLocacaoFilmeSemEstoqueException02() {
//		// cenario
//		Usuario usuario = UsuarioBuilder.umUsuario().build();
//		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().build());
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
//		Usuario usuario = UsuarioBuilder.umUsuario().build();
//		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().semEstoque().build());
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
	public void deveLancarExcecaoAoAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenario
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().build());

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
	public void deveLancarExcecaoAoAlugarFilmeSemFilme() throws FilmeSemEstoqueException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();

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
	public void deveLancarExcecaoAoAlugarFilmeSemFilmeForma02() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		
		exception.expect(LocadoraException.class);
 		exception.expectMessage("Filme Vazio");

		// acao		
		service.alugarFilme(usuario, null);
		
	}
	
	@Test
	public void devePagar75PorCentoNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Double valor75Desconto = 11.00;
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(new Filme("Titanic", 1, 4.00),
				new Filme("Star Wars", 1, 4.00),
				new Filme("Jurassic Park", 1, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valor75Desconto,resultado.getValor());	
		
	}
	
	@Test
	public void devePagar50PorCentoNoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Double valor50Desconto = 13.00;
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(new Filme("Titanic", 1, 4.00),
				new Filme("Star Wars", 1, 4.00),
				new Filme("Jurassic Park", 1, 4.00),
				new Filme("Avengers", 1, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valor50Desconto,resultado.getValor());	
		
	}
	
	@Test
	public void devePagar25PorCentoNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Double valor25Desconto = 14.00;
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(new Filme("Titanic", 1, 4.00),
				new Filme("Star Wars", 1, 4.00),
				new Filme("Jurassic Park", 1, 4.00),
				new Filme("Avengers", 1, 4.00),
				new Filme("Batman", 1, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valor25Desconto,resultado.getValor());	
		
	}
	
	@Test
	public void devePagar0NoSextoFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		Double valorDescontoTotal = 14.00;
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(new Filme("Titanic", 1, 4.00),
				new Filme("Star Wars", 1, 4.00),
				new Filme("Jurassic Park", 1, 4.00),
				new Filme("Avengers", 1, 4.00),
				new Filme("Batman", 1, 4.00),
				new Filme("Homem Aranha", 1, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valorDescontoTotal,resultado.getValor());	
		
	}
	
	@Test
	//@Ignore
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenario
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		List<Filme> filmes = Arrays.asList(new Filme("Titanic", 1, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(resultado.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);	
		
		// verificacao atraves de um Matcher criado
		
		Assert.assertThat(resultado.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		Assert.assertThat(resultado.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		Assert.assertThat(resultado.getDataRetorno(), MatchersProprios.caiNumaSegunda());
		
	}

}
