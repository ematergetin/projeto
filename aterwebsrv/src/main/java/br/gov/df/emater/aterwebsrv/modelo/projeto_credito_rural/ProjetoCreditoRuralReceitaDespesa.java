package br.gov.df.emater.aterwebsrv.modelo.projeto_credito_rural;

import static br.gov.df.emater.aterwebsrv.modelo.UtilitarioInfoBasica.infoBasicaReg;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo.InfoBasica;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo.dominio.FluxoCaixaTipo;

@Entity
@Table(name = "projeto_credito_rural_receita_despesa", schema = EntidadeBase.CREDITO_RURAL_SCHEMA)
public class ProjetoCreditoRuralReceitaDespesa extends EntidadeBase implements _ChavePrimaria<Integer>, InfoBasica<ProjetoCreditoRuralReceitaDespesa> {

	private static final long serialVersionUID = 1L;

	private Integer ano;

	@Enumerated(EnumType.STRING)
	@Column(name = "arquivo_codigo")
	private ProjetoCreditoRuralArquivo.Codigo codigo;

	@ManyToOne
	@JoinColumn(name = "custo_producao_id")
	private CustoProducao custoProducao;

	private String descricao;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "projeto_credito_rural_id")
	private ProjetoCreditoRural projetoCreditoRural;

	@Column(name = "quantidade")
	private BigDecimal quantidade;

	@Enumerated(EnumType.STRING)
	private FluxoCaixaTipo tipo;

	private String unidade;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@Column(name = "valor_unitario")
	private BigDecimal valorUnitario;

	public ProjetoCreditoRuralReceitaDespesa() {
		super();
	}

	public ProjetoCreditoRuralReceitaDespesa(Integer id) {
		super(id);
	}

	public ProjetoCreditoRuralReceitaDespesa(Integer ano, String descricao, Integer id, BigDecimal quantidade, FluxoCaixaTipo tipo, String unidade, BigDecimal valorTotal, BigDecimal valorUnitario, CustoProducao custoProducao, ProjetoCreditoRuralArquivo.Codigo codigo) {
		super();
		this.ano = ano;
		this.descricao = descricao;
		this.id = id;
		this.quantidade = quantidade;
		this.tipo = tipo;
		this.unidade = unidade;
		this.valorTotal = valorTotal;
		this.valorUnitario = valorUnitario;
		this.custoProducao = custoProducao;
		this.codigo = codigo;
	}

	public Integer getAno() {
		return ano;
	}

	public ProjetoCreditoRuralArquivo.Codigo getCodigo() {
		return codigo;
	}

	public CustoProducao getCustoProducao() {
		return custoProducao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public ProjetoCreditoRural getProjetoCreditoRural() {
		return projetoCreditoRural;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public FluxoCaixaTipo getTipo() {
		return tipo;
	}

	public String getUnidade() {
		return unidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	@Override
	public ProjetoCreditoRuralReceitaDespesa infoBasica() {
		return new ProjetoCreditoRuralReceitaDespesa(this.ano, this.descricao, this.id, this.quantidade, this.tipo, this.unidade, this.valorTotal, this.valorUnitario, infoBasicaReg(this.custoProducao), this.codigo);
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public void setCodigo(ProjetoCreditoRuralArquivo.Codigo codigo) {
		this.codigo = codigo;
	}

	public void setCustoProducao(CustoProducao custoProducao) {
		this.custoProducao = custoProducao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjetoCreditoRural(ProjetoCreditoRural projetoCreditoRural) {
		this.projetoCreditoRural = projetoCreditoRural;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public void setTipo(FluxoCaixaTipo tipo) {
		this.tipo = tipo;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}