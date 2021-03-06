package br.gov.df.emater.aterwebsrv.dao.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.df.emater.aterwebsrv.modelo.pessoa.Endereco;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.Pessoa;
import br.gov.df.emater.aterwebsrv.modelo.pessoa.PessoaEndereco;

@Repository("PessoaEnderecoDao")
public interface PessoaEnderecoDao extends JpaRepository<PessoaEndereco, Integer> {

	PessoaEndereco findOneByPessoaAndEndereco(Pessoa result, Endereco endereco);

}