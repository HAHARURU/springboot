package com.haru.mybatis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.generation.component.GenerateBeanComponent;
import com.haru.mybatis.generation.pojo.Field;
import com.haru.mybatis.generation.pojo.OriginData;
import com.haru.mybatis.model.Address;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.Item;
import com.haru.mybatis.model.Order;
import com.haru.mybatis.mongoRepository.CountryMongoRepository;
import com.haru.mybatis.repository.CountryRepository;
import com.haru.mybatis.schedule.AsyncTask;
import com.haru.mybatis.service.CountryService;
import com.haru.mybatis.util.BeanUtils;
import com.haru.mybatis.util.CollectionUtils;
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

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
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

    @Resource
    private GenerateBeanComponent generateBeanComponent;

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

    @Test
    public void stringFormat() {
        String s = "{\"sId\":\"681b3a87-62a7-43f3-99aa-c65740ce982b\",\"sType\":1}," +
                "{\"sId\":\"38203c38-5784-4e8e-b464-73e4144c55f5\",\"sType\":1}," +
                "{\"sId\":\"b31bda05-7fa6-41cd-b76e-c791ecbf1800\",\"sType\":1}," +
                "{\"sId\":\"4f17a4b4-8602-49cc-9d45-8e9c84b17462\",\"sType\":1}," +
                "{\"sId\":\"1735388d-24c2-45c9-9755-5af475c3834a\",\"sType\":1}," +
                "{\"sId\":\"e7ff0ac6-9f52-404b-a535-e5ef987b7c0a\",\"sType\":1}," +
                "{\"sId\":\"dbc6e5e9-b8c5-49fa-8621-887965b44dc3\",\"sType\":1}," +
                "{\"sId\":\"26ca274d-9ae3-40eb-9894-09adc0dbe267\",\"sType\":1}," +
                "{\"sId\":\"6b503284-4362-4db4-bb35-03e5858ebce1\",\"sType\":1}," +
                "{\"sId\":\"7ea8539e-525b-476c-b1d2-9712c900e563\",\"sType\":1}," +
                "{\"sId\":\"2c4e8c3a-1ccb-4b1a-8dc5-fce277de39db\",\"sType\":1}," +
                "{\"sId\":\"da9efcd2-88ff-4205-81fc-6053c5f98ffc\",\"sType\":1}," +
                "{\"sId\":\"209307f1-1a2e-4423-96b0-c95360ca205c\",\"sType\":1}," +
                "{\"sId\":\"10ad8868-3327-4113-9b1c-150eb297f518\",\"sType\":1}," +
                "{\"sId\":\"ce179b07-d248-48f4-9030-d09bfee2e2b7\",\"sType\":1}," +
                "{\"sId\":\"6ada3ec8-f33d-4f53-be0e-6c6966bca93f\",\"sType\":1}," +
                "{\"sId\":\"105d531f-66a3-4922-b822-a1bba0c547eb\",\"sType\":1}," +
                "{\"sId\":\"ca0e2605-99ad-4311-816c-175186e8e884\",\"sType\":1}," +
                "{\"sId\":\"684dcd6b-a56a-41e1-899e-3e18882e6f8f\",\"sType\":1}," +
                "{\"sId\":\"bc3bc79d-9719-4f9a-956b-f29d768f671f\",\"sType\":1}," +
                "{\"sId\":\"80069fa5-51f8-42d9-b9b4-7fe1ba191b83\",\"sType\":1}," +
                "{\"sId\":\"90bb3f87-0ee3-4cbc-9d00-836bf7e07ed9\",\"sType\":1}," +
                "{\"sId\":\"0298c23e-215f-46f8-93fd-a48a769925d0\",\"sType\":1}," +
                "{\"sId\":\"14da0bce-aa2e-4904-963a-567c8fab636c\",\"sType\":1}," +
                "{\"sId\":\"e286b5da-5fa0-4089-ab20-20d999e5bff6\",\"sType\":1}," +
                "{\"sId\":\"a04c2b8e-bb94-49cc-b38e-49d2c15bc445\",\"sType\":1}," +
                "{\"sId\":\"848d94e8-3525-43ca-8f33-d2cb34be2108\",\"sType\":1}," +
                "{\"sId\":\"416addc5-4c8c-410c-b0d9-6246a36f29aa\",\"sType\":1}," +
                "{\"sId\":\"07b2d540-7025-4b86-ba0d-cf28aa976ced\",\"sType\":1}," +
                "{\"sId\":\"17a96a5a-793c-4182-b01e-c765c899979b\",\"sType\":1}," +
                "{\"sId\":\"cf8a0e86-d000-418a-add6-7e6811fc4192\",\"sType\":1}," +
                "{\"sId\":\"5a2dedd4-c9bc-4db2-938c-de6ec6a5adf4\",\"sType\":1}," +
                "{\"sId\":\"43a901a5-9779-4e7b-8397-d8dabb1d866b\",\"sType\":1}," +
                "{\"sId\":\"2b1f1fb8-a618-4094-b9de-01863b1c8796\",\"sType\":1}," +
                "{\"sId\":\"257fe863-c03a-4f42-b80f-8de2108763f5\",\"sType\":1}," +
                "{\"sId\":\"177b3324-a661-4158-8c9f-b8db06c97a13\",\"sType\":1}," +
                "{\"sId\":\"af462dfb-e796-4dcb-9e50-04c7204c9b57\",\"sType\":1}," +
                "{\"sId\":\"8b688fe4-83f1-4459-9552-77c32303a898\",\"sType\":1}," +
                "{\"sId\":\"6ae60b48-f632-49cd-9ead-71c3b53e8331\",\"sType\":1}," +
                "{\"sId\":\"2aa7e661-974e-478e-a0b5-da48c26b3aae\",\"sType\":1}," +
                "{\"sId\":\"1f2c3776-ac46-431b-8740-da2df4ce2df9\",\"sType\":1}," +
                "{\"sId\":\"ce563e2a-b651-4848-a5f8-d3e4eb3bf8d3\",\"sType\":1}," +
                "{\"sId\":\"c7cef101-b493-4bfa-bc07-e920570c0f3e\",\"sType\":1}," +
                "{\"sId\":\"da5f2778-8251-47c6-85af-298502e65b35\",\"sType\":1}," +
                "{\"sId\":\"9ccfd71c-4c2f-4766-ba66-b67291d00f67\",\"sType\":1}," +
                "{\"sId\":\"4df08883-d98a-433a-8eb0-ba07fa7135a6\",\"sType\":1}," +
                "{\"sId\":\"30cc9db5-fb86-4400-b4a8-f2ec97bbed87\",\"sType\":1}," +
                "{\"sId\":\"aaf9be27-5031-41fe-9079-b19387502eb6\",\"sType\":1}," +
                "{\"sId\":\"43ff94b5-415a-4d64-a0ee-3083cca3af2b\",\"sType\":1}," +
                "{\"sId\":\"3a53b952-4137-42d7-87b1-4dc28698b2d0\",\"sType\":1}," +
                "{\"sId\":\"3a35db70-214d-4a7c-8595-5db619b37295\",\"sType\":1},";
        String replace = s.replace("{\"sId\":\"", "'");
        String replace1 = replace.replace("\",\"sType\":1},", "',");
        System.out.println("replace1::::" +  replace1);
    }

    @Test
    public void deepCopyTest() {
        // 创建地址对象
        Address address = new Address();
        address.setCity("上海");
        address.setId(1);

        // 创建商品集合
        Item fruits = new Item();
        fruits.setId(1);
        fruits.setName("水果");
        Item meat = new Item();
        meat.setId(2);
        meat.setName("肉");
        List<Item> items = CollectionUtils.createList(ArrayList.class, fruits, meat);

        // 创建订单对象
        Order order = new Order();
        order.setAddress(address);
        order.setId(1);
        order.setOrderCode("01");
        order.setItems(items);

        // 获取类的字段名集合
        List<String> classFieldNames = BeanUtils.classFieldNames(Order.class);

        // 深拷贝
        Order deepCopyOrder = new Order();
        BeanUtils.deepCopy(order, deepCopyOrder,  CollectionUtils.getListByIndex(classFieldNames, 0, 2));

        System.out.println("over");

    }

    @Test
    public void testPath() {
        OriginData originData = new OriginData();
        originData.setAuthor("haru");
        originData.setPackageName("com.mybatis.model");
        originData.setDescription("用户");
        originData.setClassName("User");
        Field fieldId = new Field();
        fieldId.setName("id");
        fieldId.setType("Integer");
        fieldId.setComments("用户id");
        Field fieldDate = new Field();
        fieldDate.setName("createDate");
        fieldDate.setType("Date");
        fieldDate.setComments("创建时间");
        originData.setFields(CollectionUtils.createList(ArrayList.class, fieldId, fieldDate));
        generateBeanComponent.generateBean(originData);
    }
}