package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.Scrap;
import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.exception.UserForbiddenException;
import com.dasom.gongtalk.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public Scrap getFromId(Integer id){
        Optional<Scrap> scrap = scrapRepository.findById(id);
        try{
            return scrap.get();
        }catch (Exception e){
            throw new ResourceNotFoundException("scrap", "id", id, e.toString());
        }
    }

    public void checkAuthority(User user, Scrap scrap){
        if (!user.equals(scrap.getUser())){
            throw new UserForbiddenException(String.format("The user has no authority to scrap id %d",scrap.getId()));
        }
    }

    public Scrap save(User user, Post post){
        try {
            Scrap scrap = new Scrap(user, post);
            scrapRepository.save(scrap);
            return scrap;
        }catch (Exception e){
            throw new SqlException("Scrap create error", e.toString());
        }
    }
}
