package com.github.kaa.covid19.informer.support;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Map;

@Component
@Data
@RequiredArgsConstructor
@Slf4j
public class BrowserHelper {
    private final ParserHelper parserHelper;
    private WebClient webClient;
    private String url;
    private HtmlPage page;
    private String html;
    private Document document;

    @PostConstruct
    private void initBrowser() {
        // HtmlUnit emulation browser
        webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setJavaScriptEnabled(true); // Enable JS interpreter, default is true
        webClient.getOptions().setCssEnabled(false); // Disable css support
        webClient.getOptions().setThrowExceptionOnScriptError(false); // Whether to throw an exception when js runs incorrectly
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        int timeout = 20 * 1000;
        webClient.getOptions().setTimeout(timeout); // Set the connection timeout
    }

    @SneakyThrows
    public void navigate(String url) {
        log.info("navigate to {}", url);
        page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(30 * 1000); // Wait for js to execute in the background for 30 seconds
        html = page.asXml();
        document = Jsoup.parse(html, url);
    }

    public Map<String, BigDecimal> queryToMap(String query) {
        return parserHelper.queryToMap(document, query);
    }

    public Elements query(String query) {
        return parserHelper.query(document, query);
    }
}
