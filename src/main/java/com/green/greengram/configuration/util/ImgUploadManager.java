package com.green.greengram.configuration.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImgUploadManager {
    private final MyFileUtil myFileUtil;

    //폴더 생성, feed/${feedId}
    public List<String> saveFeedPics(long feedId, List<MultipartFile> pice){
        String feedPath = String.format("feed/%d", feedId);
        myFileUtil.makeFolders(feedPath);

        List<String> picFileNames = new ArrayList(pice.size());
        for(MultipartFile mf :pice){
            String randomFileName = myFileUtil.makeRandomFileName(mf);
            picFileNames.add(randomFileName);

            //파일 저장, feedPath경로에 파일을 저장
            String fullFilePath = String.format("%s/%s", feedPath, randomFileName);

            try {
                myFileUtil.transferTo(mf, fullFilePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return picFileNames;
    }

}
