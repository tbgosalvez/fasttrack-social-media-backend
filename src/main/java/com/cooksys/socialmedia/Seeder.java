package com.cooksys.socialmedia;

import java.sql.Timestamp;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.socialmedia.entities.Credentials;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Profile;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

	private final HashtagRepository hashtagRepository;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		Timestamp now = new Timestamp(System.currentTimeMillis());

		//////////// User1 /////////////////////

		User user1 = new User();

		Credentials credentials1 = new Credentials();
		credentials1.setUsername("Kenny");
		credentials1.setPassword("bondstone");

		Profile profile1 = new Profile();
		profile1.setEmail("Kenny@mail.com");
		profile1.setFirstName("Kenny");
		profile1.setLastName("Worth");
		profile1.setPhone("(999)999-9999");

		user1.setCredentials(credentials1);
		user1.setJoined(now);
		user1.setProfile(profile1);
		user1.setDeleted(false);

		//////////// User2 /////////////////////

		User user2 = new User();

		Credentials credentials2 = new Credentials();
		credentials2.setUsername("Vincent");
		credentials2.setPassword("bondstone");

		Profile profile2 = new Profile();
		profile2.setEmail("Vincent@mail.com");
		profile2.setFirstName("Vincent");
		profile2.setLastName("Cheng");
		profile2.setPhone("(555)555-5555");

		user2.setCredentials(credentials2);
		user2.setJoined(now);
		user2.setProfile(profile2);
		user2.setDeleted(false);

		//////////// User3 /////////////////////

		User user3 = new User();

		Credentials credentials3 = new Credentials();
		credentials3.setUsername("Brian");
		credentials3.setPassword("bondstone");

		Profile profile3 = new Profile();
		profile3.setEmail("Brian@mail.com");
		profile3.setFirstName("Timothy-Brian");
		profile3.setLastName("Gosalvez");
		profile3.setPhone("(111)867-5309");

		user3.setCredentials(credentials3);
		user3.setJoined(now);
		user3.setProfile(profile3);
		user3.setDeleted(false);

		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		////////// HashTags ///////////////////

		Hashtag hashtag1 = new Hashtag();
		hashtag1.setLabel("Winning");
		hashtag1.setFirstUsed(now);
		hashtag1.setLastUsed(now);

		Hashtag hashtag2 = new Hashtag();
		hashtag2.setLabel("NotWinning");
		hashtag2.setFirstUsed(now);
		hashtag2.setLastUsed(now);

		Hashtag hashtag3 = new Hashtag();
		hashtag3.setLabel("WhatWinning?");
		hashtag3.setFirstUsed(now);
		hashtag3.setLastUsed(now);

		hashtagRepository.saveAll(Arrays.asList(hashtag1, hashtag2, hashtag3));

		//////////// Tweets /////////////

		Tweet tweet1 = new Tweet();
		tweet1.setAuthor(user1);
		tweet1.setContent("This is a test Tweet!");
		tweet1.setPosted(now);

		Tweet tweet2 = new Tweet();
		tweet2.setAuthor(user2);
		tweet2.setContent("Tigerblood in my veins!");
		tweet2.setPosted(now);

		Tweet tweet3 = new Tweet();
		tweet3.setAuthor(user3);
		tweet3.setContent("Classic!");
		tweet3.setPosted(now);
		tweet3.setInReplyTo(tweet2);
		
		Tweet tweet4 = new Tweet();
		tweet4.setAuthor(user1);
		tweet4.setPosted(now);
		tweet4.setRepostOf(tweet2);

		tweetRepository.saveAll(Arrays.asList(tweet1, tweet2, tweet3, tweet4));

	}

}
