package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException  {
		
		Double valorLocacao = 0d;
		
		if(usuario == null) {
			throw new LocadoraException("Usuario Vazio");
		}
		
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme Vazio");
		}
		
		for (int i = 0; i< filmes.size(); i++) {
			
			Filme filme = filmes.get(i);
			
			if(filme.getEstoque() == 0) {
				// Solucao 01
				//throw new Exception("Filme Sem Estoque");
				throw new FilmeSemEstoqueException();
			}
			
			switch(i) {
			  case 2: valorLocacao += filme.getPrecoLocacao() * 0.75;
			    break;
			  case 3: valorLocacao += filme.getPrecoLocacao() * 0.5;
			    break;
			  case 4: valorLocacao += filme.getPrecoLocacao() * 0.25;
				break;
			  case 5: valorLocacao += filme.getPrecoLocacao() * 0;
				break;
			  default:valorLocacao += filme.getPrecoLocacao();
			}
			
		}
		
			
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(valorLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		
		return locacao;
	}

	
	
}