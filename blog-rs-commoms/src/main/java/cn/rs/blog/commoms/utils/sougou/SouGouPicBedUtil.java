package cn.rs.blog.commoms.utils.sougou;

import cn.rs.blog.commoms.utils.sina.GeneralUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.config.RequestConfig;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import org.apache.http.util.EntityUtils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author rs    529811807@qq.com
 *
 */
public class SouGouPicBedUtil {

	private static String bucketUrl = "http://pic.sogou.com/ris_upload?r=";
	/**
	 * 搜狗图片上传
	 * @param multipartFiles
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<String> uploadFile(MultipartFile[] multipartFiles) throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String dateline = String.valueOf(System.currentTimeMillis());
		dateline = dateline.substring(0, 10);
		List<String> urls = new ArrayList<>();
		try {
			HttpPost httpPost = new HttpPost(bucketUrl + System.currentTimeMillis());
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(100000).setSocketTimeout(100000).build();
			httpPost.setConfig(requestConfig);
			String fileName = null;
			String prefix = ".jpg";
			File piclFile = null;
			for(MultipartFile multipartFile:multipartFiles) {
				if(!GeneralUtils.isImage(multipartFile.getInputStream())) {
					 continue;
				}
				fileName = multipartFile.getOriginalFilename();
		        fileName.substring(fileName.lastIndexOf("."));
		        piclFile = File.createTempFile(GeneralUtils.getLowerUUID(), prefix);
		        multipartFile.transferTo(piclFile);
		        FileBody bin = new FileBody(piclFile);
				HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("pic_path", bin).build();
				httpPost.setHeader(new BasicHeader("Charset", "UTF-8"));
				httpPost.setHeader(new BasicHeader("Host", "pic.sogou.com"));
				httpPost.setEntity(reqEntity);
				CloseableHttpResponse response = httpclient.execute(httpPost);

				try {

					Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD上
					String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请
					newuri = URLDecoder.decode(newuri, "utf-8");
					newuri = newuri.substring(newuri.indexOf("query=") + 6);
					urls.add(newuri);
					HttpEntity resEntity = response.getEntity();
					EntityUtils.consume(resEntity);
				} finally {
					deleteFile(piclFile);
					if(response!=null){
						response.close();
					}

				}
			}
		} finally {
			httpclient.close();
		}
		return urls;
	}

	/**
	 * 删除缓存文件
	 * @param files
	 */
	private static void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }


}
