package com.atguigu.common.constant;

/**
 * @program: gulimall
 * @description:
 * @author: LZ
 * @create: 2022-10-23 00:01
 **/
public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性");

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }


        public String getMsg() {
            return msg;
        }


    }


    public enum ProductStatusEnum {
        NEW_SPU(0, "新建"),
        SPU_UP(1, "商品上架"),
        SPU_DOWN(2, "商品下架"),
        ;

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductStatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }
}
