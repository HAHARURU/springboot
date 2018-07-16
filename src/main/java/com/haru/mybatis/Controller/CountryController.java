package com.haru.mybatis.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haru.mybatis.dto.ResultDto;
import com.haru.mybatis.enu.ErrorEnum;
import com.haru.mybatis.exception.CustomException;
import com.haru.mybatis.model.City;
import com.haru.mybatis.model.Country;
import com.haru.mybatis.model.vo.CityVo;
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
        List<City> city = countryService.getCity(cityVo);
        ResultDto<List<City>> listResultDto = new ResultDto<>(String.valueOf(ErrorEnum.成功.getValue()), ErrorEnum.成功.name(), city);
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
}
