package com.cooksys.socialmedia.repositories;

import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.Hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}
