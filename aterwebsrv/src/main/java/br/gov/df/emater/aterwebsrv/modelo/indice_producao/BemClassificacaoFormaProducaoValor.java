package br.gov.df.emater.aterwebsrv.modelo.indice_producao;

import static br.gov.df.emater.aterwebsrv.modelo.UtilitarioInfoBasica.infoBasicaReg;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo.InfoBasica;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;

@Entity
@Table(name = "bem_classificacao_forma_producao_valor", schema = EntidadeBase.INDICE_PRODUCAO_SCHEMA)
public class BemClassificacaoFormaProducaoValor extends EntidadeBase implements _ChavePrimaria<Integer>, InfoBasica<BemClassificacaoFormaProducaoValor> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "bem_classificacao_id")
	private BemClassificacao bemClassificacao;

	@ManyToOne
	@JoinColumn(name = "forma_producao_valor_id")
	private FormaProducaoValor formaProducaoValor;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private Integer ordem;

	public BemClassificacaoFormaProducaoValor() {
		super();
	}

	public BemClassificacaoFormaProducaoValor(Integer id, BemClassificacao bemClassificacao, FormaProducaoValor formaProducaoValor, Integer ordem) {
		super();
		this.id = id;
		this.bemClassificacao = bemClassificacao;
		this.formaProducaoValor = formaProducaoValor;
		this.ordem = ordem;
	}

	public BemClassificacao getBemClassificacao() {
		return bemClassificacao;
	}

	public FormaProducaoValor getFormaProducaoValor() {
		return formaProducaoValor;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public Integer getOrdem() {
		return ordem;
	}

	@Override
	public BemClassificacaoFormaProducaoValor infoBasica() {
		return new BemClassificacaoFormaProducaoValor(this.id, new BemClassificacao(this.bemClassificacao == null ? null : this.bemClassificacao.getId()), infoBasicaReg(this.formaProducaoValor), this.ordem);
	}

	public void setBemClassificacao(BemClassificacao bemClassificacao) {
		this.bemClassificacao = bemClassificacao;
	}

	public void setFormaProducaoValor(FormaProducaoValor formaProducaoValor) {
		this.formaProducaoValor = formaProducaoValor;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

}