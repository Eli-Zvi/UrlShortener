package com.eli.urlshortener.services;

import com.eli.urlshortener.DTOs.UrlDto;
import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.exceptions.UrlAlreadyExistsException;
import com.eli.urlshortener.mappers.UrlMapper;
import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.models.UrlResponse;
import com.eli.urlshortener.repositories.UrlRepository;
import com.eli.urlshortener.repositories.UserRepository;
import com.eli.urlshortener.utility.Base62;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;
    private final UrlMapper urlMapper;

    public Url addUrl(Url url){
        userRepository.save(url.getOwner());

        return urlRepository.save(url);
    }

    public void deleteUrl(String code){
        Long id = Base62.decode(code);

        if(!urlRepository.existsById(id)){
            throw new ShortCodeNotFoundException(String.format("Short code %s does not exist in the database", code));
        }

        urlRepository.deleteById(id);
    }

    public Url getUrl(String code){
        Long id = Base62.decode(code);

        Optional<Url> optional = urlRepository.findById(id);

        return optional.orElseThrow(() ->
                new ShortCodeNotFoundException(String.format("Short code %s does not exist in the database", code)));
    }

    public List<Url> getUrls(){
        return urlRepository.findAll();
    }
}
