package com.Form;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.OrderList;
import com.util.Attribute;
import com.util.PubicMethod;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FormController {

    @Autowired
    FormServiceImpl formService;

    /**
     *
     */
    @RequestMapping(value = "/form/getPic", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin
    @ResponseBody
    private byte[] getPic(HttpServletRequest request) throws Exception {
        String picId = request.getParameter("picId");
        String dir = Attribute.dir_s;
        File file = new File(dir + picId + ".png");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] pic_bytes = new byte[fileInputStream.available()];
        fileInputStream.read(pic_bytes, 0, fileInputStream.available());
        return pic_bytes;
    }


    @RequestMapping(value = "/form/findFoodByNameW", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String findFoodByName(@RequestBody String foodName) throws Exception {
        JSONObject Json_foodName = JSONObject.parseObject(foodName);
        //防止空值
        String foodName_c = "" + Json_foodName.getString("foodName");
        //使用模糊查询
        JSONObject returnJson = new JSONObject();
        List<Map<String, Object>> result = formService.findFoodByName(foodName_c);

        if (null == result) {
            returnJson.put("flag", "0");
            returnJson.put("msg", "暂未搜索到相关菜品");
        } else {
            returnJson.put("flag", "1");
            //用来防止菜品数据的
            JSONArray jsonArray = new JSONArray();

            //传回所有的菜品以及饮料等等
            for (int i = 0; i < result.size(); i++) {
                JSONObject innerParam = new JSONObject();
                Map<String, Object> tempMap = result.get(i);
                //挑选菜品类型
                innerParam.put("foodName", tempMap.get("M_foodName"));
                innerParam.put("foodCode", tempMap.get("M_foodCode"));
                innerParam.put("foodPrice", tempMap.get("M_foodPrice"));
                innerParam.put("foodClassName", tempMap.get("FC_className"));
                innerParam.put("foodClassID", tempMap.get("M_foodClass"));
                innerParam.put("foodDesc", tempMap.get("M_foodDesc"));
                innerParam.put("foodEnable", tempMap.get("M_foodEnable"));
                innerParam.put("foodImages",Attribute.pic+innerParam.get("M_foodPic"));
                jsonArray.add(innerParam);
            }
            returnJson.put("msg", jsonArray);
        }
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/findFoodByNameC", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String findFoodByNameC(@RequestBody String foodName) throws Exception {
        JSONObject Json_foodName = JSONObject.parseObject(foodName);
        //防止空值
        String foodName_c = "" + Json_foodName.getString("foodName");
        //使用模糊查询
        JSONObject resultJson = new JSONObject();
        List<Map<String, Object>> resultMenu = formService.findFoodByName(foodName_c);
        List<Map<String, Object>> foodClass = formService.getFoodClass();
        if (null == resultMenu) {
            resultJson.put("flag", "0");
            resultJson.put("msg", "未有菜品");
        } else {
            resultJson.put("flag", "1");
            //foodClassID & foodClassName && goods_list<Array>
            JSONArray resultJson1Array = new JSONArray();
            for (int i = 0; i < foodClass.size(); i++) {
                JSONObject innerJson = new JSONObject();
                int foodClassID = (int) foodClass.get(i).get("FC_id");
                String foodClassName = (String) foodClass.get(i).get("FC_className");
                innerJson.put("foodClassID", foodClassID);
                innerJson.put("foodClassName", foodClassName);

                //添加goods_list
                JSONArray jsonArray = new JSONArray();
                for (Map<String, Object> innerParam : resultMenu) {
                    if ((int) (innerParam.get("M_foodClass")) == foodClassID) {
                        JSONObject temp = new JSONObject();
                        temp.put("foodPrice", innerParam.get("M_foodPrice"));
                        temp.put("foodName", innerParam.get("M_foodName"));
                        temp.put("foodCode", innerParam.get("M_foodCode"));
                        temp.put("foodDesc", innerParam.get("M_foodDesc"));
                        temp.put("foodEnable", innerParam.get("M_foodEnable"));
                        temp.put("foodImages", Attribute.pic+innerParam.get("M_foodPic"));
                        jsonArray.add(temp);
                    }
                }
                innerJson.put("goods_list", jsonArray);
                resultJson1Array.add(innerJson);

            }

            resultJson.put("goods", resultJson1Array);
        }
        return resultJson.toString();
    }

    /**
     * 创建订单
     * GET方法
     * 无参数
     */
    @RequestMapping(value = "/form/createFormW", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String createFormW() throws Exception {

        //韦海涛使用的创建菜单接口
        JSONObject returnJson = new JSONObject();
        List<Map<String, Object>> result = formService.getMenu();
        List<Map<String, Object>> foodClass = formService.getFoodClass();

        //获取几种菜品
        int size = foodClass.size();

        //创建一个长度为size+1的jsonArrays链表
        JSONArray jsonArrays = new JSONArray();

        if (null == result) {
            returnJson.put("flag", "0");
            returnJson.put("msg", "未有菜品");
        } else {
            returnJson.put("flag", "1");
            //传回所有的菜品以及饮料等等
            for (int i = 0; i < result.size(); i++) {
                JSONObject innerParam = new JSONObject();
                Map<String, Object> tempMap = result.get(i);
                //挑选菜品类型
                int M_foodClass = (int) tempMap.get("M_foodClass");

                innerParam.put("foodName", tempMap.get("M_foodName"));
                innerParam.put("foodCode", tempMap.get("M_foodCode"));
                innerParam.put("foodPrice", tempMap.get("M_foodPrice"));
                innerParam.put("foodClassName", tempMap.get("FC_className"));
                innerParam.put("foodClassID", tempMap.get("M_foodClass"));
                innerParam.put("foodDesc", tempMap.get("M_foodDesc"));
                innerParam.put("foodEnable", tempMap.get("M_foodEnable"));
                innerParam.put("foodImages", Attribute.pic+tempMap.get("M_foodPic"));
                jsonArrays.add(innerParam);
            }

            JSONObject type = new JSONObject();
            for (int i = 0; i < size; i++) {
                type.put(foodClass.get(i).get("FC_id") + "", foodClass.get(i).get("FC_className"));

            }
            returnJson.put("type", type);
            returnJson.put("msg", jsonArrays);
        }
        return returnJson.toString();
    }


    @RequestMapping(value = "/form/createFormC", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String createFormC() throws Exception {

        //陈京南使用的创建菜单接口
        JSONObject resultJson = new JSONObject();
        List<Map<String, Object>> resultMenu = formService.getMenu();
        List<Map<String, Object>> foodClass = formService.getFoodClass();

        //创建一个长度为size+1的jsonArrays链表
        JSONArray jsonArrays = new JSONArray();

        if (null == resultMenu) {
            resultJson.put("flag", "0");
            resultJson.put("msg", "未有菜品");
        } else {
            resultJson.put("flag", "1");
            //foodClassID & foodClassName && goods_list<Array>
            JSONArray resultJson1Array = new JSONArray();
            for (int i = 0; i < foodClass.size(); i++) {
                JSONObject innerJson = new JSONObject();
                int foodClassID = (int) foodClass.get(i).get("FC_id");
                String foodClassName = (String) foodClass.get(i).get("FC_className");
                innerJson.put("foodClassID", foodClassID);
                innerJson.put("foodClassName", foodClassName);

                //添加goods_list
                JSONArray jsonArray = new JSONArray();
                for (Map<String, Object> innerParam : resultMenu) {
                    if ((int) (innerParam.get("M_foodClass")) == foodClassID) {
                        JSONObject temp = new JSONObject();
                        temp.put("foodCode", innerParam.get("M_foodCode"));
                        temp.put("foodPrice", innerParam.get("M_foodPrice"));
                        temp.put("foodName", innerParam.get("M_foodName"));
                        temp.put("foodDesc", innerParam.get("M_foodDesc"));
                        temp.put("foodEnable", innerParam.get("M_foodEnable"));
                        temp.put("foodImages", Attribute.pic+innerParam.get("M_foodPic"));
                        jsonArray.add(temp);
                    }
                }
                innerJson.put("goods_list", jsonArray);
                resultJson1Array.add(innerJson);

            }

            resultJson.put("goods", resultJson1Array);
        }
        return resultJson.toString();
    }

    @RequestMapping(value = "/form/addFoodClass", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String addFoodClass(@RequestBody String jsonParam) throws Exception {
        JSONObject foodClassJson = JSONObject.parseObject(jsonParam);
        //防止空值
        String foodClassName = "" + foodClassJson.getString("foodClassName");
        //使用模糊查询
        JSONObject returnJson = new JSONObject();
        formService.addFoodClass(foodClassName);
        returnJson.put("flag", "1");
        returnJson.put("msg", "添加成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/deleteFoodClass", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String deleteFoodClass(@RequestBody String jsonParam) throws Exception {
        JSONObject foodClassJson = JSONObject.parseObject(jsonParam);
        //防止空值
        int foodClassID = foodClassJson.getIntValue("foodClassID");
        JSONObject returnJson = new JSONObject();
        formService.deleteFoodClass(foodClassID);
        returnJson.put("flag", "1");
        returnJson.put("msg", "删除成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/addMenuFood", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String addMenuFood(@RequestBody String jsonParam) throws Exception {
        JSONObject foodClassJson = JSONObject.parseObject(jsonParam);

        String foodName = foodClassJson.getString("foodName");
        String foodCode = "fod" + PubicMethod.getAcademeCode();
        float foodPrice = foodClassJson.getFloat("foodPrice");
        int foodClass = foodClassJson.getInteger("foodClass");
        String foodDesc = foodClassJson.getString("foodDesc");
        String foodPic = foodClassJson.getString("foodPic");
        if(foodPic.isEmpty()){
            //占位符
            foodPic = "food";
        }

        Map<String, Object> param = new HashMap<>();
        param.put("foodName", foodName);
        param.put("foodCode", foodCode);
        param.put("foodPrice", foodPrice);
        param.put("foodClass", foodClass);
        param.put("foodDesc", foodDesc);
        param.put("foodPic", foodPic);

        //返回结果
        JSONObject returnJson = new JSONObject();
        formService.addMenuFood(param);
        returnJson.put("flag", "1");
        returnJson.put("msg", "添加成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/updateMenuFood", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String updateMenuFood(@RequestBody String jsonParam) throws Exception {
        JSONObject foodClassJson = JSONObject.parseObject(jsonParam);

        String foodName = foodClassJson.getString("foodName");
        String foodCode = foodClassJson.getString("foodCode");
        float foodPrice = foodClassJson.getFloat("foodPrice");
        int foodClass = foodClassJson.getInteger("foodClass");
        String foodDesc = foodClassJson.getString("foodDesc");
        int foodEnable = foodClassJson.getInteger("foodEnable");
        String foodPic = foodClassJson.getString("foodPic");

        Map<String, Object> param = new HashMap<>();
        param.put("foodName", foodName);
        param.put("foodCode", foodCode);
        param.put("foodPrice", foodPrice);
        param.put("foodClass", foodClass);
        param.put("foodDesc", foodDesc);
        param.put("foodEnable", foodEnable);
        param.put("foodPic", foodPic);

        //返回结果
        JSONObject returnJson = new JSONObject();
        formService.updateMenuFood(param);
        returnJson.put("flag", "1");
        returnJson.put("msg", "更新成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/deleteMenuFood", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String deleteMenuFood(@RequestBody String jsonParam) throws Exception {
        JSONObject foodClassJson = JSONObject.parseObject(jsonParam);

        String foodCode = foodClassJson.getString("foodCode");

        //返回结果
        JSONObject returnJson = new JSONObject();
        formService.deleteMenuFood(foodCode);
        returnJson.put("flag", "1");
        returnJson.put("msg", "删除成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/updateMenuFoodEnable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String updateMenuFoodEnable(@RequestBody String jsonParam) throws Exception {
        JSONObject foodJson = JSONObject.parseObject(jsonParam);

        int foodEnable = foodJson.getInteger("foodEnable");
        String foodCode = foodJson.getString("foodCode");

        Map<String, Object> param = new HashMap<>();
        param.put("foodEnable", foodEnable);
        param.put("foodCode", foodCode);
        //返回结果
        JSONObject returnJson = new JSONObject();
        formService.updateMenuFoodEnable(param);
        returnJson.put("flag", "1");
        returnJson.put("msg", "修改成功");
        return returnJson.toString();
    }

    @RequestMapping(value = "/form/getFoodClass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getFoodClass() throws Exception {

        //返回结果
        JSONObject returnJson = new JSONObject();
        List<Map<String, Object>> params = formService.getFoodClass();
        returnJson.put("flag", "1");

        JSONArray innerJson = new JSONArray();

        for (Map<String, Object> param : params) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", param.get("FC_className"));
            jsonObject.put("index", param.get("FC_id"));
            innerJson.add(jsonObject);
        }
        returnJson.put("len", params.size());
        returnJson.put("msg", innerJson);
        return returnJson.toString();
    }

//    {
//        "orderCode":"ord1594645095",
//            "userOrderCode":"A5095",
//            "orderTime":"1594645095",
//            "price":111.11,
//            "orderList":
//[
//        {"foodCode":"food1234","foodCount":1},
//        {"foodCode":"food1232","foodCount":3}
//]
//    }


    @RequestMapping(value = "/form/submitOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String submitOrder(@RequestBody String jsonParam) throws Exception {
        JSONObject foodJson = JSONObject.parseObject(jsonParam);
        JSONArray jsonArray = foodJson.getJSONArray("orderList");
        String orderCode = foodJson.getString("orderCode");
        long orderTime = foodJson.getLong("orderTime");
        float price = foodJson.getFloat("price");

        List<OrderList> orderLists = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = JSONObject.parseObject(jsonArray.get(i).toString());
            OrderList orderList = new OrderList();
            orderList.setFoodCode(jsonObject.getString("foodCode"));
            orderList.setFoodCount(jsonObject.getInteger("foodCount"));
            orderList.setOrderCode(orderCode);
            orderLists.add(orderList);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("orderTime", orderTime);
        param.put("price", price);
        param.put("orderCode", orderCode);
        formService.insertOrder(param);
        formService.insertInnerOrder(orderLists);
        //返回结果
        JSONObject resultJson = new JSONObject();
        resultJson.put("flag", "1");
        resultJson.put("msg", "提交成功,请尽快付款");
        return resultJson.toString();
    }

    @RequestMapping(value = "/form/getOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getOrder(@RequestBody String jsonParam) throws Exception {
        JSONObject foodJson = JSONObject.parseObject(jsonParam);
        int status = foodJson.getInteger("status");
        List<Map<String, Object>> result1 = formService.getOrder(status);

        JSONArray result1Array = new JSONArray();
        for (int i = 0; i < result1.size(); i++) {
            JSONObject innerJson = new JSONObject();
            Map<String, Object> param1 = result1.get(i);
            String orderCode = (String) param1.get("O_orderCode");
            float price = (float) param1.get("O_price");
            long orderTime = (int) param1.get("O_orderTime");

            innerJson.put("orderCode", orderCode);
            innerJson.put("price", price);
            innerJson.put("orderTime", orderTime);
            innerJson.put("status", status);
            result1Array.add(innerJson);

            //通过orderCode获得数据
            JSONArray jsonArray2 = new JSONArray();
            List<Map<String, Object>> result2 = formService.getInnerOrder(orderCode);
            for (int j = 0; j < result2.size(); j++) {
                JSONObject innerJSON2 = new JSONObject();
                Map<String, Object> param2 = result2.get(j);
                //System.out.println("param2"+param2.toString());
                innerJSON2.put("foodCode", param2.get("I_foodCode"));
                innerJSON2.put("foodCount", param2.get("I_foodCount"));
                innerJSON2.put("foodName", param2.get("M_foodName"));
                jsonArray2.add(innerJSON2);
            }
            innerJson.put("list", jsonArray2);
        }

        //返回结果
        JSONObject resultJson = new JSONObject();
        resultJson.put("flag", "1");
        resultJson.put("msg", result1Array);
        return resultJson.toString();
    }

    @RequestMapping(value = "/form/updateOrderStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getFoodOrder(@RequestBody String jsonParam) throws Exception {
        JSONObject param = JSONObject.parseObject(jsonParam);
        String orderCode = param.getString("orderCode");
        int status = param.getInteger("status");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("orderCode",orderCode);
        paramMap.put("status",status);
        formService.updateOrderStatue(paramMap);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag","1");
        jsonObject.put("msg","更新成功");
        return jsonObject.toString();
    }


//    @RequestMapping(value = "/form/getFoodOrder", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @CrossOrigin
//    private String getFoodOrder(@RequestBody String jsonParam) throws Exception {
//        JSONObject param = JSONObject.parseObject(jsonParam);
//
//        //返回结果
//        JSONObject returnJson = new JSONObject();
//        List<Map<String,Object>> params = formService.getFoodClass();
//        returnJson.put("flag","1");
//
//        JSONArray innerJson = new JSONArray();
//
//        for(Map<String,Object> param:params){
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name",param.get("FC_className"));
//            jsonObject.put("index",param.get("FC_id"));
//            innerJson.add(jsonObject);
//        }
//        returnJson.put("len",params.size());
//        returnJson.put("msg",innerJson);
//        return returnJson.toString();
//    }

    @RequestMapping(value = "/form/imgUpdate", produces = "application/json; charset=utf-8" ,method = RequestMethod.POST)
    @ResponseBody
    public String imgUpdate(@RequestParam(value = "file") MultipartFile file) {
        JSONObject resultJSON = new JSONObject();
        if (file.isEmpty()) {
            resultJSON.put("flag","0");
            resultJSON.put("msg","图片为空");
            return resultJSON.toString();
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 文件上传后的路径
        String filePath = Attribute.dir_s;
        File dest = new File(filePath + fileName);
        //System.out.println(suffixName);
        if(!suffixName.equals(".png") && !suffixName.equals(".PNG")){
            resultJSON.put("flag","0");
            resultJSON.put("msg","图片格式错误");
            return resultJSON.toString();
        }
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //将图片存储下来
            file.transferTo(dest);
            resultJSON.put("flag","1");
            resultJSON.put("msg","上传成功");
            return resultJSON.toString();
        } catch (Exception e) {
            resultJSON.put("flag","0");
            resultJSON.put("msg","内部错误");
            return resultJSON.toString();
        }
    }

}
