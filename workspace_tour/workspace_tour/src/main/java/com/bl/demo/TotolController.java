package com.bl.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bl.demo.tour.TourService;
import com.bl.demo.user.UserService;
import com.bl.utli.Attribute;
import com.bl.utli.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TotolController {
    @Autowired
    UserService userService;
    @Autowired
    TourService tourService;

    @RequestMapping(value = "/User/UserAdd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserAdd(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);

        String loginName = LoginJson.getString("loginName");
        String loginPassword = LoginJson.getString("loginPassword");
        String userPhone = LoginJson.getString("userPhone");

        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("userPhone",userPhone);
        param.put("loginPassword",loginPassword);
        param.put("userCode","Usc"+PubicMethod.getAcademeCode());
        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = userService.addUser(param);
            jsonObject.put("flag","1");
            jsonObject.put("msg","注册成功");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "注册失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/Login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String Login(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);

        String loginName = LoginJson.getString("loginName");
        String loginPassword = LoginJson.getString("loginPassword");

        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("loginPassword",loginPassword);
        JSONObject jsonObject = new JSONObject();
        Map<String,Object> result = userService.login(param);
        if(null==result){
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "用户不存在");
            return jsonObject.toString();
        }
        String truePwd= (String)result.get("loginPassword");
        if(truePwd.equals(loginPassword)){
            JSONObject inner = new JSONObject();

            inner.put("userCode",result.get("userCode"));
            inner.put("loginName",result.get("loginName"));
            inner.put("userPhone",result.get("userPhone"));
            jsonObject.put("msg",inner);
            jsonObject.put("flag","1");
        }else{
            jsonObject.put("msg","密码错误");
            jsonObject.put("flag","1");
        }
        return jsonObject.toString();
    }


    @RequestMapping(value = "/User/UpdataUserInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UpdataUserInfo(@RequestBody String UpdataJSON) throws Exception {
        JSONObject UpdataInfo = JSONObject.parseObject(UpdataJSON);

        String loginName = UpdataInfo.getString("loginName");
        String userCode = UpdataInfo.getString("userCode");
        String userPhone= UpdataInfo.getString("userPhone");

        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("userCode",userCode);
        param.put("userPhone",userPhone);

        JSONObject jsonObject = new JSONObject();
        userService.updateUserInfo(param);
        jsonObject.put("flag","1");
        jsonObject.put("msg","修改成功");
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/UpdataUserPwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UpdataUserPwd(@RequestBody String UpdataJSON) throws Exception {
        JSONObject UpdataInfo = JSONObject.parseObject(UpdataJSON);

        String userCode = UpdataInfo.getString("userCode");
        String loginPassword= UpdataInfo.getString("loginPassword");

        Map<String,Object> param = new HashMap<>();
        param.put("loginPassword",loginPassword);
        param.put("userCode",userCode);

        JSONObject jsonObject = new JSONObject();
        userService.updateUserPwd(param);
        jsonObject.put("flag","1");
        jsonObject.put("msg","修改成功");
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/AddTour", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String AddTour(@RequestBody String AddJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(AddJSON);

        String tourName = LoginJson.getString("tourName");
        String posLat = LoginJson.getString("posLat");
        String Latlng = LoginJson.getString("Latlng");
        long timeAdd = System.currentTimeMillis();
        String poslng = LoginJson.getString("poslng");
        String tourCode = "tou"+PubicMethod.getAcademeCode();


        Map<String,Object> param = new HashMap<>();
        param.put("tourName",tourName);
        param.put("posLat",posLat);
        param.put("Latlng",Latlng);
        param.put("timeAdd",timeAdd);
        param.put("poslng",poslng);
        param.put("tourCode",tourCode);
        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = tourService.addTour(param);
            jsonObject.put("flag","1");
            jsonObject.put("tourCode",tourCode);
            jsonObject.put("msg","添加景点成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "添加景点失败.因为景点已存在");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/DeleteTour", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String DeleteTour(@RequestBody String deleteJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(deleteJSON);

        String tourCode = LoginJson.getString("tourCode");

        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = tourService.deleteTour(tourCode);
            jsonObject.put("flag","1");
            jsonObject.put("msg","删除景点成功");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "删除景点失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/GetTourList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String GetTourList(@RequestBody String getJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(getJSON);

        String name = getJson.getString("name");

        JSONObject jsonObject = new JSONObject();
        try
        {
            List<Map<String,Object>> result = tourService.getTourList(name);
            JSONArray jsonArray = new JSONArray();

            for(Map<String,Object> resultInner:result){
                JSONObject innerJSON = new JSONObject();
                innerJSON.put("tourName",resultInner.get("tourName"));
                innerJSON.put("tourCode",resultInner.get("tourCode"));
                innerJSON.put("posLat",resultInner.get("posLat"));
                innerJSON.put("poslng",resultInner.get("poslng"));

                JSONArray ja = new JSONArray();
                List<Map<String,Object>> k = tourService.getTourPic((String)resultInner.get("tourCode"));

                for(Map<String,Object> i:k){
                    JSONObject jo1 = new JSONObject();
                    jo1.put("picId",Attribute.pic+i.get("picId"));
                    ja.add(jo1);
                }
                innerJSON.put("pic",ja);

                jsonArray.add(innerJSON);
            }
            jsonObject.put("resultList",jsonArray);
            jsonObject.put("flag","1");
            jsonObject.put("msg","查询成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "查询失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/AddDreamList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String AddDreamList(@RequestBody String AddJSON) throws Exception {
        JSONObject AddJosn = JSONObject.parseObject(AddJSON);

        String tourCode = AddJosn.getString("tourCode");
        String userCode = AddJosn.getString("userCode");
        String dreamListCode = "DLC"+PubicMethod.getAcademeCode();
        long timeAdd = System.currentTimeMillis();

        Map<String,Object> param = new HashMap<>();
        param.put("tourCode",tourCode);
        param.put("userCode",userCode);
        param.put("timeAdd",timeAdd);
        param.put("dreamListCode",dreamListCode);

        JSONObject jsonObject = new JSONObject();
        long exist = tourService.existDreamList(param);
        if(exist==0L){
            try{
                int result = tourService.addDreamList(param);
                jsonObject.put("flag","1");
                jsonObject.put("msg","添加心愿单成功");
            }catch (Exception e)
            {
                e.printStackTrace();
                jsonObject.put("flag", "0"  );
                jsonObject.put("msg", "添加心愿单失败");
            }
        }
        else{
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "心愿单已存在");
        }

        return jsonObject.toString();
    }
    @RequestMapping(value = "/Tour/DeleteDreamList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String DeleteDreamList(@RequestBody String deleteJSON) throws Exception {
        JSONObject deleteJson = JSONObject.parseObject(deleteJSON);

        String dreamListCode = deleteJson.getString("dreamListCode");

        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = tourService.deleteDreamList(dreamListCode);
            jsonObject.put("flag","1");
            jsonObject.put("msg","删除心愿单成功");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "删除心愿单失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/GetDreamList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String GetDreamList(@RequestBody String getJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(getJSON);

        String userCode = getJson.getString("userCode");

        JSONObject jsonObject = new JSONObject();
        try
        {
            List<Map<String,Object>> result = tourService.getDreamList(userCode);
            JSONArray jsonArray = new JSONArray();

            for(Map<String,Object> resultInner:result){
                JSONObject innerJSON = new JSONObject();
                innerJSON.put("tourName",resultInner.get("tourName"));
                innerJSON.put("tourCode",resultInner.get("tourCode"));
                innerJSON.put("dreamListCode",resultInner.get("dreamListCode"));
                innerJSON.put("userCode",resultInner.get("userCode"));
                jsonArray.add(innerJSON);
            }
            jsonObject.put("resultList",jsonArray);
            jsonObject.put("flag","1");
            jsonObject.put("msg","查询成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            jsonObject.put("flag", "0"  );
            jsonObject.put("msg", "查询失败");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/addTourComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String addTourComment(@RequestBody String getJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(getJSON);

        String userCode = getJson.getString("userCode");
        String tourCode =  getJson.getString("tourCode");
        String commentText = getJson.getString("commentText");
        String commentCode = "COM"+PubicMethod.getAcademeCode();

        Map<String,Object> param = new HashMap<>();
        param.put("userCode",userCode);
        param.put("commentText",commentText);
        param.put("tourCode",tourCode);
        param.put("commentCode",commentCode);
        tourService.addTourComment(param);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag","1");
        jsonObject.put("msg","评论成功");

        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/getTourComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String getTourComment(@RequestBody String getJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(getJSON);

        String tourCode =  getJson.getString("tourCode");

        List<Map<String,Object>> result = tourService.getTourComment(tourCode);

        JSONObject jsonObject = new JSONObject();

        JSONArray inner = new JSONArray();
        for(Map<String,Object> i:result){
            JSONObject k = new JSONObject();
            k.put("tourCode",tourCode);
            k.put("commentText",i.get("commentText"));
            k.put("commentCode",i.get("commentCode"));
            k.put("loginName",i.get("loginName"));
            inner.add(k);
        }

        jsonObject.put("flag","1");
        jsonObject.put("msg",inner);

        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/getTourStatistics", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String getTourComment() throws Exception {
        List<Map<String,Object>> result = tourService.statDreamList();

        JSONObject jsonObject = new JSONObject();

        JSONArray inner = new JSONArray();
        for(Map<String,Object> i:result){
            JSONObject k = new JSONObject();
            k.put("tourCode",i.get("tourCode"));
            k.put("numCount",i.get("numCount"));
            k.put("tourName",i.get("tourName"));
            inner.add(k);

        }

        jsonObject.put("flag","1");
        jsonObject.put("msg",inner);

        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/getUserList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String getUserList() throws Exception {
        List<Map<String,Object>> result = userService.getUserList();

        JSONObject jsonObject = new JSONObject();

        JSONArray inner = new JSONArray();
        for(Map<String,Object> i:result){
            JSONObject k = new JSONObject();
            k.put("loginName",i.get("loginName"));
            k.put("loginPassword",i.get("loginPassword"));
            k.put("userCode",i.get("userCode"));
            k.put("userPhone",i.get("userPhone"));
            inner.add(k);
        }

        jsonObject.put("flag","1");
        jsonObject.put("msg",inner);

        return jsonObject.toString();
    }

    @RequestMapping(value = "/Tour/imgUpdate", produces = "application/json; charset=utf-8" ,method = RequestMethod.POST)
    @ResponseBody
    public String imgUpdate(@RequestParam(value = "file") MultipartFile file,@RequestParam("tourCode") String tourCode) {
        JSONObject resultJSON = new JSONObject();
        if (file.isEmpty()) {
            resultJSON.put("flag","0");
            resultJSON.put("msg","图片为空");
            return resultJSON.toString();
        }
        // 文件名由系统上生成
        String originFileName =  file.getOriginalFilename();

        // 获取文件的后缀名
        String suffixName = originFileName.substring(originFileName.lastIndexOf("."));

        String picId = "PIC"+PubicMethod.getAcademeCode()+suffixName;

        Map<String,Object> param = new HashMap<>();
        param.put("picId",picId);
        param.put("tourCode",tourCode);
        // 文件上传后的路径
        String filePath = Attribute.dir_n;
        File dest = new File(filePath + picId);

        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //将图片存储下来
            file.transferTo(dest);

            tourService.addTourPic(param);

            resultJSON.put("flag","1");
            resultJSON.put("msg","上传成功");
            return resultJSON.toString();
        } catch (Exception e) {
            resultJSON.put("flag","0");
            resultJSON.put("msg","内部错误");
            return resultJSON.toString();
        }
    }
    @RequestMapping(value = "/Tour/getPic", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @CrossOrigin
    @ResponseBody
    private byte[] getPic(HttpServletRequest request) throws Exception {
        String picId = request.getParameter("picId");
        String dir = Attribute.dir_n;
        File file = new File(dir + picId);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] pic_bytes = new byte[fileInputStream.available()];
        fileInputStream.read(pic_bytes, 0, fileInputStream.available());
        return pic_bytes;
    }
}
