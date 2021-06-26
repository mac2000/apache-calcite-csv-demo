package ua.net.marchenko.csv.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class QueryController {
    @PostMapping("/query")
    public List<Map<String, Object>> query(HttpEntity<String> httpEntity) {
        log.debug("Running: {}", httpEntity.getBody());
        return new ArrayList<>();
    }
}
