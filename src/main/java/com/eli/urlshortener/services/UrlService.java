package com.eli.urlshortener.services;

import com.eli.urlshortener.exceptions.ShortCodeNotFoundException;
import com.eli.urlshortener.mappers.UrlMapper;
import com.eli.urlshortener.models.AppUser;
import com.eli.urlshortener.models.Url;
import com.eli.urlshortener.repositories.UrlRepository;
import com.eli.urlshortener.repositories.UserRepository;
import com.eli.urlshortener.utility.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    private final UserRepository userRepository;
    private final UrlRepository urlRepository;

    public Url addUrl(Url url){
        Optional<AppUser> optionalUser = userRepository
                .getAppUserByUsername(url.getOwner().getUsername());

        if(optionalUser.isPresent()){
            url.setOwner(optionalUser.get());
        }else userRepository.save(url.getOwner());

        url.setOriginalUrl(normalizeUrl(url.getOriginalUrl()));

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

    /*
    Add https if it is unspecified in the link to make redirection seamless
     */
    private String normalizeUrl(String originalUrl){
        return originalUrl.startsWith("http") ? originalUrl : "https://" + originalUrl;
    }
}
