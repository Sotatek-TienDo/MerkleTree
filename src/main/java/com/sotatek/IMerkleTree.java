package com.sotatek;

public interface IMerkleTree {
  void add(Node node);
  void computeTreeHash();
  Node findMerkelPathByHash(String hash);
  boolean verifyMerklePath(Node verifiedNode);
}
