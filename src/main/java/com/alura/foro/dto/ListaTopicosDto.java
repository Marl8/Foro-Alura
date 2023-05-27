package com.alura.foro.dto;

import com.alura.foro.modelo.Topico;

public record ListaTopicosDto(Long id, String titulo, String mensaje, String autor, String curso) {

    public ListaTopicosDto(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor(), topico.getCurso());
    }
}
