package com.hr.videosplayertv.common;

public class ImmobilizationData {
    public static final String HOMEPAGE = "首页";
    public static final String CLASSIFY = "分类";
    public static final String FILM = "电影";
    public static final String TELEPLAY = "电视剧";
    public static final String VARIETY = "综艺";
    public static final String ANIME = "动漫";
    public static final String SPORTS = "体育";
    public static final String OVERSEAS = "华人圈";

    public enum Tags{
        HOMEPAGE(ImmobilizationData.HOMEPAGE,0),
        CLASSIFY(ImmobilizationData.CLASSIFY,1),
        FILM(ImmobilizationData.FILM,2),
        TELEPLAY(ImmobilizationData.TELEPLAY,3),
        VARIETY(ImmobilizationData.VARIETY,4),
        ANIME(ImmobilizationData.ANIME,5),
        SPORTS(ImmobilizationData.SPORTS,6),
        OVERSEAS(ImmobilizationData.OVERSEAS,7);

        private String name;
        private int index;
        // 构造方法：传递所有的属性
        private Tags(String name, int index) {
            this.name = name;
            this.index = index;
        }

        // 1.通过编号得到名字
        public static String getNameByIndex(int index) {
            for (Tags c : Tags.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // 2.通过编号得到枚举
        public static Tags getColorByIndex(int index) {
            for (Tags c : Tags.values()) {
                if (c.getIndex() == index) {
                    return c;
                }
            }
            return null;
        }
        // 3.通过名字得到编号
        public static int getIndexByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName() == name) {
                    return c.index;
                }
            }
            return -1;
        }
        // 通过名字得到枚举
        public static Tags getColorByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName() == name) {
                    return c;
                }
            }
            return null;
        }
        //得到枚举的名字
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        //得到枚举的编号
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
    }


}
