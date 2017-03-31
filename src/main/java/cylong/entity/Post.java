package cylong.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 来自多说的评论
 * @author cylong
 * @version 2017年3月31日 上午9:59:08
 */
public class Post {

	/** 多说评论ID。 */
	private String post_id;
	/** 这条评论对应的文章记录。 */
	private String thread_id;
	/** 评论内容。 */
	private String message;
	/** 站点ID。 */
	private String site_id;
	/** 评论发表时间。 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, /* pattern = "yyyy-MM-dd HH:mm:ss", */ timezone = "GMT+8")
	private Date created_at;
	/** 评论更新时间。 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, /* pattern = "yyyy-MM-dd HH:mm:ss", */ timezone = "GMT+8")
	private Date updated_at;
	/** 评论被点【赞】的次数。 */
	private int likes;
	/** 作者的IP地址。 */
	private String ip;
	private String[] parents;
	/** 作者在多说的id。空表示匿名用户。 */
	private String author_id;
	/** 作者邮箱。有可能为空。 */
	private String author_email;
	/** 作者显示名。有可能为空。 */
	private String author_name;
	/** 作者填写的URL。有可能为空。 */
	private String author_url;
	private String author_key;
	private String thread_key;
	private String post_key;

	public String getPost_id() {
		return this.post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getThread_id() {
		return this.thread_id;
	}

	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSite_id() {
		return this.site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public Date getCreated_at() {
		return this.created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return this.updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public int getLikes() {
		return this.likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String[] getParents() {
		return this.parents;
	}

	public void setParents(String[] parents) {
		this.parents = parents;
	}

	public String getAuthor_id() {
		return this.author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

	public String getAuthor_email() {
		return this.author_email;
	}

	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}

	public String getAuthor_name() {
		return this.author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_url() {
		return this.author_url;
	}

	public void setAuthor_url(String author_url) {
		this.author_url = author_url;
	}

	public String getAuthor_key() {
		return this.author_key;
	}

	public void setAuthor_key(String author_key) {
		this.author_key = author_key;
	}

	public String getThread_key() {
		return this.thread_key;
	}

	public void setThread_key(String thread_key) {
		this.thread_key = thread_key;
	}

	public String getPost_key() {
		return this.post_key;
	}

	public void setPost_key(String post_key) {
		this.post_key = post_key;
	}

}
