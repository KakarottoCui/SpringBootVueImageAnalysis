package com.ivo.mas.service;

import ch.qos.logback.core.util.SystemInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ivo.mas.mapper.PicCutItemMapper;
import com.ivo.mas.mapper.PicMainMapper;
import com.ivo.mas.pojo.PicCutItem;
import com.ivo.mas.pojo.PicMain;
import com.ivo.mas.system.ResponseFormat.ResponseCode;
import com.ivo.mas.system.ResponseFormat.ResponseResult;
import com.ivo.mas.system.SysInfo;
import com.ivo.mas.system.utils.CommonFunction;
import com.ivo.mas.system.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    @Resource
    private PicMainMapper picMainMapper;

    @Resource
    private PicCutItemMapper picCutItemMapper;

    public ResponseResult cutPic(MultipartFile file,String data,Double limit) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            // 获取文件名
            String fileName = System.currentTimeMillis()+"-"+ file.getOriginalFilename();
            // 构建文件保存的路径
            Path uploadPath = Paths.get(SysInfo.FILE_PATH);
            // 如果目录不存在则创建
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // 构建文件完整路径
            Path filePath = uploadPath.resolve(fileName);
            // 保存文件
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String picPath = SysInfo.FILE_PATH+fileName;

            PicMain picMain = new PicMain();
            picMain.setValidFlag(1);
            picMain.setCreateTime(new Date());
            picMain.setPicPath(fileName);
            picMainMapper.insert(picMain);
            List<String> res = new ArrayList<String>();
            res.add(picMain.getId()+"");
            try {
                // 读取图片
                File input = new File(picPath);
                BufferedImage image = ImageIO.read(input);
                double tolerance = limit;
                List<String> picres = extractAllColorFamilies(image,tolerance,data);
                for(String path:picres){
                    if(StringUtils.isNotEmpty(path)){
                        PicCutItem picCutItem = new PicCutItem();
                        picCutItem.setCreateTime(new Date());
                        picCutItem.setValidFlag(1);
                        picCutItem.setCutPicPath(path);
                        picCutItem.setPicFk(picMain.getId());
                        picCutItemMapper.insert(picCutItem);
                        res.add(picCutItem.getId()+"");
                    }
                }
                System.out.println("处理完成，结果已保存为 output.png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return new ResponseResult<>(200,"分割成功",res);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // RGB 转 HSV
    public float[] rgbToHsv(int r, int g, int b) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(r, g, b, hsv);
        return hsv;
    }

    // 判断两个 HSV 颜色是否属于同一色系
    public boolean isSameColorFamily(float[] hsv1, float[] hsv2, double tolerance) {
        int family1 = getColorFamily(hsv1);
        int family2 = getColorFamily(hsv2);
        return family1 == family2 && Math.abs(hsv1[1] - hsv2[1]) <= tolerance && Math.abs(hsv1[2] - hsv2[2]) <= tolerance;
    }

    // 根据 HSV 颜色判断所属色系
    public int getColorFamily(float[] hsv) {
        float hue = hsv[0];
        float saturation = hsv[1];
        float value = hsv[2];

        if (saturation < 0.1 && value > 0.9) {
            return 6; // 白色系
        } else if (saturation < 0.3 && value < 0.3) {
            return 7; // 黑色系
        }

        if ((hue < 0.08 || hue > 0.92) && (saturation > 0.1 || value < 0.9)) {
            return 1; // 红色系
        } else if (hue < 0.167) {
            return 4; // 黄色系
        } else if (hue < 0.35) {
            return 2; // 绿色系
        } else if ((hue >= 0.5 && hue < 0.78) && (saturation > 0.05 || (hue >= 0.55 && hue < 0.7) )) {
            return 3; // 蓝色系
        } else {
            return 5; // 紫色系
        }
    }

    // 从图片中提取相近颜色区域
    public BufferedImage extractColor(BufferedImage image, float[] targetHsv, double tolerance) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                int r = (argb >> 16) & 0xff;
                int g = (argb >> 8) & 0xff;
                int b = argb & 0xff;
                float[] pixelHsv = rgbToHsv(r, g, b);

                if (isSameColorFamily(pixelHsv, targetHsv, tolerance)) {
                    result.setRGB(x, y, argb);
                } else {
                    result.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }

        return result;
    }

    // 提取所有色系的图片
    public List<String> extractAllColorFamilies(BufferedImage image, double tolerance,String data) {
        String[] familyNames = {"red", "green", "blue", "yellow", "purple", "white", "black"};
        float[][] representativeHsvs = {
                rgbToHsv(255, 0, 0),    // 红色系代表颜色
                rgbToHsv(0, 255, 0),    // 绿色系代表颜色
                rgbToHsv(0, 0, 255),    // 蓝色系代表颜色
                rgbToHsv(255, 255, 0),  // 黄色系代表颜色
                rgbToHsv(128, 0, 128),  // 紫色系代表颜色
                rgbToHsv(255, 255, 255),// 白色系代表颜色
                rgbToHsv(0, 0, 0)       // 黑色系代表颜色
        };
        List<String> res = new ArrayList<String>();
        for (int i = 0; i < familyNames.length; i++) {
            if(!data.contains(String.valueOf(i+1))){
                res.add("");
                continue;
            }
            BufferedImage result = extractColor(image, representativeHsvs[i], tolerance);
            try {
                String picName = System.currentTimeMillis()+"-"+ familyNames[i] + "_output.png";
                File output = new File(SysInfo.FILE_PATH + picName);
                ImageIO.write(result, "png", output);
                res.add(picName);
                System.out.println(familyNames[i] + " 色系处理完成，结果已保存为 " + familyNames[i] + "_output.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public void getPicById(Integer picId,String type, HttpServletRequest request, HttpServletResponse response){
        String path = "";
        if("main".equals(type)){
            PicMain picMain = picMainMapper.selectById(picId);
            path = picMain.getPicPath();
        }else{
            PicCutItem picCutItem = picCutItemMapper.selectById(picId);
            path = picCutItem.getCutPicPath();
        }

        // 设置编码
        response.setCharacterEncoding("UTF-8");
        FileInputStream objInputStream = null;
        ServletOutputStream objOutStream = null;
        String attachName = path;
        Map<String,String> fileInfo = CommonFunction.CutFileInfoFromPath(attachName);
        if(fileInfo==null)
            return;
        response.setContentType("image/"+fileInfo.get("type"));
        response.setHeader("Content-Disposition", "attachment;fileName="+fileInfo.get("name"));
        try {
            objInputStream = new FileInputStream(SysInfo.FILE_PATH + attachName);
            objOutStream = response.getOutputStream();
            int aRead = 0;
            while ((aRead = objInputStream.read()) != -1 & objInputStream != null) {
                objOutStream.write(aRead);
            }
            objOutStream.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                objOutStream.close();
            }catch (IOException e) {

            }
            try {
                objInputStream.close();
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
    }

    public ResponseResult getHis() {
        PicMain picMain = new PicMain();
        picMain.setValidFlag(1);
        QueryWrapper<PicMain> queryWrapper = new QueryWrapper<PicMain>(picMain);
        List<PicMain> picMains = picMainMapper.selectList(queryWrapper);
        if(picMains!=null&&!picMains.isEmpty()){
            for(PicMain item:picMains){
                PicCutItem picCutItem = new PicCutItem();
                picCutItem.setValidFlag(1);
                picCutItem.setPicFk(item.getId());
                QueryWrapper<PicCutItem> queryWrapper2 = new QueryWrapper<PicCutItem>(picCutItem);
                List<PicCutItem> items = picCutItemMapper.selectList(queryWrapper2);
                item.setList(items);
            }
        }

        return  new ResponseResult<>(200,"分割成功",picMains);
    }
}
