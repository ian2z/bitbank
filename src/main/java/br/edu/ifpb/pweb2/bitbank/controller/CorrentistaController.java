package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @RequestMapping("/form")
    public String getForm(Correntista correntista, Model model) {
        model.addAttribute("correntista", correntista);
        return "correntistas/form";
    }

    @RequestMapping("/save")
    public String save(Correntista correntista, Model model) {
        String erro = validar(correntista);
        if (erro != null) {
            model.addAttribute("mensagem", erro);
            model.addAttribute("correntista", correntista);
            return "correntistas/form";
        }

        correntistaRepository.save(correntista);
        model.addAttribute("correntistas", correntistaRepository.findAll());
        return "correntistas/list";
    }

    private String validar(Correntista correntista) {
        if (correntista == null || correntista.getNome() == null || correntista.getNome().trim().isEmpty()) {
            return "Nome do correntista é obrigatório";
        }
        if (correntista.getNome().trim().length() > 50) {
            return "Nome do correntista deve ter tamanho máximo de 50 caracteres";
        }
        if (correntista.getSenha() == null || correntista.getSenha().trim().isEmpty()) {
            return "Senha é obrigatória";
        }
        if (correntista.getEmail() == null || correntista.getEmail().trim().isEmpty()) {
            return "Email é obrigatório";
        }
        return null;
    }
}
