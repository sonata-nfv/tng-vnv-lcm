package eu.h2020_5gtango.vnv.lcm.model

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.Instant

class Session {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss 'UTC'")
    Date session_began_at
    String username
    Token token

    static class Token {
        String access_token
        int expires_in
    }

    boolean invalid() {
        Instant.now().toEpochMilli() - session_began_at.getTime() > token.expires_in * 1000
    }
}
