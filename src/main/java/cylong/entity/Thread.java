package cylong.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 文章记录
 * @author cylong
 * @version 2017年3月31日 上午9:38:01
 */
public class Thread {

	/** 站点ID。 */
	private String site_id;
	/** 多说文章ID。 */
	private String thread_id;
	/** 创建时间。 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, /* pattern = "yyyy-MM-dd HH:mm:ss", */ timezone = "GMT+8")
	private Date created_at;
	/** 更新时间。 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, /* pattern = "yyyy-MM-dd HH:mm:ss", */ timezone = "GMT+8")
	private Date updated_at;
	/** 文章被点【赞】的次数。 */
	private int likes;
	/** 文章浏览数。 */
	private int views;
	/** 文章在当前站点中的唯一表示符，例如文章ID。 */
	private String thread_key;
	/** 文章的标题。 */
	private String title;
	/** 文章链接。 */
	private String url;
	/** 文章作者在本站的ID。 */
	private String author_key;
	/** 文章作者的多说ID，如果为空，说明发表文章时没有登陆多说账号。 */
	private String author_id;

	public String getSite_id() {
		return this.site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getThread_id() {
		return this.thread_id;
	}

	public void setThread_id(String thread_id) {
		this.thread_id = thread_id;
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

	public int getViews() {
		return this.views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getThread_key() {
		return this.thread_key;
	}

	public void setThread_key(String thread_key) {
		this.thread_key = thread_key;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor_key() {
		return this.author_key;
	}

	public void setAuthor_key(String author_key) {
		this.author_key = author_key;
	}

	public String getAuthor_id() {
		return this.author_id;
	}

	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}

}
