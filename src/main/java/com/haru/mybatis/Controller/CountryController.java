package com.haru.mybatis.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.dto.ResultDto;
import com.haru.mybatis.enu.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.service.CountryService;
import com.haru.mybatis.util.GsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author HARU
 * @since 2018/5/22
 */
@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    private Gson gson;

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            GsonView.regGson(gb);
            gson = gb.create();
        }
        return gson;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String save(String countryJSON) {
        Country country = getGson().fromJson(countryJSON, new TypeToken<Country>() {
        }.getType());
        countryService.save(country);
        return getGson().toJson(new ResultDto<Country>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), country));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String findAll(String paramsJSON) {
        Map<String, Object> params = getGson().fromJson(paramsJSON, new TypeToken<Map<String, Object>>() {
        }.getType());
        List<Country> countryList = countryService.getAll(Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("size").toString()), params.get("name").toString());
        return getGson().toJson(new ResultDto<List<Country>>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), countryList));
    }

    @RequestMapping(value = "/findAllGet", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String findAllGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return getGson().toJson(new ResultDto<List<Country>>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), null));
    }
}
