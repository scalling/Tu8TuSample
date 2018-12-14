package com.zm.tu8tu.sample.mvp.model.api.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/5/4
 * @description : 描述...
 */
public class NewCaseDto implements Serializable{
    private List<SceneBean> scene;
    private List<BoxBean> box;
    private List<BoxReplacer> subbox;

    public List<SceneBean> getScene() {
        return scene;
    }

    public void setScene(List<SceneBean> scene) {
        this.scene = scene;
    }

    public List<BoxBean> getBox() {
        return box;
    }

    public void setBox(List<BoxBean> box) {
        this.box = box;
    }

    public List<BoxReplacer> getSubbox() {
        return subbox;
    }

    public void setSubbox(List<BoxReplacer> subbox) {
        this.subbox = subbox;
    }

    public class SceneBean {
        /**
         * x : 410
         * y : 220
         */

        private String x;
        private String y;
    }

    public class BoxBean {
        /**
         * name : 地面
         * type : 1
         * level : 0
         * angle : 6
         * scale : 1.00
         * wscale : 1.00
         * hscale : 1.00
         * position : 3
         * canbuy : 1
         * box_img : vm/98/3b/cf/983bcfdc779dc9c792ee15a87797efc8.png
         * subbox_count : 2
         * model_img : vm/98/3b/cf/983bcfdc779dc9c792ee15a87797efc8.png
         * model_id : 1145
         * point_x : 0
         * point_y : 0
         * subbox : [{"id":"13484","name":"地砖","direction":"0","icon":"front_end/icon/component_logo_9.png"},{"id":"13485","name":"地板","direction":"0","icon":"front_end/icon/component_logo_5.png"}]
         * box_id : 23182
         * hasproduct : 0
         */

        private String name;
        private String type;
        private String level;
        private String angle;
        private float scale;
        private float wscale;
        private float hscale;
        private int position;
        private String canbuy;
        private String box_img;
        private int subbox_count;
        private String model_img;
        private String model_id;
        private float point_x;
        private float point_y;
        private String box_id;
        private String hasproduct;
        private List<SubboxBean> subbox;
        private String select_img;
        private String select_color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getAngle() {
            return angle;
        }

        public void setAngle(String angle) {
            this.angle = angle;
        }

        public float getScale() {
            return scale;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        public float getWscale() {
            return wscale;
        }

        public void setWscale(float wscale) {
            this.wscale = wscale;
        }

        public float getHscale() {
            return hscale;
        }

        public void setHscale(float hscale) {
            this.hscale = hscale;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getCanbuy() {
            return canbuy;
        }

        public void setCanbuy(String canbuy) {
            this.canbuy = canbuy;
        }

        public String getBox_img() {
            return box_img;
        }

        public void setBox_img(String box_img) {
            this.box_img = box_img;
        }

        public int getSubbox_count() {
            return subbox_count;
        }

        public void setSubbox_count(int subbox_count) {
            this.subbox_count = subbox_count;
        }

        public String getModel_img() {
            return model_img;
        }

        public void setModel_img(String model_img) {
            this.model_img = model_img;
        }

        public String getModel_id() {
            return model_id;
        }

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public float getPoint_x() {
            return point_x;
        }

        public void setPoint_x(float point_x) {
            this.point_x = point_x;
        }

        public float getPoint_y() {
            return point_y;
        }

        public void setPoint_y(float point_y) {
            this.point_y = point_y;
        }

        public String getBox_id() {
            return box_id;
        }

        public void setBox_id(String box_id) {
            this.box_id = box_id;
        }

        public String getHasproduct() {
            return hasproduct;
        }

        public void setHasproduct(String hasproduct) {
            this.hasproduct = hasproduct;
        }

        public List<SubboxBean> getSubbox() {
            return subbox;
        }

        public void setSubbox(List<SubboxBean> subbox) {
            this.subbox = subbox;
        }

        public String getSelect_img() {
            return select_img;
        }

        public void setSelect_img(String select_img) {
            this.select_img = select_img;
        }

        public String getSelect_color() {
            return select_color;
        }

        public void setSelect_color(String select_color) {
            this.select_color = select_color;
        }

        public  class SubboxBean {
            /**
             * id : 13484
             * name : 地砖
             * direction : 0
             * icon : front_end/icon/component_logo_9.png
             */

            private int id;
            private String name;
            private String direction;
            private String icon;
            private String level;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }
        }

      


    }

    public class BoxReplacer {

        private int id;
        private String box_name;
        private int factory_counts;
        private List<FactorysBean> factorys;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBox_name() {
            return box_name;
        }

        public void setBox_name(String box_name) {
            this.box_name = box_name;
        }

        public int getFactory_counts() {
            return factory_counts;
        }

        public void setFactory_counts(int factory_counts) {
            this.factory_counts = factory_counts;
        }

        public List<FactorysBean> getFactorys() {
            return factorys;
        }

        public void setFactorys(List<FactorysBean> factorys) {
            this.factorys = factorys;
        }

        public class FactorysBean {


            private String factory_name;
            private List<ItemsBean> items;

            public String getFactory_name() {
                return factory_name;
            }

            public void setFactory_name(String factory_name) {
                this.factory_name = factory_name;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public   class ItemsBean {
                /**
                 * box_id : 23182
                 * button_img : vm/68/e4/3a/68e43aad22b001f3f6b0cb517f224d1c.png
                 * img : vm/a8/bd/ca/a8bdca3e07036038ec505162864e7369.png
                 * hasproduct : 0
                 * model_id : 3516
                 */

                private String box_id;
                private String button_img;
                private String img;
                private String hasproduct;
                private String model_id;

                public String getBox_id() {
                    return box_id;
                }

                public void setBox_id(String box_id) {
                    this.box_id = box_id;
                }

                public String getButton_img() {
                    return button_img;
                }

                public void setButton_img(String button_img) {
                    this.button_img = button_img;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getHasproduct() {
                    return hasproduct;
                }

                public void setHasproduct(String hasproduct) {
                    this.hasproduct = hasproduct;
                }

                public String getModel_id() {
                    return model_id;
                }

                public void setModel_id(String model_id) {
                    this.model_id = model_id;
                }
            }
        }
    }
}
