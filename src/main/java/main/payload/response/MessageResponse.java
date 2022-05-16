package main.payload.response;

import lombok.Getter;
import lombok.Setter;

/**
 *  for some message
 */

@Getter
@Setter
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
