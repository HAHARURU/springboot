package com.haru.mybatis.generation.component.impl;

import com.haru.mybatis.generation.component.GenerateBeanComponent;
import com.haru.mybatis.generation.pojo.Field;
import com.haru.mybatis.generation.pojo.OriginData;
import com.haru.mybatis.util.CollectionUtils;
import com.haru.mybatis.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @author HARU
 * @date 2019/5/4
 **/
@Log4j
@Component
public class GenerateBeanComponentImpl implements GenerateBeanComponent {

    private Configuration configuration;

    @PostConstruct
    private void init() {
        configuration = new Configuration(Configuration.VERSION_2_3_19);
        try {
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        } catch (IOException e) {
            log.error("读取freemarker模板文件夹失败", e);
            e.printStackTrace();
        }
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Override
    public void generateBean(OriginData originData) {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> data = new HashMap<>();
        if (originData.getFileSuffixes() == null || originData.getFileSuffixes().size() == 0) {
            originData.setFileSuffixes(CollectionUtils.createList(ArrayList.class, "PO", "BO", "VO"));
        }

        String packagePath = "com";
        if (!StringUtils.isEmpty(originData.getPackageName())) {
            String[] packageArray = originData.getPackageName().split(".");
            packagePath = StringUtils.join(packageArray, "/");
        }

        String path = "src/main/java/" + packagePath;

        addPackages(data, originData.getFields());
        data.put("author", originData.getAuthor());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        data.put("creatDate", LocalDateTime.now().format(dateTimeFormatter));
        data.put("implementsList", CollectionUtils.createList(ArrayList.class, "Serializable"));
        data.put("fields", originData.getFields());

        for (String fileSuffix : originData.getFileSuffixes()) {
            String fileName = originData.getClassName() + ("PO".equals(fileSuffix) ? "" : fileSuffix) + ".java";
            String directoryName = path + "/" + fileSuffix.toLowerCase();
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directoryName + "\\" + fileName);

            data.put("packageName", packagePath + fileSuffix.toLowerCase());
            data.put("serialVersionUID", StringUtils.randomNumberString(19));
            data.put("description", originData.getDescription() + fileSuffix + "类");
            data.put("className", originData.getClassName() + ("PO".equals(fileSuffix) ? "" : fileSuffix));

            FileWriter fileWriter;
            BufferedWriter bufferedWriter;

            try {
                Template temp = configuration.getTemplate("pojo.ftl");

                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);

                temp.process(data, bufferedWriter);

                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                log.error("文件操作失败", e);
                e.printStackTrace();
            } catch (TemplateException e) {
                log.error("freemarker模板操作失败", e);
                e.printStackTrace();
            }
        }
        System.out.println("耗时（毫秒）" + (System.currentTimeMillis() - beginTime));
    }

    /**
     * 添加包，通过判断字段的类型添加，要强制关联的对象生成在同一目录下，之后手动移动到需要的文件夹下；基本类型包装类不需要添加。
     *
     * @param data   模板数据
     * @param fields 需要创建的字段数据
     */
    private void addPackages(Map<String, Object> data, List<Field> fields) {
        List<String> packageList = new ArrayList<>();
        for (Field field : fields) {
            switch (field.getType()) {
                case "Date":
                    packageList.add("java.util.Date");
                    break;
                case "Timestamp":
                    packageList.add("java.sql.Timestamp");
                    break;
                case "BigDecimal":
                    packageList.add("java.math.BigDecimal");
                    break;
                case "List":
                    packageList.add("java.util.List");
                    break;
                default:
                    break;
            }
        }
        data.put("importPackages", packageList);
    }

}
