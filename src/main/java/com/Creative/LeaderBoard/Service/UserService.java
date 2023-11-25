package com.Creative.LeaderBoard.Service;

import com.Creative.LeaderBoard.Entity.Users;
import com.Creative.LeaderBoard.Repository.UserReponsitory;
import com.Creative.LeaderBoard.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;



@Service
public class UserService {
    @Autowired
    private UserReponsitory userReponsitory;



    public List<Users> findall(){
        return  userReponsitory.findAll();
    }

    public Users getUserById(Long id) {
        return userReponsitory.findById(id).orElse(null);
    }

    public Users SaveUser(long id, String username, int score) {
        Users user = getUserById(id);
        if(user == null){
            Users newuser = new Users(id,username,score,new Date());
            return userReponsitory.save(newuser);
        }
        else if(score>user.getScore()){
           user.setScore(score);
            return userReponsitory.save(user);
        }
        else return user;
    }


    public List<Users> getTopRanking(int top){
        Pageable pageable = (Pageable) PageRequest.of(0,top, Sort.by("score").descending());
        return userReponsitory.findAllByOrderByScoreDescTimeSubmitDesc(pageable);
    }



    public List<UserResponse> getTopUser(int top){
        List<Users> topuser = getTopRanking(top);
        AtomicInteger rank = new AtomicInteger(0);

        int prevScore = Integer.MAX_VALUE;  // Initial value to handle the first iteration

        List<UserResponse> result = topuser.stream()
                .sorted(Comparator.comparing(Users::getScore, Comparator.reverseOrder())
                        .thenComparing(Users::getTimeSubmit, Comparator.reverseOrder()))
                .peek(userScore -> {
                    if (userScore.getScore() != prevScore) {
                        // If the score is different from the previous one, update the rank
                        rank.incrementAndGet();
                    }
                })
                .map(userScore -> new UserResponse(
                        userScore.getId(),
                        userScore.getUsername(),
                        userScore.getScore(),
                        userScore.getTimeSubmit(),
                        String.valueOf(rank.get())
                ))
                .collect(Collectors.toList());
        return result;
    }

    public List<UserResponse> getTopUserandUserRank(long userId){
        Users user = getUserById(userId);
        if (user != null) {

            List<Users> top1000 = getTopRanking(1000);
            List<UserResponse> responseList = getTopUser(10);

            int userIndex = top1000.indexOf(user);
            if (userIndex != -1) {
                int userRank = userIndex + 1;
                responseList.add(new UserResponse(user.getId(),user.getUsername(),user.getScore(),user.getTimeSubmit(),String.valueOf(userRank)));
                return responseList;
            }
            int score = user.getScore();
            String rank = "";
            List<Users> allUser = getTopRanking(100000);
            if(score>=allUser.get(5000).getScore()){
                rank = "1000+";
            }
            else if(score>=allUser.get(10000).getScore()){
                rank = "5000+";
            }
            else if(score>=allUser.get(20000).getScore()){
                rank = "10000+";
            }
            else if(score>=allUser.get(50000).getScore()){
                rank = "20000+";
            }
            else if(score>=allUser.get(100000).getScore()){
                rank = "50000+";
            }else {
                rank = "100000+";
            }
            responseList.add(new UserResponse(user.getId(),user.getUsername(),user.getScore(),user.getTimeSubmit(),rank));
            return responseList;
        } else {
            return null;
        }
    }

}
