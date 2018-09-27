package com.haru.mybatis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.mongoRepository.CountryMongoRepository;
import com.haru.mybatis.repository.CountryRepository;
import com.haru.mybatis.schedule.AsyncTask;
import com.haru.mybatis.service.CountryService;
import com.haru.mybatis.util.GsonView;
import com.haru.mybatis.util.encryption.MD5;
import com.haru.mybatis.util.encryption.SortCollection;
import com.haru.mybatis.util.http.HashUtils;
import com.haru.mybatis.util.http.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisApplicationTests {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CountryMongoRepository countryMongoRepository;

    @Autowired
    private AsyncTask asyncTask;

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    private String testThread(String no) {
        System.out.println("子线程开始" + no);
        try {
            Thread.sleep(3000);
//            String s = null;
//            s.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return no;
    }

    @Test
    public void contextLoads() throws ExecutionException, InterruptedException {

        String s = null;
        Optional<String> s2 = Optional.ofNullable(s);
        s2.ifPresent(v -> Optional.ofNullable(v.toUpperCase()).ifPresent(v2 -> Optional.ofNullable(v.length()).ifPresent(v3 -> System.out.println(v3))));

//        //多线程测试
//        System.out.println("主线程开始");
//
//        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> this.testThread("01"));
//        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> this.testThread("02"));

//        CompletableFuture<Void> completableFuture = CompletableFuture.allOf(completableFuture1);

//        completableFuture.join();//等待子线程执行结束
//        if (booleanCompletableFuture1.get()) {    //get方法会阻塞主线程，但是allOf无效
//            System.out.println("主线程结束");
//        } else {
//            System.out.println("子线程出错");
//        }

//        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(completableFuture1, completableFuture2);
//
//        System.out.println(objectCompletableFuture.get());
//
//        CompletableFuture<String> stringCompletableFuture = completableFuture1.thenCompose(reuslt -> CompletableFuture.supplyAsync(()
//                -> this.testThread(reuslt + "-1")));
//        System.out.println(stringCompletableFuture.get());
//
//        stringCompletableFuture.join();

//        CompletableFuture<String> stringCompletableFuture = completableFuture1.thenCombine(CompletableFuture.supplyAsync(() -> this.testThread
//                ("03")), (result1, result2) -> result1 + result2);
//
//        System.out.println(stringCompletableFuture.get());

//        System.out.println(completableFuture1.get());
//        completableFuture1.thenAccept(result ->  System.out.println(result + "@"));
//
//        System.out.println("主线程开始结束");


//        Method method = null;
//        try {
////            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//            method = CountryService.class.getMethod( "test", String.class, Integer.class );
////            String[] parameterNames = u.getParameterNames(method);
//            for( final Parameter parameter: method.getParameters()) {
//                System.out.println( "Parameter: " + parameter.getName() );
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }



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

//        Country country = countryRepository.findByCode("CN");
//        System.out.println("第一次查询：" + country.getName());
//
//        Country country2 = countryRepository.findByCode("CN");
//        System.out.println("第二次查询：" + country2.getName());

//        countryMongoRepository.save(country);

//        country.setName("中华");
//        countryRepository.save(country);
//
//        Country country3 = countryRepository.findByCode("CN");
//        System.out.println("第三次查询：" + country3.getName());
//
//        long start = System.currentTimeMillis();
//
//        try {
//            Future<String> task1 = asyncTask.doTaskOne();
//            Future<String> task2 = asyncTask.doTaskTwo();
//            Future<String> task3 = asyncTask.doTaskThree();
//
//            while (true) {
//                if (task1.isDone() && task2.isDone() && task3.isDone()) {
//                    // 三个任务都调用完成，退出循环等待
//                    break;
//                }
//                Thread.sleep(1000);
//            }
//
//            long end = System.currentTimeMillis();
//
//            System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        try {
////            asyncTask.doTaskOnePool();
////            asyncTask.doTaskTwoPool();
////            asyncTask.doTaskThreePool();
////
////            Thread.currentThread().join();
////
////            System.out.println("任务全部完成");
//
//            for (int i = 0; i < 10; i++) {
//                asyncTask.doTaskOnePool();
//                asyncTask.doTaskTwoPool();
//                asyncTask.doTaskThreePool();
//
//                if (i == 9) {
//                    System.exit(0);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Map<String, String> params = new HashMap<>();
//        String body = "{\"id\":\"4a937e7c-c988-4b23-94c0-0cac9df568b9\",\"name\":\"法国\",\"code\":\"FN\",\"state\":\"启用\",\"createTime\":\"2018-07-30 15:17:58\",\"valid\":true}";
//
//        params = getGson().fromJson(body, new TypeToken<Map<String, Object>>() {
//        }.getType());
//        params.put("MD5key", "haruKey");

//        ConcurrentSkipListMap cbody = new ConcurrentSkipListMap();
//        cbody.putAll(params);
//        String sourceStr = SortCollection.sort(null, cbody);    //排序
//        String sign = MD5.sign(sourceStr, "haruKey", "UTF-8");      //加密
//        params.clear();
//        params.put("sign", sign);

//        String post = HttpUtils.post("http://localhost:4001/country/insertCountryOnly", HashUtils.getMap("Content-type", "application/json;charset=UTF-8"),
//                params, "UTF-8", body);
//        System.out.println(post);

    }
}