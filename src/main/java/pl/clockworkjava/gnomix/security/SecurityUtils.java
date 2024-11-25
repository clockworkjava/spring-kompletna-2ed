package pl.clockworkjava.gnomix.security;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityUtils {

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256("qwevcxblkdfssdffsfsgfdgas".getBytes());
    }
}