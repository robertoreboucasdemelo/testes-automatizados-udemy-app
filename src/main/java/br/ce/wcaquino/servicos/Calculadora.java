package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NãoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int x, int y) {
		return x+y;
	}

	public int subtrair(int x, int y) {
		return x-y;
	}

	public int dividir(int x, int y) throws NãoPodeDividirPorZeroException {
		if (x == 0 || y == 0) {
			throw new NãoPodeDividirPorZeroException();
		}
		return x/y;
	}

}
