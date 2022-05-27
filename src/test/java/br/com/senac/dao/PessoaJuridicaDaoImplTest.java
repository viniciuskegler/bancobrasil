/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaJuridica;
import static br.com.senac.util.GeradorUtil.*;
import com.github.javafaker.Faker;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author silvio.junior
 */
public class PessoaJuridicaDaoImplTest {

    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private Session sessao;
    private Faker falso;

    public PessoaJuridicaDaoImplTest() {
        pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
        falso = new Faker();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        pessoaJuridica = new PessoaJuridica("Empresa " + gerarNome(), gerarLogin() + "@gmail.com", gerarCnpj(), gerarNumero(6));
        Endereco endereco = GerarEndereco.gerarEndereco();
        pessoaJuridica.setEndereco(endereco);
        endereco.setCliente(pessoaJuridica);
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();
        assertNotNull(pessoaJuridica.getId());
    }

        @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPJBd();
        pessoaJuridica.setNome(gerarNome());
        pessoaJuridica.setCnpj(gerarCnpj());
        pessoaJuridica.getEndereco().setCep(gerarCep());
        pessoaJuridica.getEndereco().setCidade(falso.address().cityName());
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, sessao);
        sessao.close();
        sessao = HibernateUtil.abrirConexao();
        PessoaJuridica pjAlt = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertTrue(pessoaJuridica.getNome().equals(pjAlt.getNome()) && pessoaJuridica.getCnpj().equals(pjAlt.getCnpj()));
        assertTrue(pessoaJuridica.getEndereco().getCep().equals(pjAlt.getEndereco().getCep()) && pessoaJuridica.getEndereco().getCidade().equals(pjAlt.getEndereco().getCidade()));
    }

    @Test
    public void testExcluir() {
        System.out.println("");
        buscarPJBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridicaDao.excluir(pessoaJuridica, sessao);
        pessoaJuridica = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertNull(pessoaJuridica);
    }
    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarPJBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaJuridica = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), sessao);
        sessao.close();
        assertNotNull(pessoaJuridica);
    }

    public PessoaJuridica buscarPJBd() {
        sessao = HibernateUtil.abrirConexao();
        List<PessoaJuridica> pjs = sessao.createQuery("From PessoaJuridica pj").getResultList();
        sessao.close();
        if (pjs.isEmpty()) {
            testSalvar();
        } else {
            pessoaJuridica = pjs.get(0);
        }
        return pessoaJuridica;
    }
    
}
