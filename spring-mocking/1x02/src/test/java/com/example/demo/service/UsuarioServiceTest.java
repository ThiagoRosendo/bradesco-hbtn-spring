package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        Long idExistente = 1L;
        Usuario usuarioMock = new Usuario(idExistente, "João Silva", "joao@email.com");
        when(usuarioRepository.findById(idExistente)).thenReturn(Optional.of(usuarioMock));

        Usuario resultado = usuarioService.buscarUsuarioPorId(idExistente);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(idExistente);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        Long idInexistente = 99L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarUsuarioPorId(idInexistente);
        });

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Usuario usuarioParaSalvar = new Usuario(null, "Maria Oliveira", "maria@email.com");
        Usuario usuarioSalvo = new Usuario(1L, "Maria Oliveira", "maria@email.com");

        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        Usuario resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Maria Oliveira", resultado.getNome());
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }
}