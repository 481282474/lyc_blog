package com.lyc;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyc.entity.Paper;
import com.lyc.service.PaperService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest
class LycBlogApplicationTests {
    @Autowired
    private PaperService paperService;

    //测试分页查询
    @Test
    public void index() {
        LambdaQueryWrapper<Paper> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Paper::getUpdateTime);
        Page<Paper> pageInfo = new Page<>(1,10);

        Page<Paper> result = paperService.page(pageInfo, wrapper);
        System.out.println(result.getRecords());
        System.out.println(result.hasPrevious());
        result.getCurrent();
    }

    //测试从md文件读取数据
    @Test
    public void paperMd(){
        String content="content:原来是小瘪三，原来是小别扇，";

        String part=content.substring(0,8);
        System.out.println(content);
        System.out.println(part);
        System.out.println(content.substring(8));
    }

}
