package com.personal.aws.dynamodb.repository.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.personal.aws.dynamodb.model.Music;
import com.personal.aws.dynamodb.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicRepositoryImpl implements MusicRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    @Override
    public List<Music> getAll() {
        return dynamoDBMapper.scan(Music.class, new DynamoDBScanExpression());
    }

    public Music save(Music music) {
        dynamoDBMapper.save(music);
        return music;
    }

    public List<Music> getAllByArtist(String artist) {
        Music music = new Music();
        music.setArtist(artist);

        DynamoDBQueryExpression<Music> queryExpression = new DynamoDBQueryExpression<Music>()
                .withHashKeyValues(music)
                .withLimit(10); //Limit 4MB

        return dynamoDBMapper.query(Music.class, queryExpression);
    }

    public Music getByArtistAndSongTitle(String artist, String songTitle) {
        return dynamoDBMapper.load(Music.class, artist, songTitle);
    }

    public String deleteByArtistAndSongTitle(String artist, String songTitle) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Music.class, artist, songTitle));
        return "Music: " + songTitle + "by Artist : "+ artist +" Deleted!";
    }

    public String update(String artist, Music music) {
        dynamoDBMapper.save(music,
                new DynamoDBSaveExpression()
        .withExpectedEntry("Artist",
                new ExpectedAttributeValue(
                        new AttributeValue().withS(artist)
                )));
        return artist;
    }
}
