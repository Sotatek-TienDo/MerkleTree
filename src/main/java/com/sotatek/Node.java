package com.sotatek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {

  private String hash;
  private String chuck;
  private boolean isLeaf;
  private Node leftLeaf;
  private Node rightLeaf;
  private Node parent;

  public boolean isLeaf(){
    return this.isLeaf;
  }
}
