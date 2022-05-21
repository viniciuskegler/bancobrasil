/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import br.com.senac.entidade.PessoaFisica;
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
public class PessoaFisicaDaoImplTest {

    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao pessoaFisicaDao;
    private Session sessao;
    private Faker falso;

    public PessoaFisicaDaoImplTest() {
        pessoaFisicaDao = new PessoaFisicaDaoImpl();
        falso = new Faker();
    }

//    @Test
    public void testSalvar() {
        System.out.println("salvar");
        ProfissaoDaoImplTest pdit = new ProfissaoDaoImplTest();
        pessoaFisica = new PessoaFisica(
                gerarNome(),
                gerarLogin(),
                gerarCpf(),
                "6599545"
        );
        Endereco endereco = GerarEndereco.gerarEndereco();
        pessoaFisica.setEndereco(endereco);
        endereco.setCliente(pessoaFisica);
        pessoaFisica.setProfissao(pdit.buscarProfissaoBd());
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();
        assertNotNull(pessoaFisica.getId());
    }

//    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPFBd();
        pessoaFisica.setNome(gerarNome());
        pessoaFisica.setCpf(gerarCpf());
        pessoaFisica.getEndereco().setCep(gerarCep());
        pessoaFisica.getEndereco().setCidade(falso.address().cityName());
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, sessao);
        sessao.close();
        sessao = HibernateUtil.abrirConexao();
        PessoaFisica pfAlt = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertTrue(pessoaFisica.getNome().equals(pfAlt.getNome()) && pessoaFisica.getCpf().equals(pfAlt.getCpf()));
        assertTrue(pessoaFisica.getEndereco().getCep().equals(pfAlt.getEndereco().getCep()) && pessoaFisica.getEndereco().getCidade().equals(pfAlt.getEndereco().getCidade()));
    }

//    @Test
    public void testExcluir() {
        System.out.println("");
        buscarPFBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaFisicaDao.excluir(pessoaFisica, sessao);
        pessoaFisica = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertNull(pessoaFisica);
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarPFBd();
        sessao = HibernateUtil.abrirConexao();
        pessoaFisica = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), sessao);
        sessao.close();
        assertNotNull(pessoaFisica);
    }

//    @Test
    public void testPesquisarPorNome() {
        System.out.println("PesquisarPorNome");
        buscarPFBd();
        sessao = HibernateUtil.abrirConexao();
        List<PessoaFisica> pfs = pessoaFisicaDao.pesquisarPorNome(pessoaFisica.getNome(), sessao);
        sessao.close();
        assertTrue(!pfs.isEmpty());
    }

    public PessoaFisica buscarPFBd() {
        sessao = HibernateUtil.abrirConexao();
        List<PessoaFisica> pfs = sessao.createQuery("From PessoaFisica pf").getResultList();
        sessao.close();
        if (pfs.isEmpty()) {
            testSalvar();
        } else {
            pessoaFisica = pfs.get(0);
            System.out.println(pfs.get(0));
        }
        return pessoaFisica;
    }

}
