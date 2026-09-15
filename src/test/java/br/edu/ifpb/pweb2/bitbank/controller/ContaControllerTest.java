package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.ContaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContaControllerTest {

    private ContaController controller;
    private ContaService contaService;
    private CorrentistaRepository correntistaRepository;
    private ContaRepository contaRepository;

    @BeforeEach
    void setUp() throws Exception {
        controller = new ContaController();
        correntistaRepository = new CorrentistaRepository();
        contaRepository = new ContaRepository();

        contaService = new ContaService();
        Field ctRepo = ContaService.class.getDeclaredField("contaRepository");
        ctRepo.setAccessible(true);
        ctRepo.set(contaService, contaRepository);

        Field crRepo = ContaService.class.getDeclaredField("correntistaRepository");
        crRepo.setAccessible(true);
        crRepo.set(contaService, correntistaRepository);

        Field ctrlContaService = ContaController.class.getDeclaredField("contaService");
        ctrlContaService.setAccessible(true);
        ctrlContaService.set(controller, contaService);

        Field ctrlCorrRepo = ContaController.class.getDeclaredField("correntistaRepository");
        ctrlCorrRepo.setAccessible(true);
        ctrlCorrRepo.set(controller, correntistaRepository);
    }

    @Test
    void testGetForm() {
        ModelAndView mav = controller.getForm(new ModelAndView());
        assertEquals("contas/form", mav.getViewName());
        assertNotNull(mav.getModel().get("conta"));
    }

    @Test
    void testSaveAndList() {
        Correntista correntista = new Correntista();
        correntista.setNome("Maria");
        correntista.setEmail("maria@email.com");
        correntista.setSenha("123");
        correntistaRepository.save(correntista);

        Conta conta = new Conta();
        conta.setNumero("1111");
        conta.setData(new Date());
        Correntista ref = new Correntista();
        ref.setId(correntista.getId());
        conta.setCorrentista(ref);

        ModelAndView mav = controller.adicioneConta(conta, new ModelAndView());
        assertEquals("contas/list", mav.getViewName());

        List<?> contas = (List<?>) mav.getModel().get("contas");
        assertEquals(1, contas.size());

        ModelAndView mavList = controller.liste(new ModelAndView());
        assertEquals("contas/list", mavList.getViewName());
        List<?> contasDoList = (List<?>) mavList.getModel().get("contas");
        assertEquals(1, contasDoList.size());
    }
}
