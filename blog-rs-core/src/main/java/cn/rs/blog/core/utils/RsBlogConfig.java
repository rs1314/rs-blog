package cn.rs.blog.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by rs
 */
@ConfigurationProperties(prefix = "rsblog")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RsBlogConfig {

    private String managePath;

    private String groupPath;

    private String weiboPath;

    private String manageTemplate;

    private String memberTemplate;

    private String frontTemplate;



}
