package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CorrentistaControllerTest {

    private CorrentistaController controller;
    private CorrentistaRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        controller = new CorrentistaController();
        repository = new CorrentistaRepository();

        Field repoField = CorrentistaController.class.getDeclaredField("correntistaRepository");
        repoField.setAccessible(true);
        repoField.set(controller, repository);
    }

    @Test
    void testSaveComSucesso() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/list", view);
        assertNull(model.getAttribute("mensagem"));
        assertNotNull(model.getAttribute("correntistas"));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testNomeObrigatorioNull() {
        Correntista c = new Correntista();
        c.setNome(null);
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Nome do correntista é obrigatório", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testNomeObrigatorioVazio() {
        Correntista c = new Correntista();
        c.setNome("   ");
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Nome do correntista é obrigatório", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testNomeExcede50Caracteres() {
        Correntista c = new Correntista();
        c.setNome("A".repeat(51));
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Nome do correntista deve ter tamanho máximo de 50 caracteres", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testNomeExatamente50CaracteresAceita() {
        Correntista c = new Correntista();
        c.setNome("A".repeat(50));
        c.setEmail("joao@email.com");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/list", view);
        assertNull(model.getAttribute("mensagem"));
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testSenhaObrigatoriaNull() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha(null);

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Senha é obrigatória", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testSenhaObrigatoriaVazia() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("joao@email.com");
        c.setSenha("   ");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Senha é obrigatória", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testEmailObrigatorioNull() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail(null);
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Email é obrigatório", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }

    @Test
    void testEmailObrigatorioVazio() {
        Correntista c = new Correntista();
        c.setNome("João Silva");
        c.setEmail("   ");
        c.setSenha("123456");

        Model model = new ConcurrentModel();
        String view = controller.save(c, model);

        assertEquals("correntistas/form", view);
        assertEquals("Email é obrigatório", model.getAttribute("mensagem"));
        assertEquals(0, repository.findAll().size());
    }
}
