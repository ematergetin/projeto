package br.gov.df.emater.aterwebsrv.modelo.indice_producao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo._LogInclusaoAlteracao;
import br.gov.df.emater.aterwebsrv.modelo.ater.PropriedadeRural;
import br.gov.df.emater.aterwebsrv.modelo.ater.PublicoAlvo;
import br.gov.df.emater.aterwebsrv.modelo.funcional.UnidadeOrganizacional;
import br.gov.df.emater.aterwebsrv.modelo.sistema.Usuario;
import br.gov.df.emater.aterwebsrv.rest.json.JsonDeserializerData;
import br.gov.df.emater.aterwebsrv.rest.json.JsonSerializerData;

@Entity
@Table(name = "producao_proprietario", schema = EntidadeBase.INDICE_PRODUCAO_SCHEMA)
public class ProducaoProprietario extends EntidadeBase implements _ChavePrimaria<Integer>, _LogInclusaoAlteracao {

	private static final long serialVersionUID = 1L;

	@Column(name = "alteracao_data", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar alteracaoData;

	@ManyToOne
	@JoinColumn(name = "alteracao_usuario_id")
	private Usuario alteracaoUsuario;

	private Integer ano;

	@ManyToOne
	@JoinColumn(name = "bem_classificado_id")
	private BemClassificado bemClassificado;

	@Column(name = "chave_sisater")
	private String chaveSisater;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "inclusao_data", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar inclusaoData;

	@ManyToOne
	@JoinColumn(name = "inclusao_usuario_id", updatable = false)
	private Usuario inclusaoUsuario;

	@OneToMany(mappedBy = "producaoProprietario")
	private List<Producao> producaoList;

	// esta propriedade do objeto é exclusiva para envio à camada de visão do
	// detalhamento, ou seja, os registros de producao dos produtores da unidade
	// organizacional. É usado como argumento de filtro os registros cuja
	// produçao dos produtores seja do mesmo ano, do mesmo produto e da mesma
	// unidade organizacional vinculada à comunidade da propriedade rural do
	// produtor
	@Transient
	private List<ProducaoProprietario> producaoProprietarioList;

	@ManyToOne
	@JoinColumn(name = "propriedade_rural_id")
	private PropriedadeRural propriedadeRural;

	@ManyToOne
	@JoinColumn(name = "publico_alvo_id")
	private PublicoAlvo publicoAlvo;

	@ManyToOne
	@JoinColumn(name = "unidade_organizacional_id")
	private UnidadeOrganizacional unidadeOrganizacional;

	public ProducaoProprietario() {
		super();
	}

	public ProducaoProprietario(Integer id) {
		super(id);
	}

	public ProducaoProprietario(ProducaoProprietario producaoProprietario) {
		this.ano = producaoProprietario.getAno();
		this.bemClassificado = producaoProprietario.getBemClassificado();
		this.propriedadeRural = producaoProprietario.getPropriedadeRural();
		this.publicoAlvo = producaoProprietario.getPublicoAlvo();
		this.unidadeOrganizacional = producaoProprietario.getUnidadeOrganizacional();
		if (!CollectionUtils.isEmpty(producaoProprietario.getProducaoList())) {
			if (this.producaoList == null) {
				this.producaoList = new ArrayList<>();
			}
			producaoProprietario.getProducaoList().forEach((p) -> this.producaoList.add(new Producao(p)));
		}
	}

	@Override
	public Calendar getAlteracaoData() {
		return alteracaoData;
	}

	@Override
	public Usuario getAlteracaoUsuario() {
		return alteracaoUsuario;
	}

	public Integer getAno() {
		return ano;
	}

	public BemClassificado getBemClassificado() {
		return bemClassificado;
	}

	public String getChaveSisater() {
		return chaveSisater;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Calendar getInclusaoData() {
		return inclusaoData;
	}

	@Override
	public Usuario getInclusaoUsuario() {
		return inclusaoUsuario;
	}

	public List<Producao> getProducaoList() {
		return producaoList;
	}

	public List<ProducaoProprietario> getProducaoProprietarioList() {
		return producaoProprietarioList;
	}

	public PropriedadeRural getPropriedadeRural() {
		return propriedadeRural;
	}

	public PublicoAlvo getPublicoAlvo() {
		return publicoAlvo;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional() {
		return unidadeOrganizacional;
	}

	@Override
	public void setAlteracaoData(Calendar alteracaoData) {
		this.alteracaoData = alteracaoData;
	}

	@Override
	public void setAlteracaoUsuario(Usuario alteracaoUsuario) {
		this.alteracaoUsuario = alteracaoUsuario;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public void setBemClassificado(BemClassificado bemClassificado) {
		this.bemClassificado = bemClassificado;
	}

	public void setChaveSisater(String chaveSisater) {
		this.chaveSisater = chaveSisater;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setInclusaoData(Calendar inclusaoData) {
		this.inclusaoData = inclusaoData;
	}

	@Override
	public void setInclusaoUsuario(Usuario inclusaoUsuario) {
		this.inclusaoUsuario = inclusaoUsuario;
	}

	public void setProducaoList(List<Producao> producaoList) {
		this.producaoList = producaoList;
	}

	public void setProducaoProprietarioList(List<ProducaoProprietario> producaoProprietarioList) {
		this.producaoProprietarioList = producaoProprietarioList;
	}

	public void setPropriedadeRural(PropriedadeRural propriedadeRural) {
		this.propriedadeRural = propriedadeRural;
	}

	public void setPublicoAlvo(PublicoAlvo publicoAlvo) {
		this.publicoAlvo = publicoAlvo;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
		this.unidadeOrganizacional = unidadeOrganizacional;
	}

}