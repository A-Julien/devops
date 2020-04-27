package Exception;

import java.io.Serializable;

public class FrameOutOfBound extends Exception implements Serializable {
    public FrameOutOfBound(String errorMessage) {
        super(errorMessage);
    }
}