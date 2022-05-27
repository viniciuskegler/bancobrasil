/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Cartao;
import br.com.senac.entidade.Cliente;
import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vinicius.kegler
 */
public class CartaoDaoImplTest {

    private Session sessao;
    private CartaoDao cartaoDao;
    private Faker falso;
    private Cartao cartao;

    public CartaoDaoImplTest() {
        cartaoDao = new CartaoDaoImpl();
        falso = new Faker();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvarCartao");
        cartao = new Cartao(null, falso.finance().creditCard(CreditCardType.MASTERCARD), "MasterCard", "202" + falso.number().digits(1));
        cartao.setCliente(pesquisarCliente());
        sessao = HibernateUtil.abrirConexao();
        cartaoDao.salvarOuAlterar(cartao, sessao);
        sessao.close();
        assertNotNull(cartao.getId());
    }

//    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarCartaoBD();


    }

    @Test
    public void testPesquisarPorNumero() {
        System.out.println("PesquisarPorNumero");
        buscarCartaoBD();
        sessao = HibernateUtil.abrirConexao();
        cartaoDao.pesquisarPorNumero(cartao.getNumero(), sessao);
        sessao.close();
        assertNotNull(cartao);

    }

    public Cartao buscarCartaoBD() {
        sessao = HibernateUtil.abrirConexao();
        Query<Cartao> consulta = sessao.createQuery("From Cartao c");
        consulta.setMaxResults(1);
        consulta.setFirstResult(1);
        List<Cartao> cartoes = consulta.getResultList();
        if (cartoes.isEmpty()) {
            testSalvar();
        } else {
            cartao = cartoes.get(0);
        }
        return cartao;
    }

    public Cliente pesquisarCliente() {
        PessoaFisicaDaoImplTest pfdit = new PessoaFisicaDaoImplTest();
        pfdit.buscarPFBd();
        PessoaJuridicaDaoImplTest pjdit = new PessoaJuridicaDaoImplTest();
        pjdit.buscarPJBd();
        sessao = HibernateUtil.abrirConexao();
        List<Cliente> clientes = sessao.createQuery("from Cliente c").getResultList();
        Collections.shuffle(clientes);
        sessao.close();
        return clientes.get(0);
    }
}
