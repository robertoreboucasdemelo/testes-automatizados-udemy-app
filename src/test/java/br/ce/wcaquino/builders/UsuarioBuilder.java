package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {
	}
	
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Roberto Melo");
		return builder;
	}
	
	public Usuario build() {
		return usuario;
	}

}
