package com.Creative.LeaderBoard.Repository;

import com.Creative.LeaderBoard.Entity.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReponsitory extends JpaRepository<Users,Long> {

    List<Users> findAllByOrderByScoreDescTimeSubmitDesc(Pageable pageable);


    Optional<Users> findById(Long id);

}
