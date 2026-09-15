package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorrentistaControllerTest {

    private CorrentistaController controller;
    private CorrentistaService service;
    private CorrentistaRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CorrentistaController();
        repository = new CorrentistaRepository();
        service = new CorrentistaService();

        Field repoInService = CorrentistaService.class.getDeclaredField("correntistaRepository");
        repoInService.setAccessible(true);
        repoInService.set(service, repository);

        Field serviceInCtrl = CorrentistaController.class.getDeclaredField("correntistaService");
        serviceInCtrl.setAccessible(true);
        serviceInCtrl.set(controller, service);
    }

    @Test
    void testGetForm() {
        ModelAndView mav = controller.getForm(new ModelAndView());
        assertEquals("correntistas/form", mav.getViewName());
        assertNotNull(mav.getModel().get("correntista"));
    }

    @Test
    void testList() {
        Correntista c = new Correntista();
        c.setNome("Carlos");
        c.setEmail("carlos@email.com");
        c.setSenha("123456");
        service.save(c);

        ModelAndView mav = controller.list(new ModelAndView());
        assertEquals("correntistas/list", mav.getViewName());
        List<?> list = (List<?>) mav.getModel().get("correntistas");
        assertEquals(1, list.size());
    }

    @Test
    void testSaveComSucesso() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/list", mav.getViewName());
        assertNull(mav.getModel().get("mensagem"));
        assertNotNull(mav.getModel().get("correntistas"));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void testNomeObrigatorioNull() {
        Correntista c = new Correntista();
        c.setNome(null);
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Nome do correntista é obrigatório", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testNomeObrigatorioVazio() {
        Correntista c = new Correntista();
        c.setNome("   ");
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Nome do correntista é obrigatório", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testNomeExcede50Caracteres() {
        Correntista c = new Correntista();
        c.setNome("A".repeat(51));
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Nome do correntista deve ter tamanho máximo de 50 caracteres", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testNomeExatamente50CaracteresAceita() {
        Correntista c = new Correntista();
        c.setNome("A".repeat(50));
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/list", mav.getViewName());
        assertNull(mav.getModel().get("mensagem"));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void testSenhaObrigatoriaNull() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha(null);

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Senha é obrigatória", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testSenhaObrigatoriaVazia() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha("   ");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Senha é obrigatória", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testEmailObrigatorioNull() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail(null);
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Email é obrigatório", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }

    @Test
    void testEmailObrigatorioVazio() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("   ");
        c.setSenha("123456");

        ModelAndView mav = controller.save(c, new ModelAndView());

        assertEquals("correntistas/form", mav.getViewName());
        assertEquals("Email é obrigatório", mav.getModel().get("mensagem"));
        assertEquals(0, service.findAll().size());
    }
}
