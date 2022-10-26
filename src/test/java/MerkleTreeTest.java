import com.sotatek.CommonMerkleTree;
import com.sotatek.MerkleTree;
import com.sotatek.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MerkleTreeTest {

  private final MerkleTree merkleTree;

  private MerkleTreeTest(){
    merkleTree = new CommonMerkleTree();
    merkleTree.add(Node.builder().chuck("1").build()); //0
    merkleTree.add(Node.builder().chuck("2").build()); //1
    merkleTree.add(Node.builder().chuck("3").build()); //2
    merkleTree.add(Node.builder().chuck("4").hash("2").build()); //3
    merkleTree.add(Node.builder().chuck("5").hash("3").build()); //4
    merkleTree.add(Node.builder().chuck("6").hash("4").build()); //5
    merkleTree.add(Node.builder().chuck("7").hash("5").build()); //6
    merkleTree.add(Node.builder().chuck("8").hash("6").build()); //7
    merkleTree.computeTreeHash();
  }

  @Test
  void buildMerkleTree(){
    Node rootNode = merkleTree.getNodeByIndex(0);

    Node leftLeafNode = merkleTree.getNodeByIndex(1);
    Node rightLeafNode = merkleTree.getNodeByIndex(2);

    Assertions.assertEquals(leftLeafNode.getChuck(), rootNode.getLeftLeaf().getChuck());
    Assertions.assertEquals(rightLeafNode.getChuck(), rootNode.getRightLeaf().getChuck());

    Node left1LeafNode = merkleTree.getNodeByIndex(3);
    Node right1LeafNode = merkleTree.getNodeByIndex(4);

    Assertions.assertEquals(left1LeafNode.getChuck(), leftLeafNode.getLeftLeaf().getChuck());
    Assertions.assertEquals(right1LeafNode.getChuck(), leftLeafNode.getRightLeaf().getChuck());

    Node left2LeafNode = merkleTree.getNodeByIndex(5);
    Node right2LeafNode = merkleTree.getNodeByIndex(6);

    Assertions.assertEquals(left2LeafNode.getChuck(), rightLeafNode.getLeftLeaf().getChuck());
    Assertions.assertEquals(right2LeafNode.getChuck(), rightLeafNode.getRightLeaf().getChuck());

    Node left3LeafNode = merkleTree.getNodeByIndex(7);

    Assertions.assertEquals(left3LeafNode.getChuck(), left1LeafNode.getLeftLeaf().getChuck());
  }

  @Test
  void computeNodeHash(){
    String expectedHash = "663";
    Assertions.assertEquals(expectedHash, merkleTree.getNodeHash(1));
  }

  @Test
  void computeRootHash(){
    String expectedHash = "66345";
    Assertions.assertEquals(expectedHash, merkleTree.getNodeHash(0));
  }

  @Test
  void verifyHash(){
    Node root = merkleTree.getNodeByIndex(0);
    Assertions.assertEquals(Boolean.TRUE,merkleTree.verifyMerklePath(root));
  }

  @Test
  void findNodeByHash(){
    Node root = merkleTree.getNodeByIndex(0);
    Node searched = merkleTree.findMerkelPathByHash(root.getHash());

    Assertions.assertEquals(root,searched);
  }

  @Test
  void veriyHashFailNotExistHash(){
    Node root = new Node();
    root.setHash("1");
    Assertions.assertEquals(Boolean.FALSE,merkleTree.verifyMerklePath(root));
  }

}
