package com.torino.app;

public class Pizza {
  public int id, price;
  public String name, desc;

  public Pizza(String target_name, String target_desc, int target_price, int target_id) {
    id = target_id;
    price = target_price;
    name = target_name;
    desc = target_desc;
  }
}
