package com.github.kaa.covid19.informer.support;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class CovidParserHelper {

    @SneakyThrows
    @EventListener({ContextRefreshedEvent.class})
    public void handleContextRefreshEvent() {
        Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/coronavirus-cases/")
                .get();

        final Elements select = doc.select("html body div.container div.row div.col-md-8 div.content-inner div div div.col-md-6 div.table-responsive table.table.table-striped.table-bordered");
        //System.out.println(doc);

        // Currently Infected
        doc.select("html body div.container div.row div.col-md-8 div.content-inner div div div.col-md-6:nth-child(1) div.table-responsive table.table.table-striped.table-bordered tr td").stream().forEach(e -> System.out.println((e.text())));
        System.out.println("-----");
        //Cases with Outcome
        doc.select("html body div.container div.row div.col-md-8 div.content-inner div div div.col-md-6:nth-child(2) div.table-responsive table.table.table-striped.table-bordered tbody tr").stream().forEach(e -> System.out.println((e.text())));        //System.out.println(select);

    }
}
