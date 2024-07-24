package secured.text;

import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class ZipUtilsTest {
    public static final Logger LOG = Logger.getLogger("secured.text");

    @Test
    void testEncryptionBiDirectional() throws Exception {
        Path path = Paths.get("test.zip");
        File file = new File(path.toUri());
        String password = "some-random-pass";
        String plainText = "This is me encrypted";

        ZipUtils.writeEncryptedFile(file, plainText,
                RandomStringUtils.randomAlphanumeric(8),
                password.toCharArray(),
                CompressionMethod.DEFLATE, true,
                EncryptionMethod.AES,
                AesKeyStrength.KEY_STRENGTH_256);

        byte[] fileContent = Files.readAllBytes(path);
        LOG.info("Encrypted file content: " + new String(fileContent, StandardCharsets.UTF_8));

        String decryptedText = ZipUtils.readEncryptedFile(file, password.toCharArray());

        assert (decryptedText).equals(plainText);
        LOG.info("Temp file deleted: " + file.delete());

    }
}
