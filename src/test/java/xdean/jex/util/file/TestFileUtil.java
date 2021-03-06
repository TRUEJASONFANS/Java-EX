package xdean.jex.util.file;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import xdean.jex.util.lang.ExceptionUtil;

public class TestFileUtil {
  private static final URI CP = ExceptionUtil.uncheck(() -> TestFileUtil.class.getResource(".").toURI());

  @Test
  public void testDeepTraversal() {
    FileUtil.deepTraversal(Paths.get(CP).resolve("count"))
        .count()
        .forEach(c -> Assert.assertEquals(6, c.intValue()));
  }

  @Test
  public void testWideTraversal() {
    FileUtil.wideTraversal(Paths.get(CP).resolve("count"))
        .count()
        .forEach(c -> Assert.assertEquals(6, c.intValue()));
  }

  @Test
  public void testEquals() throws IOException {
    Assert.assertTrue(FileUtil.equals(
        Paths.get(CP).resolve("file1"),
        Paths.get(CP).resolve("file1_copy")));
  }

  @Test
  public void testNotEquals() throws IOException {
    Assert.assertFalse(FileUtil.equals(
        Paths.get(CP).resolve("file1"),
        Paths.get(CP).resolve("file2")));
  }

  @Test
  public void testMd5() throws IOException {
    Assert.assertEquals(
        "01e8f91c94041493aaddc6dee5aeffc7",
        FileUtil.md5(Paths.get(CP).resolve("md5")));
  }
}
