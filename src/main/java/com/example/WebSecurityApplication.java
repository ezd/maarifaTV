package com.example;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.example.entities.ContentType;
import com.example.entities.Post;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.entities.UserToken;
import com.example.enums.RoleEnum;
import com.example.repository.RoleRepo;
import com.example.repository.UserRepository;
import com.example.services.PostService;
import com.example.services.UserRegistrationService;
import com.example.services.UserTokenService;

@SpringBootApplication
public class WebSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSecurityApplication.class, args);
	}

	@Value("${webmaster.email}")
	String webmasterEmail;
	
	@Value("${webmaster.password}")
	String webmasterpassword;
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	PostService postService;
	
	@Value("${token.expiration.inmin}")
	int expirationMiniut;
	
	@Autowired
	UserTokenService userTokenService;
	
	@Autowired
	private MailSender mailSender;
	private SimpleMailMessage msgTemplate;
//	private SimpleMailMessage templateMessage;


	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public SimpleMailMessage getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(SimpleMailMessage msgTemplate) {
		this.msgTemplate = msgTemplate;
	}
	
//	@PostConstruct
//	public void dbinit(){
//		Role adminRole=new Role(RoleEnum.ADMIN);
//		Role userRole=new Role(RoleEnum.USER);
//		Role posterRole=new Role(RoleEnum.POSTER);
//		
//		User adminUser=new User("biliyala.ezd21@gmail.com", webmasterpassword, "Admin", "AdminLast", "lasColinas","Irving", "USA", "12-23-1982", "206-229-1976",true);
//		Set<Role> roles=new HashSet<>();
//		roles.add(adminRole);
//		User savedAdminUser=userRegistrationService.saveUserInfo(adminUser, roles);
//		
//		User userUser=new User("biliyala.ezd22@gmail.com", "123456", "ezedin", "wangoria", "kolfea", "addis ababa", "Ethiopia", "12-23-1982", "251911701777",true);
//		roles=new HashSet<>();
//		roles.add(userRole);
//		User savedUserUser=userRegistrationService.saveUserInfo(userUser, roles);
//		
//		User posterUser=new User("biliyala.ezd23@gmail.com", "123456", "red", "muluken", "asko", "addis ababa", "Ethiopia", "12-23-1982", "251911701777",true);
//		roles=new HashSet<>();
//		roles.add(posterRole);
//		User savedPosterUser=userRegistrationService.saveUserInfo(posterUser, roles);
//		
//		Post post=this.getPost(savedUserUser);
//		Post savedPost=postService.saveOrUpdatePost(post);
//		
//		Post post2=this.getPost(savedAdminUser);
//		post2.setTitle("2nd post");
//		post2.setEnabled(false);
//		Post savedPost2=postService.saveOrUpdatePost(post2);
//		
//		Post post3=this.getPost(savedPosterUser);
//		post3.setTitle("3rd post");
//		post3.setPoster(posterUser);
//		Post savedPost3=postService.saveOrUpdatePost(post3);
//		
//		Post post4=this.getPost(savedUserUser);
//		post4.setTitle("4th post");
//		post4.setPoster(adminUser);
//		post4.setCatigory("social");
//		post4.setContentType(ContentType.NonTextContentType);
//		savedPost=postService.saveOrUpdatePost(post4);
//		
////		System.out.println("last Saved info:"+savedPost.toString());
//		
//		Post postToUpdate=postService.getPost(1L);
//		postToUpdate.setContentType(ContentType.NonTextContentType);
//		postToUpdate.setContent("");
//		postToUpdate.setUrl("https://www.youtube.com/watch?v=WzUaxNhKlTo");
//		postToUpdate.setCatigory("religion");
//		Post updatedPost=postService.saveOrUpdatePost(postToUpdate);
//		
////		System.out.println("*************************************************************");
//////		System.out.println("updated info:"+updatedPost.toString());
//////		postService.deletePost(2L);
////		System.out.println("find all");
////		for(Post postfound:postService.getAllPostsByContentType(ContentType.TextContentType)){
////			System.out.println(postfound.getTitle());
////		}
////		
////		System.out.println("*************************************************************");
////		System.out.println("find by user");
//////		User x=
////		for(Post postfound:postService.getAllPostsByUserByContentType(savedUserUser, ContentType.TextContentType)){
////			System.out.println(postfound.getTitle());
////		}
////		System.out.println("*************************************************************");
////		System.out.println("find by user and enabled");
////		for(Post postfound:postService.getAllPostsByUserEnabledByContentType(savedUserUser, true, ContentType.TextContentType)){
////			System.out.println(postfound.getTitle());
////		}
////		
////		System.out.println("*************************************************************");
////		System.out.println("find by user and pagable");
////		Pageable pagable=new PageRequest(0, 10, Direction.DESC, "title");
////		for(Post postfound:postService.getAllPostsByUserByEnabledByContentTypeByPage(savedUserUser, true, ContentType.TextContentType, pagable)){
////			System.out.println(postfound.getTitle());
////		}
////		System.out.println("*************************************************************");
////		System.out.println("find by user and enabled pagable");
////		Pageable pagable3=new PageRequest(0, 10, Direction.DESC, "title");
////		for(Post postfound:postService.getAllPostsByUserByEnabledByPage(savedAdminUser,false,pagable3)){
////			System.out.println(postfound.getTitle());
////		}
//		
//		
//		//getAllPostsByUserByEnabledByPage
////		System.out.println("*************************************************************");
////		System.out.println("find all byPage");
////		Pageable pagable=new PageRequest(0, 10, Direction.DESC, "title");
////		for(Post postfound:postService.getAllPostsByPage(pagable)){
////			System.out.println(postfound.getTitle());
////		}
////		
////		System.out.println("*************************************************************");
////		System.out.println("find all enabled");
////		for(Post postfound:postService.getAllPostsByEnabled(true)){
////			System.out.println(postfound.getTitle());
////		}
////		System.out.println("*************************************************************");
////		System.out.println("find all enabled byPage");
////		Pageable pagable2=new PageRequest(0, 10, Direction.DESC, "title");
////		for(Post postfound:postService.getAllPostsByEnabled(true,pagable2)){
////			System.out.println(postfound.getTitle());
////		}
//
//		
//		
//		
//		
//		
//		//UserToken ut= userTokenService.createToken(savedUser, expirationMiniut);
////		this.msgTemplate=new SimpleMailMessage();
////		this.msgTemplate.setFrom(webmasterEmail);
////		this.msgTemplate.setTo("biliyala.ezd2@gmail.com");
////		this.msgTemplate.setSubject("Spring boot mail sender test");
////		this.msgTemplate.setText("It should works");
////		SimpleMailMessage msg=new SimpleMailMessage(msgTemplate);
////		mailSender.send(msg);
//	}
	
	private Post getPost(User user) {
		Post newPost=new Post();
		newPost.setPoster(user);
		newPost.setCatigory("Religion-poletics");
		newPost.setContent(this.longtext());
		newPost.setContentType(ContentType.TextContentType);
		newPost.setDescription(this.longtext().substring(0,1000));
		newPost.setEnabled(true);
		newPost.setPostedDate(new Date());
		newPost.setTitle("first test posting.");
		newPost.setUrl("");
		return newPost;
	}

	public String longtext(){
		return "Lorem ipsum dolor sit amet, et aeque nonumy vel, eam ex nostro utamur. Qui munere impetus ex. Ad duo voluptua convenire persequeris, an facer movet periculis eam. "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"In enim docendi perpetua nam, pri feugait voluptua gloriatur an, vix at case ferri inani. An eos legere definitiones, adhuc luptatum adolescens ut eum."
				+"No pri errem regione. Mea legere veritus at. Ad fugit mentitum vel, iriure denique detracto his at. Saepe everti adipiscing te mei. Tale atomorum ex pro, ne copiosae patrioque "
				+"his. Usu wisi legendos patrioque ne.Sea an illud melius civibus, pro dolores invenire erroribus ut. Ea vis efficiendi honestatis scripserit, tale tollit "+
				"mediocritatem eam no. Eos inani essent recteque ut, pro utroque eligendi in, viderer tibique cotidieque in vis. Et lobortis neglegentur ullamcorper quo. "
				+"Qui ut feugiat appetere laboramus. Sed ad discere sensibus, et dicat error regione quo.\n "
				+"Blandit abhorreant scriptorem ne cum, meis lorem sapientem ea mei, id mei iusto utroque. Ea nisl offendit eleifend cum, sed id nostrum salutandi, qui solet fierent argumentum cu."
				+"Id alia partem primis pri. Luptatum lobortis forensibus ex est. Alii purto animal ea vel, sit cibo elitr diceret ea, quo persius ornatus nusquam ei."
				+"Everti euripidis definitionem per ne. Diam fugit expetendis ea pro. Ex qui dolore necessitatibus, id natum partem eloquentiam sea, no est quem summo luptatum. "
				+"Ius id libris quaeque, habeo alienum mnesarchum sea te, eum porro inani accommodare ne.";
	}
}
