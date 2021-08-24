package com.community.enums;

public enum TagEnum {
    REPLY_QUESTION("life","生活"),
    PRAISE_QUESTION("tech","点赞了问题"),
    FOLLOW_USER("people","人文");


    private String tag;
    private String tagString;

    TagEnum(String tag, String tagString) {
        this.tag = tag;
        this.tagString = tagString;
    }

    public String getTag() {
        return tag;
    }

    public String getTagString() {
        return tagString;
    }

    public static String getTagString(String tag){
        TagEnum[] tagEnums = TagEnum.values();
        String result = "";
        for(TagEnum tagEnum : tagEnums){
            if(tagEnum.equals(tag)){
                 result = tagEnum.tagString;
            }
        }
        return result;
    }
}
