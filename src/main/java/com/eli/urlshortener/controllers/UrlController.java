package com.eli.urlshortener.controllers;

import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.models.UrlResponse;
import com.eli.urlshortener.repositories.UrlRepository;
import com.eli.urlshortener.repositories.UserRepository;
import com.eli.urlshortener.utility.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;

    @GetMapping(value = "/{code}")
    public ResponseEntity<Void> getUrlAndRedirect(@PathVariable("code") String code){
        Optional<Url> optional = urlRepository.findById(Base62.decode(code));

        if(optional.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.SEE_OTHER)
                    .location(URI.create("https://" + optional.get().getUrl()))
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse addUrl(@RequestBody Url url){
        userRepository.save(url.getOwner());
        Url savedUrl = urlRepository.save(url);

        return new UrlResponse(savedUrl.getOwner(), savedUrl.getUrl(), new Base62(Base62.encode(savedUrl.getId())));
    }
}
