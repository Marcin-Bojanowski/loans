package com.example.loans.process;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class GetInterestRate {

    @JobWorker
    public Map<String, Object> getInterestRate() throws Exception {
        BigDecimal refValue = null;
        Map<String, Object> variables = new HashMap<>();
        String xmlUrl = "https://static.nbp.pl/dane/stopy/stopy_procentowe.xml";

        WebClient webClient = WebClient.create();
        String response = webClient.get()
                .uri(xmlUrl)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(response.getBytes()));

            NodeList nodeList = doc.getElementsByTagName("pozycja");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String id = element.getAttribute("id");
                if (id.equals("ref")) {
                    NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
                    String value = element.getAttribute("oprocentowanie");
                    Number number = format.parse(value);
                    refValue = BigDecimal.valueOf(number.doubleValue());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        variables.put("referenceInterestRate", refValue);
        variables.put("referenceInterestRateDate", LocalDate.now());

        return variables;
    }
}
