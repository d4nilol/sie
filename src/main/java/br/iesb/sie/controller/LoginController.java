package br.iesb.sie.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Model
public class LoginController extends BaseController {

    /**
     * 
     */
    private static final long serialVersionUID = 2601070582054015945L;

    @Inject
    private Logger logger;

    private String login;

    private String senha;

    public String logar() {
        HttpServletRequest request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
        try {
            request.login(login, senha);
            return "/view/index.xhtml?faces-redirect=true";
        } catch (ServletException e) {
            addErrorMessage("Não foi possível autenticar, verifique a matrícula e a senha.");
            logger.log(Level.SEVERE, "Erro ao efetuar o login", e);
        }
        return null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
