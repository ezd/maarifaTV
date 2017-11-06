package com.example.entities;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity(name="POSTS")
public class Post {
	
	public Post() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private ContentType contentType;
	
	@ManyToOne
	private User poster;
	
	private Date postedDate;
	private String catigory;
	private String url;
	private boolean enabled;
	private boolean commentable;
	@Transient
	private Set<Comment> comments;
	@Transient
	private Set<Like> likes;

	private String shortWords;
	private String descriptiveImageUrl;
	
	
	
	public String getShortWords() {
		return shortWords;
	}
	public void setShortWords(String shortWords) {
		this.shortWords = shortWords;
	}
	public String getDescriptiveImageUrl() {
		return descriptiveImageUrl;
	}
	public void setDescriptiveImageUrl(String descriptiveImageUrl) {
		this.descriptiveImageUrl = descriptiveImageUrl;
	}
	public boolean isCommentable() {
		return commentable;
	}
	public void setCommentable(boolean commentable) {
		this.commentable = commentable;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	public Set<Like> getLikes() {
		return likes;
	}
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}
	public boolean isLikeable() {
		return likeable;
	}
	public void setLikeable(boolean likeable) {
		this.likeable = likeable;
	}

	private boolean likeable;
	private String title;
	@Column(columnDefinition="LONGTEXT")
	private String description;
	@Column(columnDefinition="LONGTEXT")
	private String content;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public ContentType getContentType() {
		return contentType;
	}
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public User getPoster() {
		return poster;
	}
	public void setPoster(User poster) {
		this.poster = poster;
	}
	public Date getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCatigory() {
		return catigory;
	}
	public void setCatigory(String catigory) {
		this.catigory = catigory;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", contentType=" + contentType + ", poster=" + poster + ", postedDate=" + postedDate
				+ ", catigory=" + catigory + ", url=" + url + ", disable=" + enabled + ", title=" + title
				+ ", description=" + description + ", content=" + content + "]";
	}
	
	
	
}

