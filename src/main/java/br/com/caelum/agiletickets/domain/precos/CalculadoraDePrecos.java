package br.com.caelum.agiletickets.domain.precos;

import java.math.BigDecimal;

import br.com.caelum.agiletickets.models.Sessao;
import br.com.caelum.agiletickets.models.TipoDeEspetaculo;

public class CalculadoraDePrecos {

	public static BigDecimal calcula(Sessao sessao, Integer quantidade) {
		BigDecimal preco;
		
		TipoDeEspetaculo tipoEspetaculo = sessao.getEspetaculo().getTipo();
		preco = calculaPreco(sessao, tipoEspetaculo);
		return preco.multiply(BigDecimal.valueOf(quantidade));
	}
	private static BigDecimal calculaPreco(Sessao sessao, TipoDeEspetaculo tipoEspetaculo) {
		BigDecimal preco;
		switch (tipoEspetaculo) {
			case CINEMA: case SHOW:
				preco = calculaParaCinemaShow(sessao);
				break;
			case BALLET: case ORQUESTRA:
				preco = calculaParaBalletOrquestra(sessao);
				break;
			default:
				preco = sessao.getPreco();
		}
		return preco;
	}
	private static BigDecimal calculaParaBalletOrquestra(Sessao sessao) {
		BigDecimal preco;
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.50) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.20)));
		} else {
			preco = sessao.getPreco();
		}
		
		if(sessao.getDuracaoEmMinutos() > 60){
			preco = preco.add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		}
		return preco;
	}
	private static BigDecimal calculaParaCinemaShow(Sessao sessao) {
		BigDecimal preco;
		//quando estiver acabando os ingressos... 
		if((sessao.getTotalIngressos() - sessao.getIngressosReservados()) / sessao.getTotalIngressos().doubleValue() <= 0.05) { 
			preco = sessao.getPreco().add(sessao.getPreco().multiply(BigDecimal.valueOf(0.10)));
		} else {
			preco = sessao.getPreco();
		}
		return preco;
	}

}