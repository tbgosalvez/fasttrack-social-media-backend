package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByLabel(String label);
}
