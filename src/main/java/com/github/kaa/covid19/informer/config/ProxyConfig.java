package com.github.kaa.covid19.informer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Properties;


@ConfigurationProperties(prefix = "application.proxy")
@ConditionalOnProperty(prefix = "application.proxy", value = "enabled", havingValue = "true")
@Component
public class ProxyConfig {
    private String host;
    private String port;
    private String noProxy;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNoProxy() {
        return noProxy;
    }

    public void setNoProxy(String noProxy) {
        this.noProxy = noProxy;
    }

    @PostConstruct
    void setupProxy() {
        Properties systemProps = System.getProperties();
        if (!StringUtils.isEmpty(host)) {
            systemProps.put("proxySet", "true");
            systemProps.put("proxyHost", host);
            systemProps.put("proxyPort", port);
            systemProps.put("nonProxyHosts", noProxy);
            systemProps.put("http.nonProxyHosts", noProxy);
            systemProps.put("https.nonProxyHosts", noProxy);
        }
    }
}