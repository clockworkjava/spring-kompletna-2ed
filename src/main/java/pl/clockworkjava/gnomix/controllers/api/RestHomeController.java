package pl.clockworkjava.gnomix.controllers.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class RestHomeController {

    @GetMapping
    public String root() {
        return "OK";
    }
}
