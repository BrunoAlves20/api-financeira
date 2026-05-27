package com.example.api_financeira.Controller;
import com.example.api_financeira.domain.Usuario;
import com.example.api_financeira.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController avisa o Spring que essa classe vai responder requisições HTTP devolvendo JSON
@RestController
@RequestMapping("/api/usuarios") // O caminho oficial da nossa porta de entrada
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // @PostMapping indica que vamos receber dados (Criar algo novo)
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        
        // Manda o service fazer o trabalho pesado (salvar usuário e criar a conta)
        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
        
        // Retorna o status 201 (Created) e os dados do usuário salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }
}