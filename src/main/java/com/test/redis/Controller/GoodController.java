package com.test.redis.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodController
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/buy_goods")
    public String buy_Goods()
    {
        String result = stringRedisTemplate.opsForValue().get("goods:001");
        int goodsNumber = result == null ? 0 : Integer.parseInt(result);

        if(goodsNumber > 0)
        {
            int realNumber = goodsNumber - 1;
            stringRedisTemplate.opsForValue().set("goods:001",realNumber + "");
            System.out.println("你已经成功秒杀商品，此时还剩余：" + realNumber + "件"+"\t 服务器端口："+serverPort);
            return "你已经成功秒杀商品，此时还剩余：" + realNumber + "件"+"\t 服务器端口："+serverPort;
        }else{
            System.out.println("商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口："+serverPort);
        }

        return "商品已经售罄/活动结束/调用超时，欢迎下次光临"+"\t 服务器端口："+serverPort;
    }
}


