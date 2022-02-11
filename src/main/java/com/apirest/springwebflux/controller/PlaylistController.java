package com.apirest.springwebflux.controller;

import com.apirest.springwebflux.document.Playlist;
import com.apirest.springwebflux.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping(value = "/playlist")
    public Flux<Playlist> getPlaylist() {
        return playlistService.findAll();
    }

    @GetMapping(value = "/playlist/{id}")
    public Mono<Playlist> getPlayListId(@PathVariable String id) {
        return playlistService.findById(id);
    }

    @PostMapping(value = "/playlist")
    public Mono<Playlist> save(@RequestBody  Playlist playlist) {
        return playlistService.save(playlist);
    }

    @GetMapping(value = "/playlist/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByEvents() {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
        Flux<Playlist> events = playlistService.findAll();
        System.out.println("Passou aqui events");
        return Flux.zip(interval, events);
    }
}
