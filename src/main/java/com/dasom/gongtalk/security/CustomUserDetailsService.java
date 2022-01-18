package com.dasom.gongtalk.security;
import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Setting;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.BookmarkRepository;
import com.dasom.gongtalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.{UserDetailsService => SecurityUserDetailsService};

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements  {

    private UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final Principal principal;

    public User saveUser(String deviceNum){
        User createdUser = new User();
        createdUser.setDeviceNum(deviceNum);
        createdUser.setUsername();
        createdUser.setPassword();
        createdUser.setSetting(new Setting());

        return createdUser;
    }



    public List<Board> getBookmarkedBoards(User user) {
        return bookmarkRepository.findBoardsByUser(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return principal.buildCredentials(userRepository.findByUsername(username));
    }


}
