import java.util.ArrayList;
import java.util.Iterator;

public interface FilteredSongIterable extends Iterable<Song> {
    /**
     * Filters the songs in the playlist by genre and returns a new filtered playlist.
     *
     * @param genre the genre to filter by
     * @return a new playlist containing songs with the specified genre
     */
    Playlist filterGenre(Song.Genre genre);

    /**
     * Filters the songs in the playlist by artist and returns a new filtered playlist.
     *
     * @param artist the artist to filter by
     * @return a new playlist containing songs by the specified artist
     */
    Playlist filterArtist(String artist);

    /**
     * Filters the songs in the playlist by duration and returns a new filtered playlist.
     *
     * @param duration the duration to filter by
     * @return a new playlist containing songs with the specified duration
     */
    Playlist filterDuration(int duration);

    @Override
    Iterator<Song> iterator();
}
