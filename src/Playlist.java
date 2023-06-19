import java.util.*;

public class Playlist implements Cloneable, Iterable<Song>, FilteredSongIterable, OrderSongIterable {
    private ArrayList<Song> originalSongs;
    private ArrayList<Song> filteredSongs;
    private ScanningOrder scanningOrder;
    private String filterArtist;
    private Song.Genre filterGenre;
    private int filterDuration;

    /**
     * constructor for the playlist class
     */
    public Playlist() {
        originalSongs = new ArrayList<>();
        filteredSongs = new ArrayList<>();
        scanningOrder = ScanningOrder.ADDING;
        this.filterArtist = null;
        this.filterGenre = null;
        this.filterDuration = -200000;
    }

    public Playlist(ArrayList<Song> songs) {
        originalSongs = new ArrayList<>(songs);
        //filteredSongs = new ArrayList<>(songs);
        scanningOrder = ScanningOrder.ADDING;
        this.filterArtist = null;
        this.filterGenre = null;
        this.filterDuration = -200000;
    }
    /**
     * equals method for the playlist class
     *
     * @param obj the object to compare to the playlist object (must be a playlist)
     * @return true if the playlists are equal, if they contain the same songs, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Playlist otherPlaylist = (Playlist) obj;//casting the object to a playlist
        List<Song> otherSongs = otherPlaylist.getSongs();//getting the songs list of the other playlist
        if (originalSongs.size() != otherSongs.size()) {//if the playlists don't have the same amount of songs
            return false;
        }
        return true;
    }

    /**
     * hashCode method for the playlist class
     *
     * @return the hashcode of the playlist
     */
    @Override
    public int hashCode() {
        int hashTotal = 0;
        for (Song song : originalSongs) {
            hashTotal += song.hashCode();
        }
        return hashTotal;
    }

    /**
     * getter for the songs list
     *
     * @return the songs list
     */
    public ArrayList<Song> getSongs() {
        return originalSongs;
    }

    /**
     * clone method for the playlist class (deep copy)
     *
     * @return a deep copy of the playlist
     */
    public Playlist clone() {
        try {
            Playlist newPlaylist = new Playlist();
            for (Song song : originalSongs) {
                newPlaylist.addSong(song.clone());
            }
            return newPlaylist;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * toString method
     *
     * @return String representation of the playlist
     */
    public String toString() {
        StringJoiner playlist = new StringJoiner(", ", "[", "]");
        for (Song song : originalSongs) {
            playlist.add("(" + song.toString() + ")");
        }
        return playlist.toString();
    }

    /**
     * addSong method
     *
     * @param song the song to be added to the playlist
     * @throws SongAlreadyExistsException if the song already exists in the playlist
     */
    public void addSong(Song song) {
        if (isSongAlreadyExist(song)) {
            throw new SongAlreadyExistsException("Song already exists in the playlist.");
        }
        originalSongs.add(song);
    }

    /**
     * isSongAlreadyExist method checks if a song already exists in the playlist
     *
     * @param song the song to be checked
     * @return true if the song already exists, false otherwise
     */
    private boolean isSongAlreadyExist(Song song) {
        for (Song s : originalSongs) {
            if (s.equals(song)) {
                return true;
            }
        }
        return false;
    }

    /**
     * removeSong method
     *
     * @param song the song to be removed from the playlist
     * @return true if the song was removed, false otherwise
     */
    public boolean removeSong(Song song) {
        if (song == null) {
            return false;
        }
        if (!isSongAlreadyExist(song)) {
            return false;
        }
        originalSongs.remove(song);
        return true;
    }

    public ScanningOrder getScanningOrder() {
        return scanningOrder;
    }

    public String getFilterArtist() {
        return filterArtist;
    }

    public Song.Genre getGenreFilter() {
        return filterGenre;
    }

    public int getDurationFilter() {
        return filterDuration;
    }

    /**
     * filterGenre method
     *
     * @param genre the genre to filter by
     * @return a new playlist containing songs with the specified genre
     */
    @Override
    public Playlist filterGenre(Song.Genre genre) {

        // Create a copy of the filteredSongs list
        ArrayList<Song> songsToRemove = new ArrayList<>(originalSongs);

        for (Song song : originalSongs) {
            if (!song.getGenre().equals(genre)) {
                songsToRemove.add(song);
            }
        }
        originalSongs.removeAll(songsToRemove);
        return new Playlist(songsToRemove);
    }

    @Override
    public Playlist filterArtist(String artist) {
        ArrayList<Song> songsToRemove = new ArrayList<>(originalSongs);

        for (Song song : originalSongs) {
            if (!song.getArtist().equals(artist)) {
                songsToRemove.add(song);
            }
        }
        originalSongs.removeAll(songsToRemove);
        return new Playlist(songsToRemove);
    }

    @Override
    public Playlist filterDuration(int duration) {
        ArrayList<Song> songsToRemove = new ArrayList<>(originalSongs);

        for (Song song : originalSongs) {
            int minutes = song.getDuration().getMinutes();
            int seconds = song.getDuration().getSeconds();
            if ((minutes * 60) + seconds < duration) {
                songsToRemove.add(song);
            }
        }
        originalSongs.removeAll(songsToRemove);
        return new Playlist(songsToRemove);
    }


    @Override
    public void setScanningOrder(ScanningOrder scanningOrder) {
        if (scanningOrder != null) {
            this.scanningOrder = scanningOrder;
        }
    }

    @Override
    public Iterator<Song> iterator() {
        ArrayList<Song> songsToIterate = new ArrayList<>();
        switch (scanningOrder) {
            case NAME:
                songsToIterate = new ArrayList<>(originalSongs);
                songsToIterate.sort(Comparator.comparing(Song::getName));
                break;
            case DURATION:
                songsToIterate = new ArrayList<>(originalSongs);
                songsToIterate.sort(Comparator.comparingInt(Song::getDurationInSeconds));
                break;
            case ADDING:
                songsToIterate = new ArrayList<>(originalSongs);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported scanning order: " + scanningOrder);
        }

        return new PlaylistIterator();
    }

    /**
     * private class for the iterator
     */

    private class PlaylistIterator implements Iterator<Song> {
        private int currentIndex;
        private ArrayList<Song> cloneSongs;
        private ArrayList<Song> filtersArtist;
        private ArrayList<Song> filtersGenre;
        private ArrayList<Song> filtersDuration;

        public PlaylistIterator() {
            this.currentIndex = 0;
            cloneSongs = new ArrayList<>(originalSongs);
            applyFilters();
        }

        private void applyFilters() {
            if (filterArtist != null)
                filtersArtist = filterArtist(filterArtist).getSongs();
            if (filterGenre != null)
                filtersGenre = filterGenre(filterGenre).getSongs();
            if (filterDuration != -200000)
                filtersDuration = filterDuration(filterDuration).getSongs();
            cloneSongs = new ArrayList<>(originalSongs);
            if(filterArtist != null || filterGenre != null || filterDuration != -200000)
                removeFilters();
        }

        private void removeFilters() {
            for (Song song : filtersArtist) {
                originalSongs.remove(song);
            }
            for (Song song : filtersGenre) {
                originalSongs.remove(song);
            }
            for (Song song : filtersDuration) {
                originalSongs.remove(song);
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < cloneSongs.size();
        }

        @Override
        public Song next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more songs in the playlist.");
            }
            return cloneSongs.get(currentIndex++);
        }
    }


}










