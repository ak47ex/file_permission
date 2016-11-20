package com.araneus.model.files;

import com.araneus.model.FileData;
import com.araneus.model.Metadata;
import com.araneus.model.configuration.Configuration;
import com.araneus.model.core.CoreManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.util.ArrayUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Araneus on 24.10.2016.
 */
public class PttFile {

    private static final String LOG_TAG = PttFile.class.getSimpleName();

    private Path path;

    private Metadata metadata;

    private List<String> files;

    private ZipInputStream zipInputStream;

    public PttFile(String path){
        this(new File(path));
    }

    public PttFile(File file){
        path = Paths.get(file.getAbsolutePath());

        try {
            zipInputStream = new ZipInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        open();
        checkMeta();

        if(file.exists()) return;
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void open() {
        byte[] buffer = new byte[2048];


        Map<String, byte[]> fileMap = new HashMap<>();

        List<ZipEntry> filesInside = new ArrayList<>();
        ZipEntry entry;
        try {
            while ((entry = zipInputStream.getNextEntry()) != null){
                String currentFileName = entry.getName();
                byte[] currentFileBytes = new byte[0];
                try
                {
                    int len = 0;
                    while ((len = zipInputStream.read(buffer)) > 0)
                    {
                        int aLen = currentFileBytes.length;
                        int bLen = buffer.length;
                        byte[] c = new byte[aLen+bLen];
                        System.arraycopy(currentFileBytes, 0, c, 0, aLen);
                        System.arraycopy(buffer, 0, c, aLen, bLen);
                        currentFileBytes = c;
                    }
                }catch (IOException e ) {
                    e.printStackTrace();
                }
                fileMap.put(currentFileName, currentFileBytes);
                filesInside.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkMeta() {

        //TODO: проверить мета информацию
        if(metadata == null) metadata = new Metadata();
        files = metadata.getFiles().stream().map( v -> v.getName()).collect(Collectors.toList());
    }

    public String getAbsolutePath() {
        return path.toString();
    }

    public String getName() {
        return path.getFileName().toString();
    }

    public void insertFile(File file) {
        metadata.addFile(new FileData(file.getName()));
        files.add(file.getName());
//        try {
//            innerFiles.add(new InnerFile(file.getName(),new FileInputStream(file)));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public List<String> getFiles() {
        return files;
    }

//    void open() throws IOException {
//
//        zipFile = new ZipFile();
//        fileEntries = new HashMap<>();
//        innerFiles = new LinkedList<>();
//        //считываем файлы из архива и кладём их в мапу
//
//        Enumeration<? extends ZipEntry> entries = zipFile.entries();
//        while(entries.hasMoreElements()){
//            ZipEntry entry = entries.nextElement();
//            if(!entry.isDirectory()) {
//                innerFiles.add(new InnerFile(entry.getAbsolutePath(),zipFile.getInputStream(entry)));
//            }
//        }
//
//        //region Проверяем на наличие метафайла
//
//        String metafileName = CoreManager.getInstance().getConfiguration().getMetafileName();
//        InnerFile metafile;
//
//        try {
//            metafile = innerFiles.stream().filter(i -> i.getAbsolutePath().equals(metafileName)).findFirst().get();
//        }catch(NoSuchElementException exception){
//            metafile = null;
//        }
//
//        if(metafile != null) {
//            Logger.write(LOG_TAG, "Metafile was found!");
//
//            //считываем метафайл в поле metadata
//            ObjectMapper mapper = new ObjectMapper();
//            metadata = mapper.readValue(metafile.getInputStream(), Metadata.class);
//            Logger.write(LOG_TAG, metadata.toString());
//
//        } else {
//            Logger.write(LOG_TAG, "Metafile wasn't found!");
//            createMeta();
//        }
//
//        //endregion
//    }


    public void save(){

        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(getAbsolutePath())))
        {
            String metafileName = Configuration.getInstance().getMetafileName();

//            fileEntries.entrySet().stream()
//                    .filter( pair -> !pair.getKey().getAbsolutePath().equals(metafileName))
//                    .forEach( pair -> {
//                        try {
//                            ZipEntry entry = (ZipEntry) pair.getKey().clone();
//                            entry.setCompressedSize(-1);
//                            zout.putNextEntry(entry);
//                            byte[] buffer = new byte[pair.getValue().available()];
//
//                            pair.getValue().read(buffer);
//                            pair.getValue().close();
//
//                            zout.write(buffer);
//                            // закрываем текущую запись для новой записи
//                            zout.closeEntry();
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    });
//
//            refreshMetadata();

            zout.putNextEntry(new ZipEntry(metafileName));
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(zout, metadata);

            zout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            open();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}
