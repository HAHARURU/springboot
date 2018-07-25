package com.haru.mybatis;

import com.haru.mybatis.model.Country;
import com.haru.mybatis.mongoRepository.CountryMongoRepository;
import com.haru.mybatis.repository.CountryRepository;
import com.haru.mybatis.util.http.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMongoRepository countryMongoRepository;

    @Test
    public void contextLoads() {
//        FTPUtils ftpUtils = new FTPUtils("Administrator", "123", "192.168.90.11", 21);
//        ftpUtils.connectServer(false);
//        ftpUtils.mkdir("m2/m12/m112");
//        List<String> strings = ftpUtils.listAllFileNames("haru", new FTPFileFilter() {
//            @Override
//            public boolean accept(FTPFile file) {   //true不过滤
//                return true;
//            }ss
//        });
//        ftpUtils.upFile("上传", "他.png", "C:\\Users\\Administrator\\Desktop\\图.png");
//        ftpUtils.downFile("上传", "他.png", "D:\\job\\project\\我");
////        ftpUtils.closeConnect();
//        Map<String ,String> params = new HashMap<>();
//        params.put("page", "1");
//        params.put("size", "2");
//        params.put("name", "法国");
//        HttpUtils.get("http://localhost:4001/country/findAllGet", params,null);

        Country country = countryRepository.findByCode("CN");
//        System.out.println("第一次查询：" + country.getName());
//
//        Country country2 = countryRepository.findByCode("CN");
//        System.out.println("第二次查询：" + country2.getName());

        countryMongoRepository.save(country);

//        country.setName("中华");
//        countryRepository.save(country);
//
//        Country country3 = countryRepository.findByCode("CN");
//        System.out.println("第三次查询：" + country3.getName());
    }
}