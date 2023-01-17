package com.lyc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.common.GlobalException;
import com.lyc.entity.Category;
import com.lyc.entity.Paper;
import com.lyc.mapper.ParamMapper;
import com.lyc.service.CategoryService;
import com.lyc.service.PaperService;
import com.lyc.mapper.PaperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
* @author 蜡笔
* @description 针对表【paper】的数据库操作Service实现
* @createDate 2022-12-25 18:54:01
*/
@Slf4j
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService{

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private CategoryService categoryService;

    /**
     * 从本地导入文章，能够批量导入
     * @param path  文章存放路径
     */
    @Override
    public void importFiles(String path) {
        File dir = new File(path);
        if (!dir.isDirectory()) {
            throw new GlobalException(400, "不是文件目录");
        }
        //找到以md结尾的文件
        File[] files = dir.listFiles(pathname -> pathname.getName().endsWith("md"));

        if (files==null||files.length == 0) {
            throw new GlobalException(400, "没有可导入的 Markdown 文件");
        }

        List<Paper> paperList = packageToList(files);

        for (Paper paper : paperList) {
            // 插入文章
            paperMapper.insert(paper);

        }

    }

    /**
     * 已发布的文章数量
     * @return 数量
     */
    @Override
    public Long releaseCount() {
        LambdaQueryWrapper<Paper> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Paper::getStatus,1);
        Long count = paperMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 对每一篇文章的具体操作
     * @param files
     * @return
     */
    private List<Paper> packageToList(File[] files) {
        List<Paper> paperList = new ArrayList<>(files.length);
        BufferedReader br = null;
        Paper paper;
        for (File file : files) {
            try {
                br = new BufferedReader(new FileReader(file));
                //第一行为标题
                String titleStr = br.readLine();
                //第二行为标签
                String tagsStr = br.readLine();
                //第三行为分类
                String categoryNameStr = br.readLine();
                //第四行和后面若干行为为摘要
                String content;
                StringBuilder subContent = new StringBuilder();
                while ((content=br.readLine())!=null){
                    log.info(content);
                    log.info(content.substring(0,8));

                    //判断行头
                    if(content.startsWith("content:")){
                        break;
                    }

                    // "\r":回车，回到行头。"\n":换行
                    subContent.append(content).append("\r\n");
                }

                //剩下为文章内容
                StringBuilder sb=new StringBuilder();
                do{
                    sb.append(content).append("\r\n");
                }while ((content=br.readLine())!=null);


                //创建一个paper对象，后面依次封装
                paper = new Paper();
                //trim()：去掉首位空格，读取数据
                paper.setTitle(titleStr.substring(titleStr.indexOf(":") + 1).trim());
                //去掉content，subContent标识
                paper.setSubContent(subContent.substring(11));
                paper.setContent(sb.substring(8));

                //通过分类名查找一条分类
                String categoryName = categoryNameStr.substring(categoryNameStr.indexOf(":") + 1).trim();
                LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Category::getName,categoryName);

                Category category = categoryService.getOne(wrapper);
                //分类不存在，创建一个新的分类
                if(category==null){
                    category=new Category();
                    category.setName(categoryName);
                    category.setColor("text-primary");
                    categoryService.save(category);
                }
                //再次查询分类
                category = categoryService.getOne(wrapper);
                //设置分类id
                paper.setCategoryId(category.getId());

                String tags = tagsStr.substring(tagsStr.indexOf(":") + 1).trim();
                if (!StringUtils.isEmpty(tags)) {
                    tags = tags.replace("[", "").replace("]", "");
                }
                //状态初始值为0，不发布
                paper.setStatus(0);

                //随机设置一张背景，位置/static/portal/images/random
                paper.setImgUrl("material-" + (new Random().nextInt(30) + 1) + ".jpg");
                paper.setTags(tags);

                //文章放入集合，读取该目录下所有文章后统一插入
                paperList.add(paper);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return paperList;
    }
}
