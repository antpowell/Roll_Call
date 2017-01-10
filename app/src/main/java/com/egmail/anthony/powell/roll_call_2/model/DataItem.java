package com.egmail.anthony.powell.roll_call_2.model;

import java.util.Date;

/**
 * Created by SGT_POWELL on 11/20/2016.
 */

public class DataItem {

 private String itemIndex;
 private String itemName;
 private String itemImage;



 public DataItem(){}
 public DataItem(String itemIndex, String itemName){
  this.itemIndex = itemIndex;
  this.itemName = itemName;
 }

 public DataItem(String itemIndex, String itemName, String itemImage) {
  this.itemIndex = itemIndex;
  this.itemName = itemName;
  this.itemImage = itemImage;
 }


 public String getItemIndex() {
  return itemIndex;
 }

 public void setItemIndex(String itemIndex) {
  this.itemIndex = itemIndex;
 }

 public String getItemName() {
  return itemName;
 }

 public void setItemName(String itemName) {
  this.itemName = itemName;
 }

 public String getItemImage() {
  return itemImage;
 }

 public void setItemImage(String itemImage) {
  this.itemImage = itemImage;
 }
}
