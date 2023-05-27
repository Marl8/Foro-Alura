package com.alura.foro.controller;

import com.alura.foro.dto.ActualizarTopicosDto;
import com.alura.foro.dto.ListaTopicosDto;
import com.alura.foro.dto.TopicosDto;
import com.alura.foro.dto.TopicosPorIdDto;
import com.alura.foro.modelo.Topico;
import com.alura.foro.repository.TopicosRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    private TopicosRepository topicosRepository;

    public TopicosController(TopicosRepository topicosRepository) {
        this.topicosRepository = topicosRepository;
    }

    @PostMapping
    public ResponseEntity<TopicosPorIdDto> crearTopico (@RequestBody TopicosDto topicosDto,
                                                        UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicosRepository.save(new Topico(topicosDto));
        TopicosPorIdDto topicosPorId = new TopicosPorIdDto(topico.getId(),topico.getTitulo(),
                topico.getMensaje(),topico.getAutor(),topico.getCurso());
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(topicosPorId);
    }

    @GetMapping
    public ResponseEntity<Page<ListaTopicosDto>> mostrarTopicos (@PageableDefault(size = 10) Pageable paginacion) {
       return ResponseEntity.ok(topicosRepository.findByActivoTrue(paginacion).map(ListaTopicosDto::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicosPorIdDto> updateTopicos (@RequestBody ActualizarTopicosDto actualizarTopicosDto) {

        Topico topico = topicosRepository.getReferenceById(actualizarTopicosDto.id());
        topico.actualizarTopico(actualizarTopicosDto);
        return ResponseEntity.ok(new TopicosPorIdDto(topico.getId(),topico.getTitulo(),
                topico.getMensaje(),topico.getAutor(),topico.getCurso()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicosPorIdDto> buscarPorId (@PathVariable Long id) {

        Topico topico = topicosRepository.getReferenceById(id);
        TopicosPorIdDto topicosPorId = new TopicosPorIdDto(topico.getId(),topico.getTitulo(),
                topico.getMensaje(),topico.getAutor(),topico.getCurso());
        return ResponseEntity.ok(topicosPorId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminar (@PathVariable Long id) {

        Topico topico = topicosRepository.getReferenceById(id);
        topicosRepository.delete(topico);
        return ResponseEntity.ok().build();
    }
}
