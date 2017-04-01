package cylong.entity;

import java.util.List;

/**
 * 多说评论格式
 * @see <a href="http://dev.duoshuo.com/docs/500fc3cdb17b12d24b00000a"> 多说格式文档</a>
 * @author cylong
 * @version 2017年3月31日 上午9:31:38
 */
public class Duoshuo {

	private String generator;
	private String version;
	/** 文章 */
	List<Thread> threads;
	/** 评论 */
	List<Post> posts;

	public String getGenerator() {
		return this.generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Thread> getThreads() {
		return this.threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

}
