package br.com.wepdev.springbatch.dominio;

public enum TipoConta {
	PRATA, OURO, PLATINA, DIAMANTE, INVALIDA;

	/**
	 * double -> primitivo nao aceita valor null, e necessário transforma lo em Object Double
	 * @param faixaSalarial
	 * @return
	 */
	public static TipoConta fromFaixaSalarial(Double faixaSalarial) {

		if(faixaSalarial == null)
			return INVALIDA;

		if (faixaSalarial <= 3000)
			return PRATA;

		else if (faixaSalarial > 3000 && faixaSalarial <= 5000)
			return OURO;

		else if (faixaSalarial > 5000 && faixaSalarial <= 10000)
			return PLATINA;

		else
			return DIAMANTE;
	}
}
