package com.cnrmall.springcloud.controller;

import com.cnrmall.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    @Autowired
    private IMessageProvider messageProviderImpl;

    @Autowired
    private IMessageProvider streamSourceSender;

    @RequestMapping("/sendMsg")
    public void SendMessage(){
        messageProviderImpl.send();
    }


    @RequestMapping("/sendMsg/{msg}")
    public void SendMessage(@PathVariable String msg){
        streamSourceSender.SendMsg(msg);
    }

}