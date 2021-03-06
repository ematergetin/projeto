package br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural;

import static br.gov.df.emater.aterwebsrv.modelo.UtilitarioInfoBasica.infoBasicaList;
import static br.gov.df.emater.aterwebsrv.modelo.UtilitarioInfoBasica.infoBasicaReg;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo.InfoBasica;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo.indice_producao.BemClassificado;
import br.gov.df.emater.aterwebsrv.modelo.indice_producao.UnidadeMedida;
import br.gov.df.emater.aterwebsrv.rest.json.JsonFormatarBigDecimal;

@Entity
@Table(name = "custo_producao", schema = EntidadeBase.CREDITO_RURAL_SCHEMA)
public class CustoProducao extends EntidadeBase implements _ChavePrimaria<Integer>, InfoBasica<CustoProducao> {

	private static final long serialVersionUID = 1L;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal area;

	@ManyToOne
	@JoinColumn(name = "bem_classificado_id")
	private BemClassificado bemClassificado;

	@OneToMany(mappedBy = "custoProducao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<CustoProducaoFormaProducao> formaProducaoList;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@OneToMany(mappedBy = "custoProducao")
	private List<CustoProducaoItem> itens;

	@Column(name = "nome_forma_producao")
	private String nomeFormaProducao;

	@NumberFormat(style = Style.NUMBER)
	@JsonDeserialize(using = JsonFormatarBigDecimal.class)
	private BigDecimal produtividade;

	@ManyToOne
	@JoinColumn(name = "unidade_medida_id")
	private UnidadeMedida unidadeMedida;

	public CustoProducao() {
		super();
	}

	public CustoProducao(Integer id) {
		super(id);
	}

	public CustoProducao(Integer id, String nomeFormaProducao, BemClassificado bemClassificado, BigDecimal area, BigDecimal produtividade, UnidadeMedida unidadeMedida, List<CustoProducaoItem> itens) {
		super();
		this.id = id;
		this.nomeFormaProducao = nomeFormaProducao;
		this.bemClassificado = bemClassificado;
		this.area = area;
		this.produtividade = produtividade;
		this.unidadeMedida = unidadeMedida;
		this.itens = itens;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustoProducao other = (CustoProducao) obj;
		if (bemClassificado == null) {
			if (other.bemClassificado != null)
				return false;
		} else if (!bemClassificado.equals(other.bemClassificado))
			return false;
		if (formaProducaoList == null) {
			if (other.formaProducaoList != null)
				return false;
		} else if (!formaProducaoList.equals(other.formaProducaoList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomeFormaProducao == null) {
			if (other.nomeFormaProducao != null)
				return false;
		} else if (!nomeFormaProducao.equals(other.nomeFormaProducao))
			return false;
		return true;
	}

	public BigDecimal getArea() {
		return area;
	}

	public BemClassificado getBemClassificado() {
		return bemClassificado;
	}

	public List<CustoProducaoFormaProducao> getFormaProducaoList() {
		return formaProducaoList;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public List<CustoProducaoItem> getItens() {
		return itens;
	}

	public String getNomeFormaProducao() {
		return nomeFormaProducao;
	}

	public BigDecimal getProdutividade() {
		return produtividade;
	}

	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bemClassificado == null) ? 0 : bemClassificado.hashCode());
		result = prime * result + ((formaProducaoList == null) ? 0 : formaProducaoList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomeFormaProducao == null) ? 0 : nomeFormaProducao.hashCode());
		return result;
	}

	public CustoProducao infoBasica() {
		return new CustoProducao(this.id, this.nomeFormaProducao, infoBasicaReg(this.bemClassificado), this.area, this.produtividade, infoBasicaReg(this.unidadeMedida), infoBasicaList(this.itens));
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public void setBemClassificado(BemClassificado bemClassificado) {
		this.bemClassificado = bemClassificado;
	}

	public void setFormaProducaoList(List<CustoProducaoFormaProducao> formaProducaoList) {
		this.formaProducaoList = formaProducaoList;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setItens(List<CustoProducaoItem> itens) {
		this.itens = itens;
	}

	public void setNomeFormaProducao(String nomeFormaProducao) {
		this.nomeFormaProducao = nomeFormaProducao;
	}

	public void setProdutividade(BigDecimal produtividade) {
		this.produtividade = produtividade;
	}

	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

}