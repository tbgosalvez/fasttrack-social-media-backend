package com.cooksys.socialmedia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>{
	
	Optional<Tweet> findById(Long id);

}
