package com.practice.shoppingcart.bean;

import com.practice.shoppingcart.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个玩具商品的名称数组
    private static String[] mNameArray = {
            "精灵俱乐部", "机器人发明家", "芝麻街", "中央广场", "TIE 战斗机飞行员头盔", "尤达宝宝 (The Child)","哈利·波特圣诞倒数日历","1989 蝙蝠翼"
    };
    // 声明一个玩具商品的描述数组
    private static String[] mDescArray = {
            "乐高® 精灵俱乐部 (10275)",
            "乐高® MINDSTORMS® 机器人发明家 (51515)",
            "乐高® IDEAS® 芝麻街 (21324)",
            "乐高® 城市组中央广场 (60271) ",
            "乐高® TIE 战斗机飞行员头盔 (75274) ",
            "乐高® 星球大战尤达宝宝 (75318)",
            "乐高® 哈利·波特降临节日历 (75981)",
            "乐高® DC 蝙蝠侠1989年蝙蝠翼 (76161)"
    };
    // 声明一个玩具商品的价格数组
    private static float[] mPriceArray = {799, 458, 898, 2899, 1398, 678,519,2899};
    // 声明一个玩具商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.jljlb_s, R.drawable.jqr_s, R.drawable.zmj_s,
            R.drawable.zygc_s, R.drawable.tie_s, R.drawable.thechild_s,
            R.drawable.harry_s,R.drawable.bat_s
    };
    // 声明一个玩具商品的大图数组
    private static int[] mPicArray = {
            R.drawable.jljlb, R.drawable.jqr, R.drawable.zmj,
            R.drawable.zygc, R.drawable.tie, R.drawable.thechild,
            R.drawable.harry,R.drawable.bat
    };

    // 获取默认的玩具信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}
