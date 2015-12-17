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
@Table(name = "forma_producao_valor", schema = EntidadeBase.INDICE_PRODUCAO_SCHEMA)
public class FormaProducaoValor extends EntidadeBase implements _ChavePrimaria<Integer> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "forma_producao_item_id")
	private FormaProducaoItem formaProducaoItem;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String nome;

	private Integer ordem;

	public FormaProducaoValor() {
		super();
	}

	public FormaProducaoValor(Integer id) {
		super(id);
	}

	public FormaProducaoItem getFormaProducaoItem() {
		return formaProducaoItem;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setFormaProducaoItem(FormaProducaoItem formaProducaoItem) {
		this.formaProducaoItem = formaProducaoItem;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

}