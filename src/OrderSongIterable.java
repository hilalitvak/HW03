import java.util.Iterator;

public interface OrderSongIterable extends Iterable<Song> {

    /**
     * Sets the scanning order for the playlist.
     *
     * @param scanningOrder the scanning order to set
     */
    void setScanningOrder(ScanningOrder scanningOrder);

    @Override
    Iterator<Song> iterator();
}
