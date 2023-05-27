package com.alura.foro.modelo;

import com.alura.foro.dto.ActualizarTopicosDto;
import com.alura.foro.dto.TopicosDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private String mensaje;
    private String autor;
    private String curso;
    private boolean activo;

    public Topico(TopicosDto topicosDto)  {
        this.titulo = topicosDto.titulo();
        this.mensaje = topicosDto.mensaje();
        this.autor = topicosDto.autor();
        this.curso = topicosDto.curso();
    }

    public void actualizarTopico(ActualizarTopicosDto actualizarTopicosDto) {

        if (actualizarTopicosDto.titulo() != null) {
            this.titulo = actualizarTopicosDto.titulo();
        }
        if (actualizarTopicosDto.mensaje() != null) {
            this.mensaje = actualizarTopicosDto.mensaje();
        }
        if (actualizarTopicosDto.autor() != null) {
            this.autor = actualizarTopicosDto.autor();
        }
        if (actualizarTopicosDto.curso() != null) {
            this.curso = actualizarTopicosDto.curso();
        }
    }
}
