package br.gov.df.emater.aterwebsrv.modelo.funcional;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Immutable;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.rest.json.JsonDeserializerData;
import br.gov.df.emater.aterwebsrv.rest.json.JsonSerializerData;

/*
 * ATENÇÃO! Qualquer alteração na estrutura desta classe também deve ser feita na interface LotacaoBase 
 */
@Entity
@Table(name = "lotacao_ativa_vi", schema = EntidadeBase.FUNCIONAL_SCHEMA)
@Immutable
public class LotacaoAtivaVi extends EntidadeBase implements _ChavePrimaria<Integer>, LotacaoBase {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "emprego_id")
	private Emprego emprego;

	@Enumerated(EnumType.STRING)
	private Confirmacao gestor;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar inicio;

	@Enumerated(EnumType.STRING)
	private Confirmacao temporario;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = JsonSerializerData.class)
	@JsonDeserialize(using = JsonDeserializerData.class)
	private Calendar termino;

	@ManyToOne
	@JoinColumn(name = "unidade_organizacional_id")
	private UnidadeOrganizacional unidadeOrganizacional;

	public LotacaoAtivaVi() {
	}

	@Override
	public Emprego getEmprego() {
		return emprego;
	}

	@Override
	public Confirmacao getGestor() {
		return gestor;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Calendar getInicio() {
		return inicio;
	}

	@Override
	public Confirmacao getTemporario() {
		return temporario;
	}

	@Override
	public Calendar getTermino() {
		return termino;
	}

	@Override
	public UnidadeOrganizacional getUnidadeOrganizacional() {
		return unidadeOrganizacional;
	}

	@Override
	public void setEmprego(Emprego emprego) {
		this.emprego = emprego;
	}

	@Override
	public void setGestor(Confirmacao gestor) {
		this.gestor = gestor;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	@Override
	public void setTemporario(Confirmacao temporario) {
		this.temporario = temporario;
	}

	@Override
	public void setTermino(Calendar termino) {
		this.termino = termino;
	}

	@Override
	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
		this.unidadeOrganizacional = unidadeOrganizacional;
	}

}