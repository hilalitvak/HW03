/**
 * SongAlreadyExistException
 * exception for when a song already exists in the system (same name and artist)
 */
public class SongAlreadyExistsException extends RuntimeException {
    public SongAlreadyExistsException() {
        super();
    }

    public SongAlreadyExistsException(String message) {
        super(message);
    }

    public SongAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}