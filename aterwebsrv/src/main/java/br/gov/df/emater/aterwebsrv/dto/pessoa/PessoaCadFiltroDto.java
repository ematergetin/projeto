package br.gov.df.emater.aterwebsrv.dto.pessoa;

import java.util.List;
import java.util.Set;

import br.gov.df.emater.aterwebsrv.dto.CadFiltroDtoCustom;
import br.gov.df.emater.aterwebsrv.dto.TagDto;
import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioString;
import br.gov.df.emater.aterwebsrv.modelo.ater.Comunidade;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.modelo.dominio.ConfirmacaoDap;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PendenciaTipo;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaGenero;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaGeracao;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaSituacao;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaTipo;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PropriedadeUtilizacaoEspacoRural;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PublicoAlvoCategoria;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PublicoAlvoSegmento;
import br.gov.df.emater.aterwebsrv.modelo.funcional.UnidadeOrganizacional;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.GrupoSocial;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.PessoaJuridica;

public class PessoaCadFiltroDto extends CadFiltroDtoCustom {

	private static final long serialVersionUID = 1L;

	private Set<TagDto> chaveSisaterList;

	private String cnpj;

	private List<Comunidade> comunidadeList;

	private String cpf;

	private List<PessoaJuridica> empresaList;

	private List<GrupoSocial> grupoSocialList;

	private Set<TagDto> nomeList;

	private Set<PendenciaTipo> pendencia;

	private Set<PessoaGenero> pessoaGenero;

	private Set<PessoaGeracao> pessoaGeracao;

	private Set<PessoaSituacao> pessoaSituacao;

	private Set<PessoaTipo> pessoaTipoList;

	private Set<Confirmacao> publicoAlvoAtepa;

	private Set<Confirmacao> publicoAlvoAter;

	private Set<Confirmacao> publicoAlvoBeneficioSocial;

	private Set<Confirmacao> publicoAlvoBsm;

	private Set<PublicoAlvoCategoria> publicoAlvoCategoria;

	private Set<Confirmacao> publicoAlvoComprasInstitucionais;

	private Set<ConfirmacaoDap> publicoAlvoDap;

	private Set<Confirmacao> publicoAlvoIncra;

	private Set<Confirmacao> publicoAlvoIpaAgricola;

	private Set<Confirmacao> publicoAlvoIpaAnimal;

	private Set<Confirmacao> publicoAlvoIpaFlores;

	private Set<Confirmacao> publicoAlvoIpaNaoAgricola;

	private Set<Confirmacao> publicoAlvoOrganizacao;

	private Integer publicoAlvoPropriedadeUtilizacaoEspacoRural;

	private Set<PublicoAlvoSegmento> publicoAlvoSegmento;

	private Integer publicoAlvoSetor;

	private Set<Confirmacao> publicoAlvoSustentabilidade;

	private List<UnidadeOrganizacional> unidadeOrganizacionalList;

	public Set<TagDto> getChaveSisaterList() {
		return chaveSisaterList;
	}

	public String getCnpj() {
		return cnpj;
	}

	public List<Comunidade> getComunidadeList() {
		return comunidadeList;
	}

	public String getCpf() {
		return cpf;
	}

	public List<PessoaJuridica> getEmpresaList() {
		return empresaList;
	}

	public List<GrupoSocial> getGrupoSocialList() {
		return grupoSocialList;
	}

	public Set<TagDto> getNomeList() {
		return nomeList;
	}

	public Set<PendenciaTipo> getPendencia() {
		return pendencia;
	}

	public Set<PessoaGenero> getPessoaGenero() {
		return pessoaGenero;
	}

	public Set<PessoaGeracao> getPessoaGeracao() {
		return pessoaGeracao;
	}

	public Set<PessoaSituacao> getPessoaSituacao() {
		return pessoaSituacao;
	}

	public Set<PessoaTipo> getPessoaTipoList() {
		return pessoaTipoList;
	}

	public Set<Confirmacao> getPublicoAlvoAtepa() {
		return publicoAlvoAtepa;
	}

	public Set<Confirmacao> getPublicoAlvoAter() {
		return publicoAlvoAter;
	}

	public Set<Confirmacao> getPublicoAlvoBeneficioSocial() {
		return publicoAlvoBeneficioSocial;
	}

	public Set<Confirmacao> getPublicoAlvoBsm() {
		return publicoAlvoBsm;
	}

	public Set<PublicoAlvoCategoria> getPublicoAlvoCategoria() {
		return publicoAlvoCategoria;
	}

	public Set<Confirmacao> getPublicoAlvoComprasInstitucionais() {
		return publicoAlvoComprasInstitucionais;
	}

	public Set<ConfirmacaoDap> getPublicoAlvoDap() {
		return publicoAlvoDap;
	}

	public Set<Confirmacao> getPublicoAlvoIncra() {
		return publicoAlvoIncra;
	}

	public Set<Confirmacao> getPublicoAlvoIpaAgricola() {
		return publicoAlvoIpaAgricola;
	}

	public Set<Confirmacao> getPublicoAlvoIpaAnimal() {
		return publicoAlvoIpaAnimal;
	}

	public Set<Confirmacao> getPublicoAlvoIpaFlores() {
		return publicoAlvoIpaFlores;
	}

	public Set<Confirmacao> getPublicoAlvoIpaNaoAgricola() {
		return publicoAlvoIpaNaoAgricola;
	}

	public Set<Confirmacao> getPublicoAlvoOrganizacao() {
		return publicoAlvoOrganizacao;
	}

	public Integer getPublicoAlvoPropriedadeUtilizacaoEspacoRural() {
		return publicoAlvoPropriedadeUtilizacaoEspacoRural;
	}

	public Set<PublicoAlvoSegmento> getPublicoAlvoSegmento() {
		return publicoAlvoSegmento;
	}

	public Integer getPublicoAlvoSetor() {
		return publicoAlvoSetor;
	}

	public Set<Confirmacao> getPublicoAlvoSustentabilidade() {
		return publicoAlvoSustentabilidade;
	}

	public List<UnidadeOrganizacional> getUnidadeOrganizacionalList() {
		return unidadeOrganizacionalList;
	}

	public void setChaveSisaterList(Set<TagDto> chaveSisaterList) {
		this.chaveSisaterList = chaveSisaterList;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = UtilitarioString.formataCnpj(cnpj);
	}

	public void setComunidadeList(List<Comunidade> comunidadeList) {
		this.comunidadeList = comunidadeList;
	}

	public void setCpf(String cpf) {
		this.cpf = UtilitarioString.formataCpf(cpf);
	}

	public void setEmpresaList(List<PessoaJuridica> empresaList) {
		this.empresaList = empresaList;
	}

	public void setGrupoSocialList(List<GrupoSocial> grupoSocialList) {
		this.grupoSocialList = grupoSocialList;
	}

	public void setNomeList(Set<TagDto> nomeList) {
		this.nomeList = nomeList;
	}

	public void setPendencia(Set<PendenciaTipo> pendencia) {
		this.pendencia = pendencia;
	}

	public void setPessoaGenero(Set<PessoaGenero> pessoaGenero) {
		this.pessoaGenero = pessoaGenero;
	}

	public void setPessoaGeracao(Set<PessoaGeracao> pessoaGeracao) {
		this.pessoaGeracao = pessoaGeracao;
	}

	public void setPessoaSituacao(Set<PessoaSituacao> pessoaSituacao) {
		this.pessoaSituacao = pessoaSituacao;
	}

	public void setPessoaTipoList(Set<PessoaTipo> pessoaTipoList) {
		this.pessoaTipoList = pessoaTipoList;
	}

	public void setPublicoAlvoAtepa(Set<Confirmacao> publicoAlvoAtepa) {
		this.publicoAlvoAtepa = publicoAlvoAtepa;
	}

	public void setPublicoAlvoAter(Set<Confirmacao> publicoAlvoAter) {
		this.publicoAlvoAter = publicoAlvoAter;
	}

	public void setPublicoAlvoBeneficioSocial(Set<Confirmacao> publicoAlvoBeneficioSocial) {
		this.publicoAlvoBeneficioSocial = publicoAlvoBeneficioSocial;
	}

	public void setPublicoAlvoBsm(Set<Confirmacao> publicoAlvoBsm) {
		this.publicoAlvoBsm = publicoAlvoBsm;
	}

	public void setPublicoAlvoCategoria(Set<PublicoAlvoCategoria> publicoAlvoCategoria) {
		this.publicoAlvoCategoria = publicoAlvoCategoria;
	}

	public void setPublicoAlvoComprasInstitucionais(Set<Confirmacao> publicoAlvoComprasInstitucionais) {
		this.publicoAlvoComprasInstitucionais = publicoAlvoComprasInstitucionais;
	}

	public void setPublicoAlvoDap(Set<ConfirmacaoDap> publicoAlvoDap) {
		this.publicoAlvoDap = publicoAlvoDap;
	}

	public void setPublicoAlvoIncra(Set<Confirmacao> publicoAlvoIncra) {
		this.publicoAlvoIncra = publicoAlvoIncra;
	}

	public void setPublicoAlvoIpaAgricola(Set<Confirmacao> publicoAlvoIpaAgricola) {
		this.publicoAlvoIpaAgricola = publicoAlvoIpaAgricola;
	}

	public void setPublicoAlvoIpaAnimal(Set<Confirmacao> publicoAlvoIpaAnimal) {
		this.publicoAlvoIpaAnimal = publicoAlvoIpaAnimal;
	}

	public void setPublicoAlvoIpaFlores(Set<Confirmacao> publicoAlvoIpaFlores) {
		this.publicoAlvoIpaFlores = publicoAlvoIpaFlores;
	}

	public void setPublicoAlvoIpaNaoAgricola(Set<Confirmacao> publicoAlvoIpaNaoAgricola) {
		this.publicoAlvoIpaNaoAgricola = publicoAlvoIpaNaoAgricola;
	}

	public void setPublicoAlvoOrganizacao(Set<Confirmacao> publicoAlvoOrganizacao) {
		this.publicoAlvoOrganizacao = publicoAlvoOrganizacao;
	}

	public void setPublicoAlvoPropriedadeUtilizacaoEspacoRural(Integer publicoAlvoPropriedadeUtilizacaoEspacoRural) {
		this.publicoAlvoPropriedadeUtilizacaoEspacoRural = publicoAlvoPropriedadeUtilizacaoEspacoRural;
	}

	public void setPublicoAlvoSegmento(Set<PublicoAlvoSegmento> publicoAlvoSegmento) {
		this.publicoAlvoSegmento = publicoAlvoSegmento;
	}

	public void setPublicoAlvoSetor(Integer publicoAlvoSetor) {
		this.publicoAlvoSetor = publicoAlvoSetor;
	}

	public void setPublicoAlvoSustentabilidade(Set<Confirmacao> publicoAlvoSustentabilidade) {
		this.publicoAlvoSustentabilidade = publicoAlvoSustentabilidade;
	}

	public void setUnidadeOrganizacionalList(List<UnidadeOrganizacional> unidadeOrganizacionalList) {
		this.unidadeOrganizacionalList = unidadeOrganizacionalList;
	}
}