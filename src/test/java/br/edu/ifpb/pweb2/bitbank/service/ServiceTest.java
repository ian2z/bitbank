package br.edu.ifpb.pweb2.bitbank.service;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private CorrentistaRepository correntistaRepository;
    private ContaRepository contaRepository;
    private CorrentistaService correntistaService;
    private ContaService contaService;

    @BeforeEach
    void setUp() throws Exception {
        correntistaRepository = new CorrentistaRepository();
        contaRepository = new ContaRepository();

        correntistaService = new CorrentistaService();
        Field cRepo = CorrentistaService.class.getDeclaredField("correntistaRepository");
        cRepo.setAccessible(true);
        cRepo.set(correntistaService, correntistaRepository);

        contaService = new ContaService();
        Field ctRepo = ContaService.class.getDeclaredField("contaRepository");
        ctRepo.setAccessible(true);
        ctRepo.set(contaService, contaRepository);

        Field crRepo = ContaService.class.getDeclaredField("correntistaRepository");
        crRepo.setAccessible(true);
        crRepo.set(contaService, correntistaRepository);
    }

    @Test
    void testCorrentistaServiceSaveAndFind() {
        Correntista c = new Correntista();
        c.setNome("Carlos");
        c.setEmail("carlos@email.com");
        c.setSenha("123");

        Correntista saved = correntistaService.save(c);
        assertNotNull(saved.getId());

        Correntista found = correntistaService.findById(saved.getId());
        assertEquals("Carlos", found.getNome());

        List<Correntista> all = correntistaService.findAll();
        assertEquals(1, all.size());
    }

    @Test
    void testContaServiceSaveAndFind() {
        Correntista c = new Correntista();
        c.setNome("Ana");
        c.setEmail("ana@email.com");
        c.setSenha("123");
        correntistaService.save(c);

        Conta conta = new Conta();
        conta.setNumero("12345-6");
        conta.setData(new Date());
        Correntista ref = new Correntista();
        ref.setId(c.getId());
        conta.setCorrentista(ref);

        Conta saved = contaService.save(conta);
        assertNotNull(saved.getId());
        assertNotNull(saved.getCorrentista());
        assertEquals("Ana", saved.getCorrentista().getNome());

        Conta found = contaService.findById(saved.getId());
        assertEquals("12345-6", found.getNumero());

        List<Conta> all = contaService.findAll();
        assertEquals(1, all.size());
    }
}
