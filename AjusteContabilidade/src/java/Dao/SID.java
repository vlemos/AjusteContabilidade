/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Controller.Config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vinicius.lemos
 */
public class SID extends Config{
    
    public String trocaMatriz(String usuario, String senha){
        String retorno = "";
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuario + "&SENUSU=" + senha + "&PROXACAO=SID.Srv.AltEmpFil&CodEmp=1&CodFil=1");
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        }
        return retorno;
    }
    
    public String criaLoteContabil(String usuario, String senha, double valorLote, String dataLote ){
        String retorno = "";
        
        //Date dataHoje =  new Date();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               
        
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuario + "&SENUSU=" + senha + "&PROXACAO=SID.Lct.GerarLote&DatLot=" + dataLote + "&TotInf="+ Double.toString(valorLote));
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        }
        return retorno;        
    }

    public String lancarCreditoMatriz(String usuarioSapiens, String senhaSapiens, String numlote, int Conta, double valorConta, String centroCusto, String dataLote) {
        String retorno = "";
        
      //  Date dataHoje =  new Date();
     //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          //String mensagem = "Ajuste";
          String mensagem = "Ajustes_Matriz_Para_Filial";
        
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuarioSapiens 
                    + "&SENUSU=" + senhaSapiens 
                    + "&PROXACAO=SID.Lct.Gravar" 
                    + "&NumLot="+ numlote 
                    + "&CtaCre="+ Conta 
                    + "&CtaDeb=2413&VlrLct="+ valorConta                    
                    + "&DatLct=" + dataLote
                    + "&CtaRed1="+ Conta 
                    + "&CodCcu1="+ centroCusto 
                    + "&CodHpd=163"
                    + "&CplLct="+ mensagem
                    + "&PerRat1=100&vlrRat1="
                    + valorConta );
            
            
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        }
        return retorno;        
    }
    
    public String lancarCreditoOrigem(String usuarioSapiens, String senhaSapiens, String numlote, int Conta, double valorConta, String centroCusto, String dataLote, String mensagem) {
        String retorno = "";
        
      //  Date dataHoje =  new Date();
     //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          //String mensagem = "Ajuste";
          String msg = "Ajustes_Origem_Para_Destino";
        
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuarioSapiens 
                    + "&SENUSU=" + senhaSapiens 
                    + "&PROXACAO=SID.Lct.Gravar" 
                    + "&NumLot="+ numlote 
                    + "&CtaCre="+ Conta 
                    + "&CtaDeb=2413&VlrLct="+ valorConta                    
                    + "&DatLct=" + dataLote 
                    + "&CtaRed1="+ Conta 
                    + "&CodCcu1="+ centroCusto 
                    //+ "&CodHpd=163"
                    + "&CplLct="+ mensagem 
                    + "&PerRat1=100&vlrRat1="+ valorConta );
            
            
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        }
        return retorno;        
    }

    public String trocaFilial(String usuarioSapiens, String senhaSapiens, String FilialDestino) {
        String retorno = "";
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuarioSapiens 
                    + "&SENUSU=" + senhaSapiens 
                    + "&PROXACAO=SID.Srv.AltEmpFil&CodEmp=1&CodFil="+FilialDestino);
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        }
        return retorno;
    }

    public String lancarDebitoFilial(String usuarioSapiens, String senhaSapiens, String numlote, int Conta, double valorConta, String centroCusto, String dataLote, String mensagem) {
         String retorno = "";
        
      //  Date dataHoje =  new Date();
     //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          //String mensagem = "Ajuste";
        String msg = "Ajustes_Matriz_Para_Filial";
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA"
                    + "&NOMUSU=" + usuarioSapiens 
                    + "&SENUSU=" + senhaSapiens 
                    + "&PROXACAO=SID.Lct.Gravar" 
                    + "&NumLot="+ numlote 
                    + "&CtaDeb="+ Conta 
                    + "&CtaCre=2413&VlrLct="+ valorConta 
                    + "&CplLct="+ mensagem 
                    + "&DatLct=" + dataLote 
                    + "&CtaRed1="+ Conta 
                    + "&CodCcu1="+ centroCusto 
                    //+ "&CodHpd=163" // Gilberto 22/12/2016 - inclusão do histórico padrão
                    +"&PerRat1=100&vlrRat1="+ valorConta );
            
            
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        } catch ( Exception ex){
            
        }
        return retorno;        
    }
    
    public String lancarDebitoDestino(String usuarioSapiens, String senhaSapiens, String numlote, int Conta, double valorConta, String centroCusto, String dataLote, String mensagem) {
         String retorno = "";
        
      //  Date dataHoje =  new Date();
     //   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
          //String mensagem = "Ajuste";
        //String mensagem = "Ajustes_Origem_Para_Destino";
        try {
            URL url = new URL(CAMINHO_SID+"?SIS=CO&LOGIN=SID&ACAO=EXESENHA&"
                    + "NOMUSU=" + usuarioSapiens
                    + "&SENUSU=" + senhaSapiens
                    + "&PROXACAO=SID.Lct.Gravar"
                    + "&NumLot="+ numlote
                    + "&CtaDeb="+ Conta
                    + "&CtaCre=2413&VlrLct="+ valorConta                   
                    + "&DatLct=" + dataLote 
                    + "&CtaRed1="+ Conta 
                    + "&CodCcu1="+ centroCusto
                    //+ "&CodHpd=163"  // Gilberto 22/12/2016 - inclusão do histórico padrão
                    + "&CplLct="+ mensagem // Gilberto 27/12/2016 - testando a ordem sugerida na doc. senior.
                    + "&PerRat1=100&vlrRat1="+ valorConta );
            
            
            URLConnection urlConnection = url.openConnection();

            /* Enviando dados */
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            /* outputWriter1.write(); */
            outputWriter.flush();

            /* Obtendo as respostas */
            InputStreamReader inputReader = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputReader);
            

            //System.out.println("\n** retorno da página web (Nota) **");
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {
                
                retorno += linha + " ";
                //System.out.println(linha);
            }
            
            bufferedReader.close();
            
        } catch (IOException iOException) {
        } catch ( Exception ex){
            
        }
        return retorno;        
    }
}
