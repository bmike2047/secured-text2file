package secured.text;

import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * This class is responsible for encrypting/decrypting the target file using AES256
 */
public class ZipUtils {

    /**
     * Write a text to an encrypted zip file.
     *
     * @param outputZipFile     output zip file
     * @param textToSave        text to save to file
     * @param insideFileName    filename inside the zip file
     * @param password          zip file password
     * @param compressionMethod zip file compression method
     * @param encrypt           toggle encrypt
     * @param encryptionMethod  zip file encryption method
     * @param aesKeyStrength    zip file aes key strength
     * @throws IOException thrown in case of errors
     */
    public static void writeEncryptedFile(File outputZipFile, String textToSave, String insideFileName, char[] password,
                                          CompressionMethod compressionMethod, boolean encrypt,
                                          EncryptionMethod encryptionMethod, AesKeyStrength aesKeyStrength)
            throws IOException {
        ZipParameters zipParameters = buildZipParameters(compressionMethod, encrypt, encryptionMethod, aesKeyStrength);
        byte[] buff = new byte[4096];
        int readLen;
        try (ZipOutputStream zos = initializeZipOutputStream(outputZipFile, encrypt, password)) {
            zipParameters.setFileNameInZip(insideFileName);
            zos.putNextEntry(zipParameters);
            try (InputStream inputStream = new ByteArrayInputStream(textToSave.getBytes(UTF_8))) {
                while ((readLen = inputStream.read(buff)) != -1) {
                    zos.write(buff, 0, readLen);
                }
            }
            zos.closeEntry();
        }
    }

    /**
     * Helper for the zip output stream.
     *
     * @param outputZipFile zip stream filename
     * @param encrypt       zip stream toggle encrypt
     * @param password      zip stream password
     * @return ZipOutputStream
     */
    private static ZipOutputStream initializeZipOutputStream(File outputZipFile, boolean encrypt, char[] password)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(outputZipFile);
        if (encrypt) {
            return new ZipOutputStream(fos, password);
        }
        return new ZipOutputStream(fos);
    }

    /**
     * Read and decrypt the content of an encrypted zip file.
     *
     * @param zipFile  zip filename
     * @param password zip file password
     * @return Decrypted zip file content
     */
    public static String readEncryptedFile(File zipFile, char[] password) throws IOException {
        int readLen;
        byte[] readBuffer = new byte[4096];

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream = new FileInputStream(zipFile);
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream, password)) {
            while (zipInputStream.getNextEntry() != null) {
                try (outputStream) {
                    while ((readLen = zipInputStream.read(readBuffer)) != -1) {
                        outputStream.write(readBuffer, 0, readLen);
                    }
                }
            }
        }
        return outputStream.toString(UTF_8);
    }

    /**
     * Helper to build zip parameters.
     *
     * @param compressionMethod zip file compression method
     * @param encrypt           toggle encrypt
     * @param encryptionMethod  zip file encryption method
     * @param aesKeyStrength    zip file aes key strength
     * @return ZipParameters
     */
    private static ZipParameters buildZipParameters(CompressionMethod compressionMethod, boolean encrypt,
                                                    EncryptionMethod encryptionMethod, AesKeyStrength aesKeyStrength) {
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(compressionMethod);
        zipParameters.setEncryptionMethod(encryptionMethod);
        zipParameters.setAesKeyStrength(aesKeyStrength);
        zipParameters.setEncryptFiles(encrypt);
        return zipParameters;
    }
}