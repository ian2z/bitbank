package br.edu.ifpb.pweb2.bitbank.service;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorrentistaService implements br.edu.ifpb.pweb2.bitbank.service.Service<Correntista, Integer> {
    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Correntista> findAll() {
        return correntistaRepository.findAll();
    }

    @Override
    public Correntista findById(Integer id) {
        return correntistaRepository.findById(id);
    }

    @Override
    public Correntista save(Correntista c) {
        return correntistaRepository.save(c);
    }
}
