package br.com.dimdim.infinityairrewards.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "BENEFICIOS_AIRDOCK")
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "NOME_CLIENTE", nullable = false, length = 100)
    private String nomeCliente;

    @Email
    @NotBlank
    @Column(name = "EMAIL_CLIENTE", nullable = false, length = 150)
    private String emailCliente;

    @NotBlank
    @Column(name = "PRODUTO_COMPRADO", nullable = false, length = 100)
    private String produtoComprado;

    @Column(name = "DATA_COMPRA", nullable = false)
    private LocalDate dataCompra;

    @NotBlank
    @Column(name = "STATUS_BENEFICIO", nullable = false, length = 30)
    private String statusBeneficio;

    public Beneficio() {
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getProdutoComprado() {
        return produtoComprado;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public String getStatusBeneficio() {
        return statusBeneficio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public void setProdutoComprado(String produtoComprado) {
        this.produtoComprado = produtoComprado;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setStatusBeneficio(String statusBeneficio) {
        this.statusBeneficio = statusBeneficio;
    }
}