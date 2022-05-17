package com.week3.songcache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;


@SpringBootTest class SongCacheApplicationTests {

  @Test void contextLoads() {}

  @Test
  public void cacheIsWorking() {
    SongCache cache = new SongCacheApplication();
    cache.recordSongPlays("ID-1", 3);
    cache.recordSongPlays("ID-1", 1);
    cache.recordSongPlays("ID-2", 2);
    cache.recordSongPlays("ID-3", 5);
    assertThat(cache.getPlaysForSong("ID-1"), is(4));
    assertThat(cache.getPlaysForSong("ID-9"), is(-1));
    assertThat(cache.getTopNSongsPlayed(2), contains("ID-3",
        "ID-1"));
    assertThat(cache.getTopNSongsPlayed(0), is(empty()));
  }

}
