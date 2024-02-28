package com.example.market_api.service;

import com.example.market_api.entity.Crypto;
import com.example.market_api.repository.CryptoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;
    private static String apiKey = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";

    public CryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    @Scheduled(fixedDelay = 3600000)
    public void insertData(){
        String uri = "https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("start", "1"));
        parameters.add(new BasicNameValuePair("limit", "5000"));
        parameters.add(new BasicNameValuePair("convert", "USD"));

        try {
            String result = makeAPICall(uri, parameters);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result);
            JsonNode dataArray = rootNode.get("data");
            for (JsonNode cryptoNode : dataArray) {
                String id = cryptoNode.get("id").asText();
                String name = cryptoNode.get("name").asText();
                String symbol = cryptoNode.get("symbol").asText();

                Crypto crypto = new Crypto();
                crypto.setId(id);

                crypto.setName(name);
                crypto.setSymbol(symbol);
                System.out.println(crypto.getId());
                cryptoRepository.save(crypto);
            }
            if (result.contains("statusCode") && result.contains("error")) {
                System.out.println("API returned an error: " + result);
                return;
            }
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("Error: cannot access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
    }

    public static String makeAPICall(String uri, List<NameValuePair> parameters)
            throws URISyntaxException, IOException {
        String responseContent = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        try (CloseableHttpResponse response = client.execute(request)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return responseContent;
    }
}
