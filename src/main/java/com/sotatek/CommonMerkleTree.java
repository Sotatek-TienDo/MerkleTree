package com.sotatek;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonMerkleTree extends MerkleTree {

  @Override
  public String hashing(String hash) {
    MessageDigest digest;

    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      return "";
    }

    byte[] encodedHash = digest.digest(hash.getBytes(StandardCharsets.UTF_8));

    return String.valueOf(encodedHash);
  }
}
