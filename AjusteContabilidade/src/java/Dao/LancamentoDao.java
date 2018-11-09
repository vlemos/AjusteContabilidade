/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Entity.Lancamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vinicius.lemos
 */
public class LancamentoDao extends Conecta{
    
    public List<Lancamento> buscarLancamentos(String dtInicial, String dtFinal, String CentroCusto){
		try{
			
			open();
			
			System.out.print("Conectou BD");
			stmt = con.prepareStatement("select l.ctadeb,l.numlct,r.vlrrat,l.vlrlct, r.ctared, r.codccu, r.cpllct" +
                                                        " from ERP.E640lct l " + 
                                                        " inner join ERP.E640rat r on " +
                                                        " l.numlct = r.numlct and" +
                                                        " l.ctadeb = r.ctared" +
                                                        " where l.datlct between ? and ?  " +
                                                        " and r.codccu  = ? and l.codfil = 1 and l.sitlct in ('1','2')" +
                                                        " order by l.ctadeb");
			
                        //java.sql.Date dataInicioSql = new java.sql.Date(dtInicial.getTime());
                        //java.sql.Date dataFimSql = new java.sql.Date(dtFinal.getTime());
                        
			stmt.setString(1,dtInicial);
                        stmt.setString(2,dtFinal);
                        stmt.setString(3, CentroCusto);
                        
                        System.out.println(dtInicial);
                        System.out.println(dtFinal);
                        System.out.println(CentroCusto);
                        
                        rs = stmt.executeQuery();
			
			List<Lancamento> lancamentos = new ArrayList<Lancamento>();
			
			while(rs.next()){
				
				
				
				Lancamento imp = new Lancamento();
				imp.setCtaDeb(rs.getInt("ctadeb"));
				imp.setNumLct(rs.getString("numlct"));
				imp.setVlrRat1(rs.getDouble("vlrrat"));
				imp.setVlrLct(rs.getDouble("vlrlct"));
				imp.setCtaRed1(rs.getInt("ctared"));
				imp.setCodCcu1(rs.getInt("codccu"));
				imp.setCplLct(rs.getString("cpllct"));
				
				lancamentos.add(imp);
				
				
				
			}
			System.out.println(lancamentos);
			
			close();
			return lancamentos;
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
    }
    
    public List<Lancamento> buscarLancamentosFilial(String dtInicial, String dtFinal, String CentroCusto, int codigoFilial){
		try{
			
			open();
			
			System.out.print("Conectou BD");
			stmt = con.prepareStatement("select l.ctadeb,l.numlct,r.vlrrat,l.vlrlct, r.ctared, r.codccu, l.cpllct" +
                                                        " from ERP.E640lct l " + 
                                                        " inner join ERP.E640rat r on " +
                                                        " l.numlct = r.numlct and" +
                                                        " l.ctadeb = r.ctared" +
                                                        " where l.datlct between ? and ?  " +
                                                        " and r.codccu  = ? and l.codfil = ? and l.sitlct in ('1','2')" +
                                                        " order by l.ctadeb");
			
                        //java.sql.Date dataInicioSql = new java.sql.Date(dtInicial.getTime());
                        //java.sql.Date dataFimSql = new java.sql.Date(dtFinal.getTime());
                        
			stmt.setString(1,dtInicial);
                        stmt.setString(2,dtFinal);
                        stmt.setString(3, CentroCusto);
                        stmt.setInt(4, codigoFilial);
                        
                        System.out.println(dtInicial);
                        System.out.println(dtFinal);
                        System.out.println(CentroCusto);
                        System.out.println(codigoFilial);
                        
                        rs = stmt.executeQuery();
			
			List<Lancamento> lancamentos = new ArrayList<Lancamento>();
			
			while(rs.next()){
				
				
				
				Lancamento imp = new Lancamento();
				imp.setCtaDeb(rs.getInt("ctadeb"));
				imp.setNumLct(rs.getString("numlct"));
				imp.setVlrRat1(rs.getDouble("vlrrat"));
				imp.setVlrLct(rs.getDouble("vlrlct"));
				imp.setCtaRed1(rs.getInt("ctared"));
				imp.setCodCcu1(rs.getInt("codccu"));
				imp.setCplLct(rs.getString("cpllct"));
				
				lancamentos.add(imp);
				
				
				
			}
			System.out.println(lancamentos);
			
			close();
			return lancamentos;
			
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
    }
}
