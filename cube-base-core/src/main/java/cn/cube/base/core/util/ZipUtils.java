package cn.cube.base.core.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Description:ZipUtils
 * Author:zhanglida
 * Date:2019/7/24
 * Email:406504302@qq.com
 */
public class ZipUtils {
    private static final Charset CHARSET = Charset.forName("GBK");
    private static final int DEFAULT_BUFFER_SIZE = 1024;

    public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    public static final void unzip(InputStream zipInputStream, String unzipPath) throws Exception {

        File destDir = new File(unzipPath);
        if (!destDir.isDirectory()) {
            destDir.mkdir();
        }

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        ZipInputStream zis = new ZipInputStream(zipInputStream);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static final ByteArrayOutputStream unzip(InputStream zipInputStream) throws Exception {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        ZipInputStream zis = new ZipInputStream(zipInputStream, CHARSET);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        zis.getNextEntry();
        while ((len = zis.read(buffer)) > 0) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        zis.closeEntry();
        zis.close();
        return bos;
    }


    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        return destFile;
    }
}
