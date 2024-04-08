package server.cps.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@RedisHash(value = "jwtToken", timeToLive = 60*60*24*3)
public class Token {
    @Id
    private String id;
    @Indexed
    private String refreshToken;

    public Token( String id,String refreshToken) {
        this.id=id;
        this.refreshToken = refreshToken;

    }
}
