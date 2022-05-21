/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Profissao;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Locale;
import org.hibernate.Session;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author vinicius.kegler
 */
public class ProfissaoDaoImplTest {

    private Profissao profissao;
    private ProfissaoDao profissaoDao;
    private Session sessao;
    private Faker falso;

    public ProfissaoDaoImplTest() {
        profissaoDao = new ProfissaoDaoImpl();
        falso = new Faker();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        profissao = new Profissao(falso.job().title(), falso.lorem().sentence(5));
        sessao = HibernateUtil.abrirConexao();
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();
        assertNotNull(profissao.getId());
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        profissao.setDescricao(falso.lorem().sentence(6));
        profissao.setNome(falso.job().title());
        profissaoDao.salvarOuAlterar(profissao, sessao);
        sessao.close();
        sessao = HibernateUtil.abrirConexao();
        Profissao profissaoAlt = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        sessao.close();
        assertTrue(profissao.getNome().equals(profissaoAlt.getNome()) && profissao.getDescricao().equals(profissaoAlt.getDescricao()));
    }

//    @Test
//    public void testExcluir() {
//        System.out.println("excluir");
//        buscarProfissaoBd();
//        sessao = HibernateUtil.abrirConexao();
//        profissaoDao.excluir(profissao, sessao);
//        profissao = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
//        sessao.close();
//        assertNull(profissao);
//    }
    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        Profissao profissaoPesq = profissaoDao.pesquisarPorId(profissao.getId(), sessao);
        assertNotNull(profissaoPesq);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfissaoBd();
        sessao = HibernateUtil.abrirConexao();
        List<Profissao> profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), sessao);
        sessao.close();
        assertTrue(!profissoes.isEmpty());
    }

    public Profissao buscarProfissaoBd() {
        sessao = HibernateUtil.abrirConexao();
        List<Profissao> profissoes = sessao.createQuery("From Profissao p").getResultList();
        sessao.close();
        if (profissoes.isEmpty()) {
            testSalvar();
        } else {
            profissao = profissoes.get(0);
        }
        return profissao;
    }

}
