package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.ContentType;
import com.example.entities.Post;
import com.example.entities.User;


@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	List<Post> findAllByEnabled(boolean disabled);
	Page<Post> findAll(Pageable pagable);
	Page<Post> findAllByEnabled(boolean disabled,Pageable pagable);
	
	List<Post> findAllByPoster(User poster);
	Page<Post> findAllByPoster(User poster,Pageable pagable);
	List<Post> findAllByPosterAndEnabled(User poster,boolean enabled);
	Page<Post> findAllByPosterAndEnabled(User poster,boolean enabled,Pageable pagable);
	
	List<Post> findAllByContentType(ContentType contentType);
	List<Post> findAllByContentType(ContentType contentType,Pageable pagable);
	List<Post> findAllByPosterAndContentType(User poster,ContentType contentType);
	Page<Post> findAllByPosterAndContentType(User poster,ContentType contentType,Pageable pagable);
	List<Post> findAllByPosterAndEnabledAndContentType(User poster,boolean enabled,ContentType contentType);
	Page<Post> findAllByPosterAndEnabledAndContentType(User poster,boolean enabled,ContentType contentType,Pageable pagable);
	

}
