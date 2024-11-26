package com.torino.app.tools;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReqHandler {
  private final HttpClient client;
  private final String targetUrl;
  private final String requestType;

  public ReqHandler(String targetUrl, String reqType) {
    this.client = HttpClient.newHttpClient();
    this.targetUrl = targetUrl;
    this.requestType = reqType.toUpperCase();
  }

  public String sendAndRead(String json) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(targetUrl))
          .method(requestType.toUpperCase(), HttpRequest.BodyPublishers.ofString(json))
          .header("Content-Type", "application/json")
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        return response.body();
      } else {
        System.out.println("Request failed with status: " + response.statusCode());
        return null;
      }
    } catch (Exception e) {
      System.out.println("Error during request: " + e.toString());
      return null;
    }
  }

  // Method to read responses
  public String sendAndRead() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(targetUrl))
          .method(requestType, HttpRequest.BodyPublishers.noBody())
          .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() == 200) {
        return response.body();
      } else {
        System.out.println("Request failed with status: " + response.statusCode());
        return null;
      }
    } catch (IOException | InterruptedException e) {
      System.out.println("Error during request: " + e.toString());
      return null;
    }
  }
}
