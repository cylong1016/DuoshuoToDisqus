package cylong.launch;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import cylong.entity.Duoshuo;

/**
 * @author cylong
 * @version 2017年3月31日 上午9:28:37
 */
public class launch {

	public static void main(String[] args) {
		launch.run();
	}

	private static void run() {

		ObjectMapper mapper = new ObjectMapper();
		// 若使用了 @JsonFormat，则可以不设置。。
		mapper.registerModule(new JodaModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		try {

			// Convert JSON string from file to Object
			Duoshuo duoshuo = mapper.readValue(new File("data/duoshuo.json"), Duoshuo.class);

			//Pretty print
			String prettyPrint = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(duoshuo);
			System.out.println(prettyPrint);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
