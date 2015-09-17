package br.iesb.sie.controller;

import br.iesb.sie.bean.UsuarioLogado;
import br.iesb.sie.entity.Entidade;
import br.iesb.sie.entity.Funcionario;
import br.iesb.sie.service.EntidadeService;
import br.iesb.sie.service.FuncionarioService;
import br.iesb.sie.util.Attributes;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
@ViewScoped
public class ListarFuncionariosController extends ListarController {

    @Inject
    private FuncionarioService funcionarioService;

    @Inject
    private UsuarioLogado usuarioLogado;

    @Inject
    private EntidadeService entidadeService;

    private List<Funcionario> funcionarios;

    private Funcionario filtro = new Funcionario();

    @PostConstruct
    public void init() {
        if (usuarioLogado.isEscola()) {
            filtro.setEscola(usuarioLogado.getEntidade());
            filtrar();
        }
    }

    @Override
    public void filtrar() {
        funcionarios = funcionarioService.buscarFuncionarios(filtro);
    }

    public List<Entidade> buscarPessoasPorPerfil() {
        if (filtro.getPerfil() != null) {
            return entidadeService.buscarEntidadesPorPerfil(filtro.getPerfil());
        } else {
            return Collections.emptyList();
        }
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Funcionario getFiltro() {
        return filtro;
    }

    public void setFiltro(Funcionario filtro) {
        this.filtro = filtro;
    }
}
