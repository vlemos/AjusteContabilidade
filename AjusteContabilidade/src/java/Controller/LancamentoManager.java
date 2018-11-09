/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Dao.LancamentoDao;
import Dao.SID;
import Entity.Lancamento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author vinicius.lemos
 */
@ManagedBean(name = "lancamentoManager")
@SessionScoped
public class LancamentoManager {

    private Lancamento lancamento;
    private List<Lancamento> lancamentos;
    private List<Lancamento> lancamentosSelecionados;
    private Date dtInicio = new Date();
    private Date dtFim = new Date();
    private String centroCusto;
    private String centroCustoDestino;
    private String usuarioSapiens;
    private String senhaSapiens;
    private Date dataLote = new Date();
    private int filialOrigem;
    private int filialDestino;
    private Map<String,String> filiais;
    private Map<String,String> centroCustosOrigem;
    private Map<String,String> centroCustosDestino;

    LancamentoDao dao = new LancamentoDao();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Gerador de Mensagem pra tela
     *
     * @param summary
     * @param detail
     */
    
    @PostConstruct
    public void init() {
        filiais  = new HashMap<String, String>();
        filiais.put("MATRIZ", "1");
        filiais.put("BRASILIA", "8");
        filiais.put("CEASA RJ", "12");
        filiais.put("CEASA SP", "9");
        filiais.put("CURITIBA", "10");
        filiais.put("MINAS GERAIS", "11");
        filiais.put("PARAIBA", "15");
        filiais.put("PORTO ALEGRE", "13");
        filiais.put("RECIFE", "7");
        filiais.put("MARICULTURA", "21");
        filiais.put("SAO PAULO", "4");
        filiais.put("LOJA PORTO FRESCATTO", "19");
        filiais.put("ALAGOAS", "24");
        filiais = new TreeMap<String, String>(filiais);
    }
    
    public void addMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage("msg", facesMsg);
    }
    
    public void addErrorMessage(String summary, String detail) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage("msg", facesMsg);
        //FacesMessage message = new FacesMessage(summary, detail);
        //FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void ajustar() {
        
        String retorno = "";
        //System.out.println(lancamentosSelecionados);
        //sequencia de acontecimentos:
        // TROCA DE FILIAL PARA FILIAL DE ORIGEM
        // CRIA UM NOVO LOTE CONTABIL NA ORIGEM LOGADA PELO USUARIO - CERTIFICAR SE ESTE É A MATRIZ
        // GERA O LANÇAMENTO A CREDITO DO RATEIO NA ORIGEM
        // TROCA para filial correspondente
        // CRIA UM NOVO LOTE CONTABIL NO DESTINO
        // GERA O LANÇAMENTO A DEBITO DO RATEIO NO DESTINO
        SID sid = new SID(); // instancia SID
        //retorno = sid.trocaMatriz(usuarioSapiens, senhaSapiens); // chama a troca para a Filial 1
        //Troca usuário para Origem
        retorno = sid.trocaFilial(usuarioSapiens, senhaSapiens, "" + filialOrigem);

        if (retorno.equals("OK ")) { // se conseguir... segue para o passo 2

            // aqui será retornado o numero do Lote Criado
            String numeroLote = sid.criaLoteContabil(usuarioSapiens, senhaSapiens, buscaValorCriarLote(), sdf.format(dataLote));

            // pega cada lançamento e adiciona no lote criado como crédito
            for (Iterator<Lancamento> iterator = lancamentosSelecionados.iterator(); iterator.hasNext();) {
                Lancamento l = iterator.next();
                String msg = l.getCplLct().replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", "_");
                retorno = sid.lancarCreditoOrigem(usuarioSapiens, senhaSapiens, numeroLote.trim(), l.getCtaDeb(), l.getVlrRat1(), centroCusto, sdf.format(dataLote), msg);
            }

            // troca para filial de destino
            retorno = sid.trocaFilial(usuarioSapiens, senhaSapiens, "" + filialDestino);

            if (retorno.equals("OK ")) { // verifica se conseguiu trocar de filial

                //cria um lote na filial correspondente
                numeroLote = sid.criaLoteContabil(usuarioSapiens, senhaSapiens, buscaValorCriarLote(), sdf.format(dataLote));

                // pega cada lançamento e adiciona no lote criado como débito
                for (Iterator<Lancamento> iterator = lancamentosSelecionados.iterator(); iterator.hasNext();) {
                    Lancamento l = iterator.next();
                    String msg = l.getCplLct().replaceAll("\"", "").replaceAll("'", "").replaceAll(" ", "_");
                    retorno = sid.lancarDebitoDestino(usuarioSapiens, senhaSapiens, numeroLote.trim(), l.getCtaDeb(), l.getVlrRat1(), centroCusto, sdf.format(dataLote), msg);
                }
            } else { // caso não tenha conseguido ... exibe o erro
                addErrorMessage(retorno, "");
            }
            addMessage("Operação Executada com Sucesso!", "");
        } else {
            addErrorMessage(retorno, "Não foi possível trocar a filial."); // senao nã conseguiu trocar de filial, então imprime o Erro
        }
    }

    public void buscaLancamentos() {

        if ((dtInicio == null) || (dtFim == null) || (dataLote == null)) {
            addErrorMessage("Preencha todas as datas", "");
        } else if (filialOrigem == 0) {
            addErrorMessage("Informe a Filial de Origem", "");
        } else if (centroCusto.equals("")) {
            addErrorMessage("Informe o Centro de Custo de Origem", "");
        } else if (filialDestino == 0) {
            addErrorMessage("Informe a Filial de Destino", "");
        } else if (centroCustoDestino.equals("")) {
            addErrorMessage("Informe o Centro de Custo de Destino", "");
        } else if (usuarioSapiens.equals("")){
            addErrorMessage("Informe usuário do Sapiens", "");
        } else if (senhaSapiens.equals("")){
            addErrorMessage("Informe a senha", "");
        } else {
            //setLancamentos(dao.buscarLancamentos(sdf.format(dtInicio), sdf.format(dtFim), centroCusto));
            setLancamentos(dao.buscarLancamentosFilial(sdf.format(dtInicio), sdf.format(dtFim), centroCusto, filialOrigem));
        }
    }

    /**
     * @return the lancamento
     */
    public Lancamento getLancamento() {
        return lancamento;
    }

    /**
     * @param lancamento the lancamento to set
     */
    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    /**
     * @return the lancamentos
     */
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    /**
     * @param lancamentos the lancamentos to set
     */
    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    /**
     * @return the lancamentosSelecionados
     */
    public List<Lancamento> getLancamentosSelecionados() {
        return lancamentosSelecionados;
    }

    /**
     * @param lancamentosSelecionados the lancamentosSelecionados to set
     */
    public void setLancamentosSelecionados(List<Lancamento> lancamentosSelecionados) {
        this.lancamentosSelecionados = lancamentosSelecionados;
    }

    /**
     * @return the dtInicio
     */
    public Date getDtInicio() {
        return dtInicio;
    }

    /**
     * @param dtInicio the dtInicio to set
     */
    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    /**
     * @return the dtFim
     */
    public Date getDtFim() {
        return dtFim;
    }

    /**
     * @param dtFim the dtFim to set
     */
    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    /**
     * @return the centroCusto
     */
    public String getCentroCusto() {
        return centroCusto;
    }

    /**
     * @param centroCusto the centroCusto to set
     */
    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto;
    }

    /**
     * @return the usuarioSapiens
     */
    public String getUsuarioSapiens() {
        return usuarioSapiens;
    }

    /**
     * @param usuarioSapiens the usuarioSapiens to set
     */
    public void setUsuarioSapiens(String usuarioSapiens) {
        this.usuarioSapiens = usuarioSapiens;
    }

    /**
     * @return the senhaSapiens
     */
    public String getSenhaSapiens() {
        return senhaSapiens;
    }

    /**
     * @param senhaSapiens the senhaSapiens to set
     */
    public void setSenhaSapiens(String senhaSapiens) {
        this.senhaSapiens = senhaSapiens;
    }

    private double buscaValorCriarLote() {

        double valor = 0;
        for (Iterator<Lancamento> iterator = lancamentosSelecionados.iterator(); iterator.hasNext();) {
            Lancamento l = iterator.next();
            valor += l.getVlrRat1();

        }

        return valor;
    }

    /**
     * @return the dataLote
     */
    public Date getDataLote() {
        return dataLote;
    }

    /**
     * @param dataLote the dataLote to set
     */
    public void setDataLote(Date dataLote) {
        this.dataLote = dataLote;
    }

    private String trocaParaFilialCorrespondente(String centroCusto) {
        String retorno = "";

        switch (centroCusto) {
            case "1096":
                retorno = "7";
                break;
            case "1091":
                retorno = "4";
                break;
            case "1092":
                retorno = "8";
                break;
            case "1098":
                retorno = "9";
                break;
            case "1094":
                retorno = "10";
                break;
            case "1093":
                retorno = "11";
                break;
            case "951":
                retorno = "12";
                break;
            case "1095":
                retorno = "13";
                break;
            case "1097":
                retorno = "15";
                break;
            case "10470":
                retorno = "19";
                break;
            case "10480":
                retorno = "21";
                break;                           
            case "11290":
                retorno = "24";
                break;                           
        }

        return retorno;

    }

    /**
     * @return the centroCustoDestino
     */
    public String getCentroCustoDestino() {
        return centroCustoDestino;
    }

    /**
     * @param centroCustoDestino the centroCustoDestino to set
     */
    public void setCentroCustoDestino(String centroCustoDestino) {
        this.centroCustoDestino = centroCustoDestino;
    }

    /**
     * @return the filialOrigem
     */
    public int getFilialOrigem() {
        return filialOrigem;
    }

    /**
     * @param filialOrigem the filialOrigem to set
     */
    public void setFilialOrigem(int filialOrigem) {
        this.filialOrigem = filialOrigem;
    }

    /**
     * @return the filialDestino
     */
    public int getFilialDestino() {
        return filialDestino;
    }

    /**
     * @param filialDestino the filialDestino to set
     */
    public void setFilialDestino(int filialDestino) {
        this.filialDestino = filialDestino;
    }

    /**
     * @return the filiais
     */
    public Map<String,String> getFiliais() {
        return filiais;
    }

    /**
     * @param filiais the filiais to set
     */
    public void setFiliais(Map<String,String> filiais) {
        this.filiais = filiais;
    }

    /**
     * @return the centroCustosOrigem
     */
    public Map<String,String> getCentroCustosOrigem() {
        return centroCustosOrigem;
    }

    /**
     * @param centroCustosOrigem the centroCustosOrigem to set
     */
    public void setCentroCustosOrigem(Map<String,String> centroCustosOrigem) {
        this.centroCustosOrigem = centroCustosOrigem;
    }

    /**
     * @return the centroCustosDestino
     */
    public Map<String,String> getCentroCustosDestino() {
        return centroCustosDestino;
    }

    /**
     * @param centroCustosDestino the centroCustosDestino to set
     */
    public void setCentroCustosDestino(Map<String,String> centroCustosDestino) {
        this.centroCustosDestino = centroCustosDestino;
    }

}
