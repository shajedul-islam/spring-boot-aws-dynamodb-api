package com.personal.aws.dynamodb.controller;

import com.personal.aws.dynamodb.model.Music;
import com.personal.aws.dynamodb.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    @Autowired
    private MusicRepository musicRepository;

    @GetMapping
    public List<Music> getAll() {
        return musicRepository.getAll();
    }

    @PostMapping
    public Music saveMusic(@RequestBody Music music) {
        return musicRepository.save(music);
    }

    @GetMapping("/{artist}")
    public List<Music> getMusicByArtist(@PathVariable("artist") String artist) {
        return musicRepository.getAllByArtist(artist);
    }

    @GetMapping("/{artist}/{songTitle}")
    public Music getMusicByArtistAndSongTitle(@PathVariable("artist") String artist,
                                              @PathVariable("songTitle") String songTitle) {
        return musicRepository.getByArtistAndSongTitle(artist, songTitle);
    }

    @DeleteMapping("/{artist}/{songTitle}")
    public String deleteMusicByArtistAndSongTitle(@PathVariable("artist") String artist,
                                                  @PathVariable("songTitle") String songTitle) {
        return  musicRepository.deleteByArtistAndSongTitle(artist, songTitle);
    }

    @PutMapping("/{artist}")
    public String updateMusic(@PathVariable("artist") String artist, @RequestBody Music music) {
        return musicRepository.update(artist, music);
    }
}
