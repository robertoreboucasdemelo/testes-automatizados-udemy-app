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
import org.mockito.Mockito;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.daos.LocacaoDAO;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {
	
	private LocacaoService service;
	
	private SPCService spcService;
	
	private LocacaoDAO dao;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String cenario;
	
	private static Filme filme01 = FilmeBuilder.umFilme().build();
	private static Filme filme02 = FilmeBuilder.umFilme().build();
	private static Filme filme03 = FilmeBuilder.umFilme().build();
	private static Filme filme04 = FilmeBuilder.umFilme().build();
	private static Filme filme05 = FilmeBuilder.umFilme().build();
	private static Filme filme06 = FilmeBuilder.umFilme().build();
	
	@Before
	public void setup() {
		service = new LocacaoService();
		service = new LocacaoService();
		dao = Mockito.mock(LocacaoDAO.class);
		service.setLocacaoDAO(dao);
		spcService = Mockito.mock(SPCService.class);
		service.setSPCService(spcService);
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
		Usuario usuario = UsuarioBuilder.umUsuario().build();
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		Assert.assertEquals(valorLocacao,resultado.getValor());	
		
	}

}
