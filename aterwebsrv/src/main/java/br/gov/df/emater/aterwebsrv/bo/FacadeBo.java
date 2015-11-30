package br.gov.df.emater.aterwebsrv.bo;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.chain.Command;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.gov.df.emater.aterwebsrv.modelo.ater.PropriedadeRural;
import br.gov.df.emater.aterwebsrv.modelo.dominio.PessoaTipo;
import br.gov.df.emater.aterwebsrv.modelo.dto.FormularioCadFiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.dto.PessoaCadFiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.dto.PropriedadeRuralCadFiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.dto.UnidadeOrganizacionalCadFiltroDto;
import br.gov.df.emater.aterwebsrv.modelo.formulario.Formulario;
import br.gov.df.emater.aterwebsrv.modelo.formulario.FormularioVersao;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;

@Service
public class FacadeBo implements BeanFactoryAware {

	private BeanFactory beanFactory;

	@Transactional
	private _Contexto _executar(Principal usuario, String comandoNome) throws Exception {
		return this._executar(usuario, comandoNome, null);
	}

	@Transactional
	private _Contexto _executar(Principal usuario, String comandoNome, Object requisicao) throws Exception {
		_Contexto result = new _Contexto(usuario, comandoNome, requisicao);
		this._getComando(comandoNome).execute(result);
		return result;
	}

	private Command _getComando(String comandoNome) {
		return (Command) this.beanFactory.getBean(comandoNome);
	}

	@Transactional(readOnly = true)
	public _Contexto dominio(Principal usuario, String[] ent, String npk, String vpk, String order, String[] fetchs) throws Exception {
		Map<String, Object> requisicao = new HashMap<String, Object>();
		requisicao.put("entidade", ent);
		requisicao.put("nomeChavePrimaria", npk);
		requisicao.put("valorChavePrimaria", vpk);
		requisicao.put("order", order);
		requisicao.put("fetchs", fetchs);

		return this._executar(usuario, "DominioCh", requisicao);
	}

	@Transactional(readOnly = true)
	public _Contexto enderecoNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "EnderecoNovoCmd");
	}

	@Transactional
	public _Contexto formularioColetar(Principal usuario, FormularioVersao formularioVersao) throws Exception {
		return this._executar(usuario, "FormularioColetarCh", formularioVersao);
	}

	@Transactional(readOnly = true)
	public _Contexto formularioFiltroExecutar(Principal usuario, FormularioCadFiltroDto filtro) throws Exception {
		return this._executar(usuario, "FormularioFiltroExecutarCh", filtro);
	}

	@Transactional(readOnly = true)
	public _Contexto formularioFiltroNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "FormularioFiltroNovoCmd");
	}

	@Transactional(readOnly = true)
	public _Contexto formularioNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "FormularioNovoCh");
	}

	@Transactional
	public _Contexto formularioSalvar(Principal usuario, Formulario formulario) throws Exception {
		return this._executar(usuario, "FormularioSalvarCh", formulario);
	}

	@Transactional(readOnly = true)
	public _Contexto formularioVisualizar(Principal usuario, Integer id) throws Exception {
		return this._executar(usuario, "FormularioVisualizarCh", id);
	}

	@Transactional(readOnly = true)
	public _Contexto formularioVisualizarPorCodigo(Principal usuario, String codigo) throws Exception {
		return this._executar(usuario, "FormularioVisualizarPorCodigoCh", codigo);
	}

	@Transactional(readOnly = true)
	public _Contexto pessoaFiltroExecutar(Principal usuario, PessoaCadFiltroDto filtro) throws Exception {
		return this._executar(usuario, "PessoaFiltroExecutarCh", filtro);
	}

	@Transactional(readOnly = true)
	public _Contexto pessoaFiltroNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "PessoaFiltroNovoCmd");
	}

	@Transactional(readOnly = true)
	public _Contexto pessoaNovo(Principal usuario, PessoaTipo pessoaTipo) throws Exception {
		return this._executar(usuario, "PessoaNovoCh", pessoaTipo);
	}

	@Transactional
	public _Contexto pessoaSalvar(Principal usuario, Pessoa pessoa) throws Exception {
		return this._executar(usuario, "PessoaSalvarCh", pessoa);
	}

	@Transactional(readOnly = true)
	public _Contexto pessoaVisualizar(Principal usuario, Integer id) throws Exception {
		return this._executar(usuario, "PessoaVisualizarCh", id);
	}

	@Transactional(readOnly = true)
	public _Contexto propriedadeRuralFiltroExecutar(Principal usuario, PropriedadeRuralCadFiltroDto filtro) throws Exception {
		return this._executar(usuario, "PropriedadeRuralFiltroExecutarCh", filtro);
	}

	@Transactional(readOnly = true)
	public _Contexto propriedadeRuralFiltroNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "PropriedadeRuralFiltroNovoCmd");
	}

	@Transactional(readOnly = true)
	public _Contexto propriedadeRuralNovo(Principal usuario) throws Exception {
		return this._executar(usuario, "PropriedadeRuralNovoCh");
	}

	@Transactional
	public _Contexto propriedadeRuralSalvar(Principal usuario, PropriedadeRural propriedadeRural) throws Exception {
		return this._executar(usuario, "PropriedadeRuralSalvarCh", propriedadeRural);
	}

	@Transactional(readOnly = true)
	public _Contexto propriedadeRuralVisualizar(Principal usuario, Integer id) throws Exception {
		return this._executar(usuario, "PropriedadeRuralVisualizarCh", id);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	// Unidade Organizacional
	// Fitlro Executar
	@Transactional(readOnly = true)
	public _Contexto unidadeOrganizacionalFiltroExecutar(Principal usuario, UnidadeOrganizacionalCadFiltroDto filtro) throws Exception {
		return this._executar(usuario, "UnidadeOrganizacionalFiltroExecutarCh", filtro);
	}

	public _Contexto utilArquivo(Principal usuario, MultipartFile arquivo, HttpServletRequest request, String tipo) throws Exception {
		Map<String, Object> requisicao = new HashMap<String, Object>();
		requisicao.put("arquivo", arquivo);
		requisicao.put("request", request);
		requisicao.put("tipo", tipo);

		return this._executar(usuario, "UtilArquivoCh", requisicao);
	}

}