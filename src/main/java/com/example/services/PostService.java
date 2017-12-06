package com.example.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entities.Category;
import com.example.entities.ContentType;
import com.example.entities.Post;
import com.example.entities.User;
import com.example.repository.CategoryRepository;
import com.example.repository.PostRepository;

@Service
public class PostService {
	
	public PostService() {
	}
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Post saveOrUpdatePost(Post post){
		return postRepository.save(post);
	}
	public void deletePost(Long postId){
		postRepository.delete(postId);
	}
	
	public Post getPost(Long postId){
		return postRepository.findOne(postId);
	}
	
	public List<Post> getAllPosts(){
		return (List<Post>) postRepository.findAll();
	}
	public List<Post> getAllPostsByContentType(ContentType type){
		return (List<Post>) postRepository.findAllByContentType(type);
	}
	
	public List<Post> getAllPostsByUser(User user){
		return (List<Post>) postRepository.findAllByPoster(user);
	}
	
	public List<Post> getAllPostsByUserByContentType(User user,ContentType type){
		return (List<Post>) postRepository.findAllByPosterAndContentType(user, type);
	}
	
	public List<Post> getAllPostsByUserEnabled(User user,boolean enabled){
		return (List<Post>) postRepository.findAllByPosterAndEnabled(user,enabled);
	}
	
	public List<Post> getAllPostsByUserEnabledByContentType(User user,boolean enabled,ContentType type){
		return (List<Post>) postRepository.findAllByPosterAndEnabledAndContentType(user, enabled, type);
	}
	
	public List<Post> getAllPostsByEnabled(boolean disableStatus){
		List<Post> postList=new ArrayList<>();
		Iterator<Post>listFound=postRepository.findAllByEnabled(disableStatus).iterator();
		while(listFound.hasNext()){
			postList.add(listFound.next());
		}
		return postList;
	}
	
	public List<Post> getAllPostsByPage(Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAll(pagable).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	public List<Post> getAllPostsByPageByContentType(Pageable pagable,ContentType type){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByContentType(type).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	public List<Post> getAllPostsByUser(User user,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByPoster(user,pagable).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	public List<Post> getAllPostsByUserByContentTypeByPage(User user,ContentType type,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByPosterAndContentType(user, type,pagable).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	public List<Post> getAllPostsByUserByPage(User user,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByPoster(user,pagable).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	public List<Post> getAllPostsByUserByEnabledByPage(User user,boolean enabled,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByPosterAndEnabled(user,enabled,pagable).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	
	public List<Post> getAllPostsByUserByEnabledByContentTypeByPage(User user,boolean enabled,ContentType type,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		 Iterator<Post>listFound=postRepository.findAllByPosterAndEnabledAndContentType(user, enabled, type).iterator();
			while(listFound.hasNext()){
				postList.add(listFound.next());
			}
			return postList;
		 
	}
	
	public List<Post> getAllPostsByEnabled(boolean disableStatus,Pageable pagable){
		List<Post> postList=new ArrayList<>();
		Iterator<Post>listFound=postRepository.findAllByEnabled(disableStatus,pagable).iterator();
		while(listFound.hasNext()){
			postList.add(listFound.next());
		}
		return postList;
	}
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
	public Category getCategoryByName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}
	public List<Category> getAllCategories() {
		return categoryRepository.findAllByOrderByCategoryNameAsc();
	}
	public void deleteCategory(Long id) {
		categoryRepository.delete(id);
	}
	

}
