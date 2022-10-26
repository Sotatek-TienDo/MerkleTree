package com.sotatek;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MerkleTree implements IMerkleTree {

  private final List<Node> nodes;

  protected MerkleTree() {
    nodes = new ArrayList<>();
  }

  @Override
  public void add(Node node) {
    nodes.add(node);
    node.setLeaf(true);
    build(node);
  }

  @Override
  public void computeTreeHash() {
    computeNodeHash(nodes.get(0));
  }

  @Override
  public Node findMerkelPathByHash(String hash) {

    Node searchedNode = nodes.stream().filter(
            node ->
               node.getHash().equals(hash)
            )
        .findFirst()
        .orElse(new Node());

    if (Objects.isNull(searchedNode.getParent())) {
      return searchedNode;
    }

    return searchedNode.getParent();
  }

  @Override
  public boolean verifyMerklePath(Node verifiedNode) {

    Node originNode = findMerkelPathByHash(verifiedNode.getHash());

    if (Objects.isNull(originNode.getHash())) {
      return false;
    }

    if (!verifiedNode.getHash().equals(originNode.getHash())) {
      return false;
    }

    String verifiedHash = computeNodeHash(verifiedNode);

    return originNode.getHash().equals(verifiedHash);
  }

  public abstract String hashing(String hash);

  private void build(Node newNode) {

    if (nodes.size() < 2) {
      return;
    }

    boolean isOdd = ((nodes.size() - 1) % 2) == 1;
    int start;

    if (Boolean.TRUE.equals(isOdd)) {
      start = (nodes.size() - 1) / 2;
    } else {
      start = (nodes.size() - 2) / 2;
    }

    Node parentNode = nodes.get(start);
    parentNode.setLeaf(false);
    newNode.setParent(parentNode);

    if (Boolean.TRUE.equals(isOdd)) {
      parentNode.setLeftLeaf(newNode);
    } else {
      parentNode.setRightLeaf(newNode);
    }

  }

  public String getNodeHash(int index) {
    return nodes.get(index).getHash();
  }

  private String computeNodeHash(Node root) {
    if (!root.isLeaf()) {
      StringBuilder builder = new StringBuilder();
      if (Objects.isNull(root.getRightLeaf())) {

        builder.append(root.getLeftLeaf().getHash());
        builder.append(root.getLeftLeaf().getHash());

        root.setHash(builder.toString());
        return root.getHash();
      }

      builder.append(computeNodeHash(root.getLeftLeaf()));
      builder.append(computeNodeHash(root.getRightLeaf()));

      root.setHash(builder.toString());

      return root.getHash();
    }

    return root.getHash();
  }

  public Node getNodeByIndex(int index) {
    return nodes.get(index);
  }


}
