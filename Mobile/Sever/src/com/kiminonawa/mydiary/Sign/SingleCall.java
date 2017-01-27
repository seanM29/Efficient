package com.kiminonawa.mydiary.Sign;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

import java.util.ArrayList;

/**
 * Created by sean on 2016/11/30.
 */
class SingleCall {

    private String name;
    private String time;
    private ArrayList<String> CallContetn = new ArrayList<String>();
    private String location;


    public SingleCall(String n, ArrayList<String> p){
        name = n;
        time = p.get(0);
        location = p.get(1);
        CallContetn = p;
    }


    public void execute(){
        String url = "http://gw.api.taobao.com/router/rest";
        TaobaoClient client = new DefaultTaobaoClient(url, "23551557",
                "2c5cf8ad9e37eefd141ab9ba3ee1f253");
        AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
        req.setExtend( "" );
        String statement = "{name:'"+name+"',a:'"+time+"',b:'"+location+"'}";
        req.setTtsParamString( statement);

        req.setCalledShowNum( "01053912804" );
        req.setTtsCode( "TTS_31805033" );
        for(int i=2;i<CallContetn.size();i++) {
            req.setCalledNum(CallContetn.get(i));
            AlibabaAliqinFcTtsNumSinglecallResponse rsp = null;
            try {
                rsp = client.execute(req);
            } catch (ApiException e) {
                e.printStackTrace();
            }
            System.out.println(rsp.getBody());
        }
    }
}
