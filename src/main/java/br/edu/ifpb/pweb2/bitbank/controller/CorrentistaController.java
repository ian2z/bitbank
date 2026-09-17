package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    @Autowired
    private CorrentistaService correntistaService;

    @GetMapping("/form")
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("correntistas/form");
        modelAndView.addObject("correntista", new Correntista());
        return modelAndView;
    }

    @PostMapping({"", "/save"})
    public ModelAndView save(Correntista correntista, RedirectAttributes redirectAttributes) {
        String erro = validar(correntista);
        if (erro != null) {
            ModelAndView modelAndView = new ModelAndView("correntistas/form");
            modelAndView.addObject("mensagem", erro);
            modelAndView.addObject("correntista", correntista);
            return modelAndView;
        }

        correntistaService.save(correntista);
        redirectAttributes.addFlashAttribute("mensagem", "Correntista salvo com sucesso!");
        return new ModelAndView ("redirect:/correntistas");
    }

    @GetMapping({"", "/list"})
    public ModelAndView list(ModelAndView modelAndView) {
        modelAndView.setViewName("correntistas/list");
        modelAndView.addObject("correntistas", correntistaService.findAll());
        return modelAndView;
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
