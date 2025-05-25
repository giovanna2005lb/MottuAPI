package fiap.com.br.MottuAPI.controller;

import fiap.com.br.MottuAPI.model.*;
import fiap.com.br.MottuAPI.model.dto.AluguelRequest;
import fiap.com.br.MottuAPI.repository.AluguelRepository;
import fiap.com.br.MottuAPI.repository.MotoRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alugueis")
public class AluguelController {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private MotoRepository motoRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTE')")
    @CacheEvict(value = "alugueis", allEntries = true)
    public Aluguel alugarMoto(@RequestBody @Valid AluguelRequest request,
                              @AuthenticationPrincipal Usuario cliente) {

        Moto moto = motoRepository.findById(request.motoId())
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        if (moto.getStatus() != StatusMoto.DISPONIVEL) {
            throw new RuntimeException("Moto não está disponível para aluguel");
        }

        moto.setStatus(StatusMoto.ALUGADA);
        motoRepository.save(moto);

        Aluguel aluguel = new Aluguel();
        aluguel.setCliente(cliente);
        aluguel.setMoto(moto);
        aluguel.setDataInicio(request.dataInicio());
        aluguel.setDataFim(request.dataFim());

        return aluguelRepository.save(aluguel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @CacheEvict(value = "alugueis", allEntries = true)
    public Aluguel reagendarAluguel(@PathVariable Long id,
                                    @RequestBody @Valid AluguelRequest request,
                                    @AuthenticationPrincipal Usuario cliente) {

        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));

        if (!aluguel.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Você só pode editar seus próprios aluguéis");
        }

        aluguel.setDataInicio(request.dataInicio());
        aluguel.setDataFim(request.dataFim());

        return aluguelRepository.save(aluguel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTE')")
    @CacheEvict(value = "alugueis", allEntries = true)
    public void cancelarAluguel(@PathVariable Long id,
                                @AuthenticationPrincipal Usuario cliente) {

        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluguel não encontrado"));

        if (!aluguel.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Você só pode cancelar seus próprios aluguéis");
        }

        Moto moto = aluguel.getMoto();
        moto.setStatus(StatusMoto.DISPONIVEL);
        motoRepository.save(moto);

        aluguelRepository.delete(aluguel);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('MECANICO')")
    @Cacheable("alugueis")
    public Page<Aluguel> listarAlugueis(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sort,
                                        @RequestParam(defaultValue = "asc") String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        return aluguelRepository.findAll(pageable);
    }
} 