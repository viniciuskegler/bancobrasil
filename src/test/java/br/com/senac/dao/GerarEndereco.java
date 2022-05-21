/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.senac.dao;

import br.com.senac.entidade.Endereco;
import static br.com.senac.util.GeradorUtil.gerarCep;
import static br.com.senac.util.GeradorUtil.gerarNumero;
import com.github.javafaker.Faker;

/**
 *
 * @author vinicius.kegler
 */
public class GerarEndereco {

    private static Faker falso = new Faker();

    public static Endereco gerarEndereco() {
        Endereco end = new Endereco(
                null,
                falso.address().streetName(),
                falso.address().secondaryAddress(),
                gerarNumero(3),
                falso.address().cityName(),
                falso.address().state(),
                "casa",
                gerarCep());
        return end;
    }

}
