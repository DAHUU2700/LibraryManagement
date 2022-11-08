package com.example.springboot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.entity.Admin;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookService;
import com.example.springboot.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    IBookService bookService;

    //  添加
    @PostMapping("/save")
    public Result save(@RequestBody Book book) {
        bookService.save(book);
        return Result.success();
    }

    //  更新
    @PutMapping("/update")
    public Result update(@RequestBody Book book) {
        bookService.update(book);
        return Result.success();
    }

    //  删除（根据id）
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    //  根据id查询
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        return Result.success(book);
    }

    //  查询所有
    @GetMapping("/list")
    public Result list() {
        List<Book> list = bookService.list();
        return Result.success(list);
    }

    //  根据条件查询
    @GetMapping("/page")
    public Result page(BookPageRequest pageRequest) {
        return Result.success(bookService.page(pageRequest));
    }

    private static final String BASE_FILE_PATH = System.getProperty("user.dir") + "/files/";
    //  上传文件
    @PostMapping("/file/upload")
    public Result uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StrUtil.isBlank(originalFilename)) {
            return Result.error("文件上传失败");
        }
        long flag = System.currentTimeMillis();
        String filePath = BASE_FILE_PATH + flag + "_" + originalFilename;
        try {
            FileUtil.mkParentDirs(filePath);
            // 创建父级目录
            file.transferTo(FileUtil.file(filePath));
            Admin currentAdmin = TokenUtils.getCurrentAdmin();
            String token = TokenUtils.genToken(currentAdmin.getId().toString(), currentAdmin.getPassword(), 15);
            String url = "http://localhost:9090/api/book/file/download/" + flag + "?&token=" + token;
            if (originalFilename.endsWith("png") || originalFilename.endsWith("jpg") || originalFilename.endsWith("pdf")) {
                url += "&play=1";
            }
            return Result.success(url);
        } catch (Exception e) {
            log.info("文件上传失败", e);
        }
        return Result.error("文件上传失败");
    }

    //  下载文件
    @GetMapping("/file/download/{flag}")
    public void download(@PathVariable String flag, @RequestParam(required = false) String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(BASE_FILE_PATH);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        //  System.currentTimeMillis() + originalFilename
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("_") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(BASE_FILE_PATH + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }
}
