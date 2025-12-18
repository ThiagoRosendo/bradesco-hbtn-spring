package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        Long idExistente = 1L;
        Produto produtoEsperado = new Produto(idExistente, "Teclado Mecânico", 250.0);

        when(produtoRepository.findById(idExistente)).thenReturn(Optional.of(produtoEsperado));

        Produto resultado = produtoService.buscarPorId(idExistente);

        assertNotNull(resultado);
        assertEquals(idExistente, resultado.getId());
        assertEquals("Teclado Mecânico", resultado.getNome());
        verify(produtoRepository, times(1)).findById(idExistente);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {

        Long idInexistente = 99L;
        when(produtoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.buscarPorId(idInexistente);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoRepository, times(1)).findById(idInexistente);
    }
}