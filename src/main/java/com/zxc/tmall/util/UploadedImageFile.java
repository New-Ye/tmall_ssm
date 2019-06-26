package com.zxc.tmall.util;

import org.springframework.web.multipart.MultipartFile;
 
public class UploadedImageFile {
    //这里的属性名称image必须和页面中的增加分类部分中的type="file"的name值保持一致。
    //<input id="categoryPic" accept="image/*" type="file" name="image" />
    MultipartFile image;
 
    public MultipartFile getImage() {
        return image;
    }
 
    public void setImage(MultipartFile image) {
        this.image = image;
    }
 
}
