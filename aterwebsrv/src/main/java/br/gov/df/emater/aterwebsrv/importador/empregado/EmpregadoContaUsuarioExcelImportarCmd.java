package br.gov.df.emater.aterwebsrv.importador.empregado;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import br.gov.df.emater.aterwebsrv.bo.BoException;
import br.gov.df.emater.aterwebsrv.bo.FacadeBo;
import br.gov.df.emater.aterwebsrv.bo._Comando;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.dao.funcional.EmpregoDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.EmailDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.PessoaDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.PessoaEmailDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.PessoaRelacionamentoDao;
import br.gov.df.emater.aterwebsrv.dao.sistema.UsuarioDao;
import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioString;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.modelo.dominio.UsuarioStatusConta;
import br.gov.df.emater.aterwebsrv.modelo.funcional.Emprego;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Email;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.PessoaEmail;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.PessoaRelacionamento;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.RelacionamentoFuncao;
import br.gov.df.emater.aterwebsrv.modelo.sistema.Usuario;

@Service
public class EmpregadoContaUsuarioExcelImportarCmd extends _Comando {

	@Autowired
	private EmailDao emailDao;

	@Autowired
	private EmpregoDao empregoDao;

	@Autowired
	private PessoaDao pessoaDao;

	@Autowired
	private PessoaEmailDao pessoaEmailDao;

	@Autowired
	private PessoaRelacionamentoDao pessoaRelacionamentoDao;

	@Autowired
	private UsuarioDao usuarioDao;

	@SuppressWarnings("unchecked")
	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		List<Map<String, Object>> mapa = (List<Map<String, Object>>) contexto.get("ContaEmailEmpregadoExcel");

		EntityManager em = (EntityManager) contexto.get("em");
		FacadeBo facadeBo = (FacadeBo) contexto.get("facadeBo");
		Calendar agora = (Calendar) contexto.get("agora");
		RelacionamentoFuncao empregadoFuncao = (RelacionamentoFuncao) contexto.get("empregadoFuncao");

		PlatformTransactionManager transactionManager = (PlatformTransactionManager) contexto.get("transactionManager");
		DefaultTransactionDefinition transactionDefinition = (DefaultTransactionDefinition) contexto.get("transactionDefinition");

		int cont = 0;
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			for (Map<String, Object> reg : mapa) {
				if (StringUtils.isEmpty((String) reg.get("User Logon Name"))) {
					continue;
				}
				String conta[] = ((String) reg.get("User Logon Name")).split("\\@");
				if (conta == null || conta.length != 2) {
					continue;
				}
				String matricula = UtilitarioString.zeroEsquerda(conta[0].substring(3).toUpperCase(), 8);
				String emailExcel = ((String) reg.get("E-Mail Address")).toLowerCase();
				List<Emprego> empregoList = empregoDao.findByMatricula(matricula);
				if (!CollectionUtils.isEmpty(empregoList)) {
					Emprego emprego = null;
					for (Emprego e : empregoList) {
						if (e.getInicio().before(agora) && (e.getTermino() == null || e.getTermino().after(agora))) {
							if (emprego == null) {
								emprego = e;
							} else {
								throw new BoException("Empregado com mais de um emprego ativo");
							}
						}
					}
					if (emprego == null) {
						throw new BoException("Não foi possível identificar o emprego ativo do usuário");
					}

					List<PessoaRelacionamento> pessoaRelacionamentoList = emprego.getPessoaRelacionamentoList();
					if (pessoaRelacionamentoList == null) {
						pessoaRelacionamentoList = pessoaRelacionamentoDao.findByRelacionamento(emprego);
					}

					for (PessoaRelacionamento pr : pessoaRelacionamentoList) {
						if (pr.getRelacionamentoFuncao().getId().equals(empregadoFuncao.getId())) {
							Pessoa pessoa = pr.getPessoa();

							if (usuarioDao.findOneByPessoa(pessoa) == null) {

								PessoaEmail pessoaEmail = null;

								if (pessoa.getEmailList() != null) {
									for (PessoaEmail pe : pessoa.getEmailList()) {
										if (pe.getEmail().getEndereco().equalsIgnoreCase(emailExcel)) {
											pessoaEmail = pe;
											break;
										}
									}
								}
								if (pessoaEmail == null) {
									Email email = emailDao.findByEndereco(emailExcel);
									if (email == null) {
										email = new Email();
										email.setEndereco(emailExcel);
										email = emailDao.save(email);
									}
									pessoaEmail = new PessoaEmail();
									pessoaEmail.setEmail(email);
									pessoaEmail.setFinalidade("C");
									pessoaEmail.setPessoa(pessoa);
									pessoaEmail.setPrincipal(Confirmacao.S);
									pessoaEmail.setOrdem(pessoa.getEmailList() == null ? 1 : pessoa.getEmailList().size() + 1);

									pessoaEmail = pessoaEmailDao.save(pessoaEmail);
									em.detach(pessoa);
									pessoa = pessoaDao.findOne(pessoa.getId());
								}

								Usuario usr = new Usuario();
								usr.setPessoa(pessoa);
								usr.setPessoaEmail(pessoaEmail);
								usr.setUsuarioStatusConta(UsuarioStatusConta.R);
								usr = (Usuario) facadeBo.usuarioNovo(contexto.getUsuario(), usr).getResposta();

								facadeBo.usuarioSalvar(contexto.getUsuario(), usr);
							}
						}
					}
				}
				cont++;
			}
			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			transactionManager.rollback(transactionStatus);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("ContaEmailEmpregadoExcel importado %d contas", cont));
		}

		return false;
	}

}