package br.gov.df.emater.aterwebsrv.bo.propriedade_rural;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import br.gov.df.emater.aterwebsrv.bo.BoException;
import br.gov.df.emater.aterwebsrv.bo._Contexto;
import br.gov.df.emater.aterwebsrv.bo._SalvarCmd;
import br.gov.df.emater.aterwebsrv.dao.ater.PropriedadeRuralArquivoDao;
import br.gov.df.emater.aterwebsrv.dao.ater.PropriedadeRuralDao;
import br.gov.df.emater.aterwebsrv.dao.ater.PropriedadeRuralFormaUtilizacaoEspacoRuralDao;
import br.gov.df.emater.aterwebsrv.dao.ater.PropriedadeRuralPendenciaDao;
import br.gov.df.emater.aterwebsrv.dao.ater.PublicoAlvoDao;
import br.gov.df.emater.aterwebsrv.dao.ater.PublicoAlvoPropriedadeRuralDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.AreaDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.ArquivoDao;
import br.gov.df.emater.aterwebsrv.dao.pessoa.EnderecoDao;
import br.gov.df.emater.aterwebsrv.modelo.ater.PropriedadeRural;
import br.gov.df.emater.aterwebsrv.modelo.ater.PropriedadeRuralArquivo;
import br.gov.df.emater.aterwebsrv.modelo.ater.PropriedadeRuralFormaUtilizacaoEspacoRural;
import br.gov.df.emater.aterwebsrv.modelo.dominio.Confirmacao;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Area;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Arquivo;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Endereco;

@Service("PropriedadeRuralSalvarCmd")
public class SalvarCmd extends _SalvarCmd {

	@Autowired
	private AreaDao areaDao;

	@Autowired
	private ArquivoDao arquivoDao;

	@Autowired
	private PropriedadeRuralDao dao;

	@Autowired
	private EnderecoDao enderecoDao;

	@Autowired
	private PropriedadeRuralPendenciaDao propriedadeRuralPendenciaDao;

	@Autowired
	private PropriedadeRuralArquivoDao propriedadeRuralArquivoDao;

	@Autowired
	private PropriedadeRuralFormaUtilizacaoEspacoRuralDao propriedadeRuralFormaUtilizacaoEspacoRuralDao;

	@Autowired
	private PublicoAlvoDao publicoAlvoDao;

	@Autowired
	private PublicoAlvoPropriedadeRuralDao publicoAlvoPropriedadeRuralDao;

	public SalvarCmd() {
	}

	@Override
	public boolean executar(_Contexto contexto) throws Exception {
		PropriedadeRural result = (PropriedadeRural) contexto.getRequisicao();

		// captar o registro de atualização da tabela
		logAtualizar(result, contexto);

		limparChavePrimaria(result.getPublicoAlvoPropriedadeRuralList());
		limparChavePrimaria(result.getArquivoList());
		limparChavePrimaria(result.getPendenciaList());

		if (result.getEndereco() == null) {
			throw new BoException("O campo Endereço é obrigatório");
		}
		Endereco endereco = result.getEndereco();
		endereco.setPropriedadeRuralConfirmacao(Confirmacao.S);

		if (result.getEndereco().getId() != null) {
			Endereco enderecoSalvo = enderecoDao.findOne(result.getEndereco().getId());
			if (enderecoSalvo != null && !CollectionUtils.isEmpty(enderecoSalvo.getAreaList())) {
				enderecoSalvo.getAreaList().forEach((areaSalvo) -> {
					boolean encontrou = false;
					if (!CollectionUtils.isEmpty(endereco.getAreaList())) {
						for (Area area : endereco.getAreaList()) {
							if (areaSalvo.getId().equals(area.getId())) {
								encontrou = true;
								break;
							}
						}
					}
					if (!encontrou) {
						areaDao.delete(areaSalvo);
					}
				});
			}
		}

		// atualizar chave do endereço das áreas
		if (!CollectionUtils.isEmpty(endereco.getAreaList())) {
			endereco.getAreaList().forEach((area) -> area.setEndereco(endereco));
		}
		enderecoDao.save(endereco);

		// ajustar o setor
		if (!CollectionUtils.isEmpty(result.getFormaUtilizacaoEspacoRuralList())) {
			result.getFormaUtilizacaoEspacoRuralList().forEach((formaUtilizacaoEspacoRural) -> {
				formaUtilizacaoEspacoRural.setPropriedadeRural(result);
				if (result.getId() != null) {
					PropriedadeRuralFormaUtilizacaoEspacoRural salvo = propriedadeRuralFormaUtilizacaoEspacoRuralDao.findOneByPropriedadeRuralAndFormaUtilizacaoEspacoRural(result, formaUtilizacaoEspacoRural.getFormaUtilizacaoEspacoRural());
					if (salvo != null) {
						formaUtilizacaoEspacoRural.setId(salvo.getId());
					}
				}
			});
		}

		dao.save(result);

		// excluir as propriedades vinculadas ao publico alvo
		excluirRegistros(result, "publicoAlvoPropriedadeRuralList", publicoAlvoPropriedadeRuralDao);

		// atualizar vinculo de publico alvo e propriedade rural
		if (!CollectionUtils.isEmpty(result.getPublicoAlvoPropriedadeRuralList())) {
			result.getPublicoAlvoPropriedadeRuralList().forEach((papr) -> {
				papr.setPropriedadeRural(result);
				papr.setPublicoAlvo(publicoAlvoDao.findOneByPessoa(papr.getPublicoAlvo().getPessoa()));
				papr.setPrincipal(papr.getPrincipal() == null ? Confirmacao.N : papr.getPrincipal()); 
				publicoAlvoPropriedadeRuralDao.save(papr);
			});
		}

		// tratar a exclusao de registros
		excluirRegistros(result, "arquivoList", propriedadeRuralArquivoDao);

		if (!CollectionUtils.isEmpty(result.getArquivoList())) {
			// tratar a insersao de registros
			result.getArquivoList().forEach((propriedadeRuralArquivo) -> {
				Arquivo arquivo = propriedadeRuralArquivo.getArquivo();
				arquivo = arquivoDao.findByMd5(arquivo.getMd5());
				if (arquivo != null) {
					arquivo.setMd5(propriedadeRuralArquivo.getArquivo().getMd5());
					arquivo.setNomeOriginal(propriedadeRuralArquivo.getArquivo().getNomeOriginal());
					arquivo.setDataUpload(propriedadeRuralArquivo.getArquivo().getDataUpload());
					arquivo.setExtensao(propriedadeRuralArquivo.getArquivo().getExtensao());
					arquivo.setTamanho(propriedadeRuralArquivo.getArquivo().getTamanho());
					arquivo.setTipo(propriedadeRuralArquivo.getArquivo().getTipo());
				}
				arquivo = arquivoDao.save(arquivo);
				propriedadeRuralArquivo.setArquivo(arquivo);
				propriedadeRuralArquivo.setPropriedadeRural(result);

				PropriedadeRuralArquivo propriedadeRuralArquivoSalvo = propriedadeRuralArquivoDao.findOneByPropriedadeRuralAndArquivo(result, arquivo);
				if (propriedadeRuralArquivoSalvo != null) {
					propriedadeRuralArquivo.setId(propriedadeRuralArquivoSalvo.getId());
				}
				propriedadeRuralArquivoDao.save(propriedadeRuralArquivo);
			});
		}

		// tratar a exclusao de registros
		excluirRegistros(result, "pendenciaList", propriedadeRuralPendenciaDao);

		// salvar pendencias do cadastro
		if (!CollectionUtils.isEmpty(result.getPendenciaList())) {
			// tratar a insersao de registros
			result.getPendenciaList().forEach((pendencia) -> {
				pendencia.setPropriedadeRural(result);
				propriedadeRuralPendenciaDao.save(pendencia);
			});
		}

		dao.flush();

		contexto.setResposta(result.getId());
		return true;
	}

}