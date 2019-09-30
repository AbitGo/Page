package com.bl.demo;

import com.alibaba.fastjson.JSONObject;
import com.bl.DemoApplication;
import com.bl.demo.pojo.User;
import com.bl.demo.user.MailService;
import com.bl.demo.user.UserService;
import com.bl.utli.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TotolController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/User/UserAdd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserLogin(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);
        //��ȡ��¼�����¼����
        String loginName = LoginJson.getString("loginName");
        String loginPwd = LoginJson.getString("loginPwd");
        String userName = LoginJson.getString("userName");
        String userCode = "Usr"+PubicMethod.getAcademeCode();
        String userEmail = LoginJson.getString("userEmail");
        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("loginPwd",loginPwd);
        param.put("userName",userName);
        param.put("userCode",userCode);
        param.put("userEmail",userEmail);
        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = userService.addUser(param);
            jsonObject.put("flag",1);
            jsonObject.put("msg","ע��ɹ�");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "ע��ʧ��,�û����Ѵ���");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/UserToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserToken(@RequestBody String TokenJSON) throws Exception {
        JSONObject tokenJSON = JSONObject.parseObject(TokenJSON);
        //��ȡ��¼�����¼����
        String loginName = tokenJSON.getString("loginName");

        String email = userService.searchEmail(loginName);

        JSONObject jsonObject = new JSONObject();
        if(email==null){
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "��½��������");
            return jsonObject.toString();
        }
        Map<String,Object> param = new HashMap<>();

        String token = "Tkn"+PubicMethod.getAcademeCode();
        param.put("loginName",loginName);
        param.put("token",token);

        try{
            mailService.sendSimpleMail("15695203200@163.com",email,"�޸������ʼ�","�޸������ʼ�",token);
        }catch (Exception e){
            e.printStackTrace();
        }
        int result = userService.addTkn(param);

        if(result!=0){
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "��֤���Ѿ����������䣬�����");
            jsonObject.put("token",token);
        }else{
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "����ʧ�ܣ�����֤�����Ƿ���ȷ");
            jsonObject.put("token",token);
        }
        return jsonObject.toString();
    }

}
