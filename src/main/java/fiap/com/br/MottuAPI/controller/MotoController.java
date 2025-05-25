package fiap.com.br.MottuAPI.controller;

import fiap.com.br.MottuAPI.model.Moto;
import fiap.com.br.MottuAPI.model.StatusMoto;
import fiap.com.br.MottuAPI.model.Usuario;
import fiap.com.br.MottuAPI.model.dto.MotoRequest;
import fiap.com.br.MottuAPI.model.dto.MotoResponse;
import fiap.com.br.MottuAPI.repository.MotoRepository;
import fiap.com.br.MottuAPI.specification.MotoSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    MotoRepository motoRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('MECANICO')")
    @CacheEvict(value = {"motosTodas", "motosDisponiveis"}, allEntries = true)
    public MotoResponse cadastrar(@RequestBody @Valid MotoRequest request,
                                  @AuthenticationPrincipal Usuario mecanico) {
        Moto moto = Moto.builder()
                .modelo(request.modelo())
                .marca(request.marca())
                .status(request.status())
                .problema(request.problema())
                .ano(request.ano())
                .precoPorDia(request.precoPorDia())
                .mecanicoResponsavel(mecanico)
                .build();

        Moto salva = motoRepository.save(moto);

        return new MotoResponse(
            salva.getId(),
            salva.getModelo(),
            salva.getMarca(),
            salva.getStatus(),
            salva.getProblema(),
            salva.getAno(),
            salva.getPrecoPorDia(),
            salva.getMecanicoResponsavel().getId(),
            salva.getMecanicoResponsavel().getNome()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MECANICO')")
    @CacheEvict(value = {"motosTodas", "motosDisponiveis"}, allEntries = true)
    public Moto atualizarMoto(@PathVariable Long id,
                               @RequestBody @Valid Moto motoAtualizada) {

        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        moto.setModelo(motoAtualizada.getModelo());
        moto.setMarca(motoAtualizada.getMarca());
        moto.setAno(motoAtualizada.getAno());
        moto.setPrecoPorDia(motoAtualizada.getPrecoPorDia());
        moto.setStatus(motoAtualizada.getStatus());
        moto.setProblema(motoAtualizada.getProblema());

        return motoRepository.save(moto);
    }

    @CacheEvict(value = {"motosTodas", "motosDisponiveis"}, allEntries = true)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MECANICO')")
    public void deletarMoto(@PathVariable Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        motoRepository.delete(moto);
    }

    @Cacheable("motosDisponiveis")
    @GetMapping("/disponiveis")
    public List<MotoResponse> listarDisponiveis() {
        return motoRepository.findByStatus(StatusMoto.DISPONIVEL).stream().map(moto ->
                new MotoResponse(
                    moto.getId(),
                    moto.getModelo(),
                    moto.getMarca(),
                    moto.getStatus(),
                    moto.getProblema(),
                    moto.getAno(),
                    moto.getPrecoPorDia(),
                    moto.getMecanicoResponsavel().getId(),
                    moto.getMecanicoResponsavel().getNome()
                )).toList();
    }

    @Cacheable("motosTodas")
    @GetMapping("/todas")
    @PreAuthorize("hasAuthority('MECANICO')")
    public List<MotoResponse> listarTodas() {
        return motoRepository.findAll().stream().map(moto ->
            new MotoResponse(
                moto.getId(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getStatus(),
                moto.getProblema(),
                moto.getAno(),
                moto.getPrecoPorDia(),
                moto.getMecanicoResponsavel().getId(),
                moto.getMecanicoResponsavel().getNome()
            )).toList();
    }

    @GetMapping("/manutencao")
    @PreAuthorize("hasAuthority('MECANICO')")
    public List<MotoResponse> listarEmManutencao() {
        return motoRepository.findByStatus(StatusMoto.EM_MANUTENCAO).stream().map(moto ->
            new MotoResponse(
                moto.getId(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getStatus(),
                moto.getProblema(),
                moto.getAno(),
                moto.getPrecoPorDia(),
                moto.getMecanicoResponsavel().getId(),
                moto.getMecanicoResponsavel().getNome()
            )).toList();
    }

    @GetMapping
    public Page<MotoResponse> buscarComFiltros(
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) StatusMoto status,
            @RequestParam(required = false) Double precoMin,
            @RequestParam(required = false) Double precoMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction) {

        Specification<Moto> spec = MotoSpecification.comModelo(modelo)
            .and(MotoSpecification.comMarca(marca))
            .and(MotoSpecification.comStatus(status))
            .and(MotoSpecification.comPrecoMin(precoMin))
            .and(MotoSpecification.comPrecoMax(precoMax));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));

        return motoRepository.findAll(spec, pageable).map(moto -> new MotoResponse(
                moto.getId(),
                moto.getModelo(),
                moto.getMarca(),
                moto.getStatus(),
                moto.getProblema(),
                moto.getAno(),
                moto.getPrecoPorDia(),
                moto.getMecanicoResponsavel().getId(),
                moto.getMecanicoResponsavel().getNome()
        ));
    }

}