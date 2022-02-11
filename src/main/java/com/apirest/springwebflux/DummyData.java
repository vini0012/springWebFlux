package com.apirest.springwebflux;

import com.apirest.springwebflux.document.Playlist;
import com.apirest.springwebflux.repository.PlaylistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public class DummyData implements CommandLineRunner {

    private final PlaylistRepository playlistRepository;

    public DummyData(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        playlistRepository.deleteAll()
                .thenMany(Flux.just("Aprenda a baixar o aplicativo IFood", "Como fazer um pedido no IFood?", "O melhor aplicativo de delivery",
                        "Beneficie seus colaborades com cupons no IFood")
                        .map(nome -> new Playlist(UUID.randomUUID().toString(), nome))
                        .flatMap(playlistRepository::save))
                .subscribe(System.out::println);
    }
}
