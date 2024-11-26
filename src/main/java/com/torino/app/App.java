package com.torino.app;

import java.io.BufferedReader;
import java.net.*;
import java.util.Scanner;
import com.torino.app.tools.ReqHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torino.app.Pizza;

public class App {
  static String logo = """
      ████████  ██████  ██████  ██ ███    ██  ██████      ██████  ██ ███████ ███████  █████
       ██    ██    ██ ██   ██ ██ ████   ██ ██    ██     ██   ██ ██    ███     ███  ██   ██
       ██    ██    ██ ██████  ██ ██ ██  ██ ██    ██     ██████  ██   ███     ███   ███████
       ██    ██    ██ ██   ██ ██ ██  ██ ██ ██    ██     ██      ██  ███     ███    ██   ██
       ██     ██████  ██   ██ ██ ██   ████  ██████      ██      ██ ███████ ███████ ██   ██
          """;

  static String blurb = """
      Welcome to the torino backend, please input a (command) from the list:
                - Show Menu (m)
                - Add a new entry (a)
                - Remove an entry (r)
      """;

  static Scanner user_input = new Scanner(System.in);
  static ReqHandler ping_handler = new ReqHandler("http://sql.ianis.lol/ping", "GET");
  static ReqHandler add_table_handler = new ReqHandler("http://sql.ianis.lol/pizzas", "POST");
  static ReqHandler remove_table_handler = new ReqHandler("http://sql.ianis.lol/remove", "POST");
  static ReqHandler menu_handler = new ReqHandler("http://sql.ianis.lol/menu", "GET");
  static ObjectMapper object_mapper = new ObjectMapper();

  public static void clear() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void showMenu() {
    var response = menu_handler.sendAndRead();
    System.out.println(response);
  }

  // Adds an entry to the piza table
  public static void addEntry(String temp_name, String temp_description, int temp_price, int temp_id) {
    var pizza = new Pizza(temp_name, temp_description, temp_price, temp_id);
    System.out.println("ADD: Pizza object created!");
    try {
      String json = object_mapper.writeValueAsString(pizza);
      var response = add_table_handler.sendAndRead(json);
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("Exception caught " + e.getMessage());
    }

  }

  // Removes an entry from the pizza table
  public static void removeEntry(String id) {
    try {
      String convertable = "";
      String.format("{id:%s}", id);

      String json = object_mapper.writeValueAsString(convertable);
      var response = remove_table_handler.sendAndRead(json);
      System.out.println(response);
    } catch (Exception e) {
      System.out.println("Exception caught " + e.getMessage());

    }

  }

  public static void main(String[] args) {
    // Startup
    clear();
    System.out.println(logo);
    System.out.println(blurb);

    // Main
    char option = user_input.next().charAt(0);
    // user_input.next();

    switch (option) {
      case 'm':
        showMenu();
        break;
      case 'r':
        System.out.println("REMOVE: What which item? (by id)");
        user_input.nextLine();
        String id = user_input.nextLine();
        removeEntry(id);
        break;
      case 'a':
        user_input.nextLine();
        System.out.println("ADD | Pizza Name: ");
        String temp_name = user_input.nextLine();
        System.out.println("ADD | Pizza Description: ");
        String temp_description = user_input.nextLine();
        System.out.println("ADD | Pizza Price: ");
        int temp_price = user_input.nextInt();
        System.out.println("ADD | Pizza ID: ");
        int temp_id = user_input.nextInt();
        addEntry(temp_name, temp_description, temp_price, temp_id);
        break;
    }
  }
}
