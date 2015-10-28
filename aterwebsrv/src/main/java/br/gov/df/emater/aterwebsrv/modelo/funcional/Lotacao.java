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

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.gov.df.emater.aterwebsrv.modelo.EntidadeBase;
import br.gov.df.emater.aterwebsrv.modelo._ChavePrimaria;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.rest.json.JsonDeserializerData;
import br.gov.df.emater.aterwebsrv.rest.json.JsonSerializerData;

@Entity
@Table(name = "lotacao", schema = EntidadeBase.FUNCIONAL_SCHEMA)
public class Lotacao extends EntidadeBase implements _ChavePrimaria<Integer> {

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

	public Lotacao() {
	}

	public Emprego getEmprego() {
		return emprego;
	}

	public Confirmacao getGestor() {
		return gestor;
	}

	public Integer getId() {
		return id;
	}

	public Calendar getInicio() {
		return inicio;
	}

	public Confirmacao getTemporario() {
		return temporario;
	}

	public Calendar getTermino() {
		return termino;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional() {
		return unidadeOrganizacional;
	}

	public void setEmprego(Emprego emprego) {
		this.emprego = emprego;
	}

	public void setGestor(Confirmacao gestor) {
		this.gestor = gestor;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}

	public void setTemporario(Confirmacao temporario) {
		this.temporario = temporario;
	}

	public void setTermino(Calendar termino) {
		this.termino = termino;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional) {
		this.unidadeOrganizacional = unidadeOrganizacional;
	}

}