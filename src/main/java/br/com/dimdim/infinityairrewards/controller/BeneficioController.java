package br.com.dimdim.infinityairrewards.controller;

import br.com.dimdim.infinityairrewards.model.Beneficio;
import br.com.dimdim.infinityairrewards.repository.BeneficioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beneficios")
public class BeneficioController {

    private final BeneficioRepository repository;

    public BeneficioController(BeneficioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Beneficio> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beneficio> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Beneficio> criar(@Valid @RequestBody Beneficio beneficio) {
        beneficio.setId(null);

        if (beneficio.getProdutoComprado() == null || beneficio.getProdutoComprado().isBlank()) {
            beneficio.setProdutoComprado("iPhone Infinity Air");
        }

        if (beneficio.getStatusBeneficio() == null || beneficio.getStatusBeneficio().isBlank()) {
            beneficio.setStatusBeneficio("APROVADO");
        }

        Beneficio salvo = repository.save(beneficio);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beneficio> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Beneficio dados
    ) {
        return repository.findById(id)
                .map(beneficio -> {
                    beneficio.setNomeCliente(dados.getNomeCliente());
                    beneficio.setEmailCliente(dados.getEmailCliente());
                    beneficio.setProdutoComprado(dados.getProdutoComprado());
                    beneficio.setDataCompra(dados.getDataCompra());
                    beneficio.setStatusBeneficio(dados.getStatusBeneficio());

                    Beneficio atualizado = repository.save(beneficio);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}