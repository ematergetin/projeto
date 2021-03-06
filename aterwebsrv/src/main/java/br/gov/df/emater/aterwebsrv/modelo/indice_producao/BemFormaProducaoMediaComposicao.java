package br.gov.df.emater.aterwebsrv.modelo.indice_producao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;

@Entity
@Table(name = "bem_forma_producao_media_composicao", schema = EntidadeBase.INDICE_PRODUCAO_SCHEMA)
public class BemFormaProducaoMediaComposicao extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "bem_forma_producao_media_id")
	private BemFormaProducaoMedia bemFormaProducaoMedia;

	@ManyToOne
	@JoinColumn(name = "forma_producao_valor_id")
	private FormaProducaoValor formaProducaoValor;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public BemFormaProducaoMedia getBemFormaProducaoMedia() {
		return bemFormaProducaoMedia;
	}

	public FormaProducaoValor getFormaProducaoValor() {
		return formaProducaoValor;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setBemFormaProducaoMedia(BemFormaProducaoMedia bemFormaProducaoMedia) {
		this.bemFormaProducaoMedia = bemFormaProducaoMedia;
	}

	public void setFormaProducaoValor(FormaProducaoValor formaProducaoValor) {
		this.formaProducaoValor = formaProducaoValor;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}