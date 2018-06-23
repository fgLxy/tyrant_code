package org.tyrant.surfboard.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.tyrant.surfboard.dto.FileDTO;

public abstract class ResponseUtils {
	public static Map<String, Object> response(int errno, Object data) {
		Map<String, Object> resp = new HashMap<>();
		resp.put("errno", errno);
		resp.put("data", data);
		return resp;
	}
	
	public static DataPack pack(String name, Object value) {
		return new DataPack().pack(name, value);
	}
	
	public static class DataPack {
		private Map<String, Object> pack;
		
		DataPack() {
			this.pack = new HashMap<>();
		}
		
		public DataPack pack(String name, Object value) {
			pack.put(name, value);
			return this;
		}
		
		public Map<String, Object> getPack() {
			return this.pack;
		}
	}
	
	public static void setDownloadFile(HttpServletResponse rsp, FileDTO file) throws IOException {
		rsp.setContentType("application/octet-stream; charset=utf-8");
		rsp.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
		Path path = Paths.get(file.getPath());
		try (InputStream input = Files.newInputStream(path)){
			IOUtils.copy(input, rsp.getOutputStream());
			rsp.getOutputStream().flush();
		}
		Files.delete(path);
	}
}
