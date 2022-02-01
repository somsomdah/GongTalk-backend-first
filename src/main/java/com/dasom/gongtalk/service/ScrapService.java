package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.user.Scrap;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.UserForbiddenException;
import com.dasom.gongtalk.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;

    public Scrap getFromId(Integer id){
        Optional<Scrap> scrap = scrapRepository.findById(id);
        try{
            return scrap.get();
        }catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "scrap", "id", id);
        }
    }

    public void checkAuthority(User user, Scrap scrap){
        if (!user.equals(scrap.getUser())){
            throw new UserForbiddenException(String.format("The user has no authority to scrap id %d",scrap.getId()));
        }
    }

    public void checkAllAuthorities(User user, List<Scrap> scraps){

        for(Scrap scrap : scraps){
            checkAuthority(user, scrap);
        }
    }
}
