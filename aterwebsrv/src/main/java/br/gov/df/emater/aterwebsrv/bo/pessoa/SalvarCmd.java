package br.gov.df.emater.aterwebsrv.bo.pessoa;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.dao.pessoa.PessoaDao;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;

@Service("PessoaSalvarCmd")
public class SalvarCmd extends _Comando {

	@Autowired
	private PessoaDao dao;

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		Pessoa pessoa = (Pessoa) contexto.getRequisicao();
		if (pessoa.getId() == null) {
			pessoa.setUsuarioInclusao(getUsuario(contexto.getUsuario().getName()));
			pessoa.setInclusaoData(Calendar.getInstance());
		} else {
			pessoa.setUsuarioInclusao(getUsuario(pessoa.getUsuarioInclusao().getUsername()));
		}
		pessoa.setUsuarioAlteracao(getUsuario(contexto.getUsuario().getName()));
		pessoa.setAlteracaoData(Calendar.getInstance());
		pessoa = dao.saveAndFlush(pessoa);
		contexto.setResposta(pessoa.getId());
		return true;
	}

}