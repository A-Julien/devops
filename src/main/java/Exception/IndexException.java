package Exception;

import java.io.Serializable;

public class IndexException extends Exception implements Serializable {
    public IndexException(String errorMessage) {
        super(errorMessage);
    }
}