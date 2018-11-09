/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author vinicius.lemos
 */
public class Conecta extends Config {
    	PreparedStatement stmt;
	ResultSet rs;
	Connection con = null;
	
	
	

	protected void open() throws Exception {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(stringDeConexao, usuarioBD, senhaBD);
		} catch (Exception e) {
			//logger.info("Produtos OK... Inicia Gravação da NF");
			
			System.out.println("Erro na conexão com o Banco da JAHU" + e.getMessage());
		}
	}
	
	protected void close() throws Exception{
		con.close();
	}

}
