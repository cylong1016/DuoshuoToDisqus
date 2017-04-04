package cylong.launch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import cylong.entity.Duoshuo;
import cylong.entity.Post;
import cylong.entity.Thread;

/**
 * 将多说的评论格式转化成 Disqus 的 XML 格式
 * @author cylong
 * @version 2017年3月31日 上午9:28:37
 */
public class Launch {

	/** 数据文件夹 */
	private static String dataDir = "data";
	/** 多说源文件【默认路径】 */
	private static String sourceDefaultPath = dataDir + "/duoshuo.json";
	/** 格式化后的多说源文件，方便查看 */
	private static final String formatSourcePath = dataDir + "/duoshuo-format.json";
	/** 转化的 Disqus 评论文件 */
	private static final String disqusPath = dataDir + "/disqus.xml";

	public static void main(String[] args) {
		File dir = new File(dataDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (args.length != 0) {
			sourceDefaultPath = args[0];
		}
		Duoshuo duoshuo = getDuoshuoObj(sourceDefaultPath);
		createDisqusXML(duoshuo, disqusPath);
		System.out.println("Success! Path = " + disqusPath);
		System.out.println("by cylong1016(https://github.com/cylong1016/DuoshuoToDisqus)");
	}

	/**
	 * 从 JSON 文件中获得 Duoshuo 对象
	 * @param path 文件路径
	 * @return Duoshuo 对象
	 * @author cylong
	 * @version 2017年4月2日 上午1:24:38
	 */
	private static Duoshuo getDuoshuoObj(String path) {
		ObjectMapper mapper = new ObjectMapper();
		// 若使用了 @JsonFormat，则可以不设置。。
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {
			// Convert JSON string from file to Object
			Duoshuo duoshuo = mapper.readValue(new File(path), Duoshuo.class);

			// Convert object to JSON string and save into a file directly
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(formatSourcePath), duoshuo);
			return duoshuo;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建 Disqus 评论的 XML 格式，保存到文件中
	 * @param duoshuo 多说数据对象
	 * @param path 输出路径
	 * @author cylong
	 * @version 2017年4月2日 上午1:32:16
	 */
	private static void createDisqusXML(Duoshuo duoshuo, String path) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		Document document = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			document = builder.newDocument();
			Element rss = document.createElement("rss");
			rss.setAttribute("version", "2.0");
			rss.setAttribute("xmlns:content", "http://purl.org/rss/1.0/modules/content/");
			rss.setAttribute("xmlns:dsq", "http://www.disqus.com/");
			rss.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
			rss.setAttribute("xmlns:wp", "http://wordpress.org/export/1.0/");

			Element channel = document.createElement("channel");
			List<Thread> threads = duoshuo.getThreads();
			List<Post> posts = duoshuo.getPosts();

			for(Post post : posts) {
				Element item = document.createElement("item");
				for(Thread thread : threads) {
					if (post.getThread_id().equals(thread.getThread_id())) {
						Element title = document.createElement("title");
						title.appendChild(document.createTextNode(thread.getTitle()));
						Element link = document.createElement("link");
						link.appendChild(document.createTextNode(thread.getUrl()));
						Element contentEncoded = document.createElement("content:encoded");
						contentEncoded.appendChild(document.createCDATASection(""));
						Element dsqThread_identifier = document.createElement("dsq:thread_identifier");
						dsqThread_identifier.appendChild(document.createTextNode(thread.getThread_id()));
						Element wpPost_date_gmt = document.createElement("wp:post_date_gmt");
						String created_at = sdf.format(thread.getCreated_at());
						wpPost_date_gmt.appendChild(document.createTextNode(created_at));

						item.appendChild(title);
						item.appendChild(link);
						item.appendChild(contentEncoded);
						item.appendChild(dsqThread_identifier);
						item.appendChild(wpPost_date_gmt);
					}
				}

				Element wpComment_status = document.createElement("wp:comment_status");
				wpComment_status.appendChild(document.createTextNode("open"));
				Element wpComment = document.createElement("wp:comment");

				Element wpComment_id = document.createElement("wp:comment_id");
				wpComment_id.appendChild(document.createTextNode(post.getPost_id()));
				Element wpComment_author = document.createElement("wp:comment_author");
				wpComment_author.appendChild(document.createTextNode(getStringNotNULL(post.getAuthor_name())));
				Element wpComment_author_email = document.createElement("wp:comment_author_email");
				wpComment_author_email.appendChild(document.createTextNode(getStringNotNULL(post.getAuthor_email())));
				Element wpComment_author_url = document.createElement("wp:comment_author_url");
				wpComment_author_url.appendChild(document.createTextNode(getStringNotNULL(post.getAuthor_url())));
				Element wpComment_author_IP = document.createElement("wp:comment_author_IP");
				wpComment_author_IP.appendChild(document.createTextNode(post.getIp()));
				Element wpComment_date_gmt = document.createElement("wp:comment_date_gmt");
				String commentDateGMT = sdf.format(post.getCreated_at());
				wpComment_date_gmt.appendChild(document.createTextNode(commentDateGMT));
				Element wpComment_content = document.createElement("wp:comment_content");
				wpComment_content.appendChild(document.createCDATASection(getStringNotNULL(post.getMessage())));
				Element wpComment_approved = document.createElement("wp:comment_approved");
				wpComment_approved.appendChild(document.createTextNode("1"));
				Element wpComment_parent = document.createElement("wp:comment_parent");
				String[] parents = post.getParents();
				String parentsStr = "";
				if (parents != null && parents.length != 0) {
					parentsStr += parents[0];
					for(int i = 1; i < parents.length; i++) {
						parentsStr = parentsStr + "," + parents[i];
					}
				}
				wpComment_parent.appendChild(document.createTextNode(parentsStr));

				wpComment.appendChild(wpComment_id);
				wpComment.appendChild(wpComment_author);
				wpComment.appendChild(wpComment_author_email);
				wpComment.appendChild(wpComment_author_url);
				wpComment.appendChild(wpComment_author_IP);
				wpComment.appendChild(wpComment_date_gmt);
				wpComment.appendChild(wpComment_content);
				wpComment.appendChild(wpComment_approved);
				wpComment.appendChild(wpComment_parent);

				item.appendChild(wpComment_status);
				item.appendChild(wpComment);

				channel.appendChild(item);
			}
			rss.appendChild(channel);
			document.appendChild(rss);

			writeXML(document, disqusPath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将创建的 xml document 写入到文件中
	 * @param document
	 * @param path 文件路径
	 * @author cylong
	 * @version 2017年4月2日 上午4:02:38
	 */
	private static void writeXML(Document document, String path) {
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			PrintWriter pw = new PrintWriter(new FileOutputStream(path));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将 null 字符串转化成 ""字符串
	 * @param str
	 * @return
	 * @author cylong
	 * @version 2017年4月2日 上午2:54:17
	 */
	private static String getStringNotNULL(String str) {
		return str == null ? "" : str;
	}
}
