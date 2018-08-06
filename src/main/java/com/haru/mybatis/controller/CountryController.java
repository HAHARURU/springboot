package com.haru.mybatis.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.dto.ResultDto;
import com.haru.mybatis.enumPackage.ErrorEnum;
import com.haru.mybatis.exception.AccessApiGatewayException;
import com.haru.mybatis.exception.ClassNotFoundApiGatewayException;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.exception.MethodNotFoundApiGatewayException;
import com.haru.mybatis.model.City;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.vo.CityVo;
import com.haru.mybatis.service.ApiGatewayApiService;
import com.haru.mybatis.service.imp.CountryServiceImp;
import com.haru.mybatis.util.GsonView;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static com.haru.mybatis.service.ApiGatewayAbstractService.getFromStack;

/**
 * @author HARU
 * @since 2018/5/22
 */
@RestController
@RequestMapping("/country")
public class CountryController {
    static Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryServiceImp countryService;

    @Autowired
    private ApiGatewayApiService apiGatewayApiService;

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    /**
     * 网关请求接口
     *
     * @param serviceName 服务名称
     * @param methodName  方法名称
     * @param request     请求，主要使用其中的各种参数
     * @return 执行结果
     */
    @RequestMapping(value = "/{serviceName}/{methodName}", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String execute(@PathVariable String serviceName, @PathVariable String methodName, HttpServletRequest request) {
        Object resultObj = null;
        String errMsg = "";
        try {
            resultObj = apiGatewayApiService.executeService(serviceName, methodName, request);
        } catch (ClassNotFoundApiGatewayException e) {
            errMsg = e.getMsg();
        } catch (MethodNotFoundApiGatewayException e) {
            errMsg = e.getMsg();
        } catch (AccessApiGatewayException e) {
            errMsg = e.getMsg();
        } catch (UnsupportedEncodingException e) {
            errMsg = "转码出错";
        } catch (InvocationTargetException e) {
            errMsg = processException(e.getTargetException());
        }

        if (StringUtils.isNotBlank(errMsg)) {
            ResultDto<String> stringResultDto = new ResultDto<String>(String.valueOf(ErrorEnum.失败.getValue()), ErrorEnum.失败.name(), errMsg);
            String json = getGson().toJson(stringResultDto);
            return json;
        }
        ResultDto<Object> result = new ResultDto<Object>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), resultObj);
        String json = getGson().toJson(result);
        return json;
    }

    public static String processException(Throwable ex) {
        CustomException bizEx = getFromStack(CustomException.class, ex);
        if (bizEx != null) {
            logger.error("业务异常", ex);
            return ex.getMessage();
        }

        //乐观锁异常


        ConstraintViolationException consEx = getFromStack(ConstraintViolationException.class, ex);
        if (consEx != null) {
            return "数据保存失败，违反数据库约束";
        }
        RuntimeException runEx = getFromStack(RuntimeException.class, ex);
        if (runEx != null) {
            logger.error("运行异常", runEx);
            String msg = runEx.getMessage();
            if (StringUtils.isNotEmpty(msg)) {
                if (msg.startsWith("com.haru.mybatis.exception.CustomException")) {
                    String[] errorMsgs = msg.split("\\n");
                    if (errorMsgs.length > 0) {
                        return errorMsgs[1];
                    }
                }

                //乐观锁异常


                if (msg.startsWith("org.hibernate.exception.ConstraintViolationException")) {
                    return "数据已被其他人修改，请重填写后保存";
                }
                if (msg.startsWith("org.springframework.dao.DataIntegrityViolationException")) {
                    return "此数据已被业务占用，无法删除";
                }
            }
            msg = "执行服务异常";
            return msg;
        }
        if (ex != null) {
            logger.error("运行异常", runEx);
            return ("未知异常");
        }
        return ("系统发生异常");
    }


    @RequestMapping(value = "/insertCountryOnly", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String insertCountryOnly(String countryJSON) {
        Country country = getGson().fromJson(countryJSON, new TypeToken<Country>() {
        }.getType());
        ResultDto<Country> resultDto = new ResultDto<Country>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.insertCountryOnly(country));
        String json = getGson().toJson(resultDto);
        return json;
    }

    @RequestMapping(value = "/findAllGet", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String findAllGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return getGson().toJson(new ResultDto<List<Country>>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), null));
    }

    @RequestMapping(value = "/getCity", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getCity(String cityJSON) {
        CityVo cityVo = getGson().fromJson(cityJSON, new TypeToken<CityVo>() {
        }.getType());
        ResultDto<List<City>> listResultDto = new ResultDto<>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.getCity(cityVo));
        String json = getGson().toJson(listResultDto);
        return json;
    }

    @RequestMapping(value = "/getAllCountries", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getAllCountries() {
        return getGson().toJson(new ResultDto<List<Country>>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.getAllCountries()));
    }

    @RequestMapping(value = "/insertCity", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String insertCity(String cityJSON) {
        City city = getGson().fromJson(cityJSON, new TypeToken<City>() {
        }.getType());
        ResultDto<City> resultDto = new ResultDto<City>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.insertCity(city));
        String json = getGson().toJson(resultDto);
        return json;
    }

    @RequestMapping(value = "/insertCountry", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String insertCountry(String countryJSON) {
        Country country = getGson().fromJson(countryJSON, new TypeToken<Country>() {
        }.getType());
        ResultDto<Country> resultDto = new ResultDto<Country>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.insertCountry(country));
        String json = getGson().toJson(resultDto);
        return json;
    }

    @RequestMapping(value = "/updateCountryWithCity", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String updateCountryWithCity(String countryJSON) {
        Country country = getGson().fromJson(countryJSON, new TypeToken<Country>() {
        }.getType());
        ResultDto<Country> resultDto = new ResultDto<Country>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryService.updateCountryWithCity(country));
        String json = getGson().toJson(resultDto);
        return json;
    }

    @RequestMapping(value = "/redisCountry", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public void redisCountry() {
        countryService.redisCountry();
    }
}
