package com.week3.songcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.concurrent.ConcurrentHashMap;

import java.util.*;

@SpringBootApplication public class SongCacheApplication implements SongCache{

  private final Map<String, Integer> map;

  public static void main(String[] args) {
    SpringApplication.run(SongCacheApplication.class, args);
  }

  public SongCacheApplication(){
    this.map = new ConcurrentHashMap<>();
  }

  @Override
  public synchronized void recordSongPlays(String songId, int numPlays) {
    if(songId != null)
      map.put(songId, map.getOrDefault(songId, 0)+numPlays);
  }

  @Override
  public int getPlaysForSong(String songId) {
    if(songId == null)
      return -1;
    return map.getOrDefault(songId, -1);
  }

  /**
   * By pushing every song in map into an n-size-limited priority queue, we can get the
   * n songs with the highest priority (times being played)
   * pQ is created and only used in this method,to get thread-safe
   */
  @Override
  public List<String> getTopNSongsPlayed(int n) {
    List<String> res = new ArrayList<>();
    if(n <= 0)
      return res;

    PriorityQueue<String> pQ = new PriorityQueue<>(Comparator.comparingInt(map::get));
    // limit size of the priority queue by poll whenever exceeds size limit
    for(String s: map.keySet()){
      pQ.add(s);
      if(pQ.size() > n)
        pQ.poll();
    }
    // poll the pQ one by one, which gives the n-highest songs in increasing order
    while(pQ.size() > 0)
      res.add(pQ.poll());
    // reverse to get the expected result
    Collections.reverse(res);
    return res;
  }

}
