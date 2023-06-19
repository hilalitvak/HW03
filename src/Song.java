
public class Song extends Object implements Cloneable {
    private String artist;
    private String name;
    private Time duration;
    private Genre genre;

    /** constructor for Song class
     *
     * @param artist artist name
     * @param name song name
     * @param duration song duration hours:minutes
     * @param genre song genre (enum)
     */
    public Song(String name, String artist,Genre genre, int duration) {
        this.artist = artist;
        this.name = name;
        this.duration= new Time(0,0);
        this.duration.setTime(duration);
        this.genre = genre;
    }

    public Time getDuration() {
        return duration;
    }

    /** builds hashcode for Song class
     *
     * @return hashcode of the song
     */
    public int hashcode(){
        return this.name.hashCode()+this.artist.hashCode()+this.genre.hashCode()+this.duration.hashCode();
    }

    /** checks if two songs are equal
     *
     * @param o object to compare to
     * @return true if the songs are equal, false otherwise
     */
    public boolean equals(Object o){
        if(o==null){ //if the object is null
            return false;
        }
        if(o==this){ //if the object is the same object
            return true;
        }
        if(o.getClass()!=this.getClass()){//if the object is not a song
            return false;
        }
        Song s = (Song) o;
        return this.name.equals(s.name) && this.artist.equals(s.artist);
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }
    public int getDurationInSeconds(){
        return this.duration.getSeconds() + (this.duration.getMinutes()*60);
    }

    public Genre getGenre() {
        return genre;
    }

    /** clone constructor for Song class
     *
     * @return new song with the same attributes
     */
    public Song clone(){
        try{
            return new Song(this.name,this.artist,this.genre,this.duration.getSeconds() + (this.duration.getMinutes()*60));
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     *
     * @param newDuration new duration of the song in seconds
     */
    public void setDuration(int newDuration) {
        int minutes = newDuration / 60;
        int seconds = newDuration % 60;
        this.duration=new Time(minutes,seconds);
    }
    /**
     *
     * @param newGenre new genre of the song
     */
    public void setGenre(Genre newGenre) {
        this.genre=newGenre;
    }

    /**
     *
     * @return string of the important attributes of the song
     */
    public String toString(){
        return this.name+", "+this.artist+", "+this.genre+", "+this.duration.toString();
    }

    public enum Genre {
        POP,
        ROCK,
        HIP_HOP,
        COUNTRY,
        JAZZ,
        DISCO;
    }

}
