/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Cartao;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author vinicius.kegler
 */
public class CartaoDaoImpl extends BaseDaoImpl<Cartao, Long> implements CartaoDao, Serializable{

    @Override
    public Cartao pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return sessao.find(Cartao.class, id);
    }

    @Override
    public Cartao pesquisarPorNumero(String numero, Session sessao) throws HibernateException {
        return (Cartao) sessao.createQuery("From Cartao c where c.numero = :numeroHql").setParameter("numeroHql", numero).getSingleResult();
    }
    
}
