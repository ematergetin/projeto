package br.gov.df.emater.aterwebsrv.dao.pessoa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.df.emater.aterwebsrv.ferramenta.UtilitarioString;
import br.gov.df.emater.aterwebsrv.modelo.dto.PessoaCadFiltroDto;

public class PessoaDaoImpl implements PessoaDaoCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> filtrar(PessoaCadFiltroDto filtro) {
		// objetos de trabalho
		List<Object[]> result = null;
		List<Object> params = new ArrayList<Object>();

		// construção do sql
		StringBuilder sql = new StringBuilder();
		sql.append("select p.id").append("\n");
		sql.append("     , p.nome").append("\n");
		sql.append("     , p.apelidoSigla").append("\n");
		sql.append("     , p.pessoaTipo").append("\n");
		sql.append("     , p.cpf").append("\n");
		sql.append("     , p.cnpj").append("\n");
		sql.append("     , p.publicoAlvoConfirmacao").append("\n");
		sql.append("     , a.md5").append("\n");
		sql.append("     , a.extensao").append("\n");
		sql.append("     , a.tipo").append("\n");
		sql.append("from Pessoa p").append("\n");
		sql.append("left join p.arquivoList pa").append("\n");
		sql.append("left join pa.arquivo a").append("\n");
		sql.append("where (p.pessoaTipo in ('PF', 'PJ'))").append("\n");
		sql.append("and   (pa is null or pa.perfil = 'S')").append("\n");
		//sql.append("where 1 = 1").append("\n");
		if (!StringUtils.isEmpty(filtro.getNome())) {
			params.add(String.format("%%%s%%", filtro.getNome()));
			sql.append("and (p.nome like ?").append(params.size());
			params.add(String.format("%%%s%%", filtro.getNome()));
			sql.append(" or p.apelidoSigla like ?").append(params.size()).append(")").append("\n");
		}
		if (filtro.getTipoPessoa() != null && !(Arrays.asList(0, 2).contains(filtro.getTipoPessoa().size()))) {
			params.add(filtro.getTipoPessoa().toArray()[0]);
			sql.append("and p.pessoaTipo = ?").append(params.size()).append("\n");
		}
		if (!StringUtils.isEmpty(filtro.getCpf())) {
			params.add(UtilitarioString.formataCpf(filtro.getCpf()));
			sql.append("and p.cpf = ?").append(params.size()).append("\n");
		}
		if (!StringUtils.isEmpty(filtro.getCnpj())) {
			params.add(UtilitarioString.formataCnpj(filtro.getCnpj()));
			sql.append("and p.cnpj = ?").append(params.size()).append("\n");
		}
		sql.append("order by p.nome, p.apelidoSigla").append("\n");

		// criar a query
		TypedQuery<Object[]> query = em.createQuery(sql.toString(), Object[].class);

		// inserir os parametros
		for (int i = 1; i <= params.size(); i++) {
			query.setParameter(i, params.get(i - 1));
		}

		// definir a pagina a ser consultada
		filtro.configuraPaginacao(query);

		// executar a consulta
		result = query.getResultList();

		// retornar
		return result;
	}

}