package jane.mall.classification;

import java.util.List;

import jane.mall.base.BaseEntity;

/**
 * @author Jane
 *         email 1024797370@qq.com
 *         date 2016/5/4 15:22
 *         description
 *         vsersion
 */
public class AllCategoryEntity extends BaseEntity {


    private List<CategoryLevel1Entity> returnObject;

    @Override
    public String toString() {
        return super.toString() + "     AllCategoryEntity{" +
                "returnObject=" + returnObject +
                '}';
    }

    public List<CategoryLevel1Entity> getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(List<CategoryLevel1Entity> returnObject) {
        this.returnObject = returnObject;
    }

    /**
     * 一级目录
     */
    public static class CategoryLevel1Entity extends BaseCategoryEntity {

        public CategoryLevel1Entity() {
            mType = TYPE_LEVEL_1;
        }

        private CategoryAdvListEntity categoryAdvListEntity;
        private List<CategoryLevel2Entity> subCategoryList;

        public CategoryAdvListEntity getCategoryAdvListEntity() {
            return categoryAdvListEntity;
        }

        public void setCategoryAdvListEntity(CategoryAdvListEntity categoryAdvListEntity) {
            this.categoryAdvListEntity = categoryAdvListEntity;
        }

        public List<CategoryLevel2Entity> getSubCategoryList() {
            return subCategoryList;
        }

        public void setSubCategoryList(List<CategoryLevel2Entity> subCategoryList) {
            this.subCategoryList = subCategoryList;
        }

        @Override
        public String toString() {
            return "CategoryLevel1Entity{" +
                    "categoryAdvListEntity=" + categoryAdvListEntity +
                    ", subCategoryList=" + subCategoryList +
                    '}';
        }
    }

    /**
     * 二级目录
     */
    public static class CategoryLevel2Entity extends BaseSubCategoryEntity {

        public CategoryLevel2Entity() {
            mType = TYPE_LEVEL_2;
        }

        private List<CategoryLevel3Entity> subCategoryList;

        public List<CategoryLevel3Entity> getSubCategoryList() {
            return subCategoryList;
        }

        public void setSubCategoryList(List<CategoryLevel3Entity> subCategoryList) {
            this.subCategoryList = subCategoryList;
        }

        @Override
        public String toString() {
            return "CategoryLevel2Entity{" +
                    "subCategoryList=" + subCategoryList +
                    '}';
        }
    }


    public static class CategoryLevel3Entity extends BaseSubCategoryEntity {

        private String categoryImg;

        public CategoryLevel3Entity() {
            mType = TYPE_LEVEL_3;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }


    /**
     * 子类共性抽取
     */
    public static class BaseCategoryEntity {

        private int categoryId;
        private String categoryName;

        public static final int TYPE_LEVEL_1 = 0;
        public static final int TYPE_LEVEL_2 = 1;
        public static final int TYPE_LEVEL_3 = 2;

        protected int mType;

        private String categoryLevel3Code;
        private String categoryLevel2Code;

        public String getCategoryLevel2Code() {
            return categoryLevel2Code;
        }

        public void setCategoryLevel2Code(String categoryLevel2Code) {
            this.categoryLevel2Code = categoryLevel2Code;
        }

        public int getType() {
            return mType;
        }

        public String getCategoryLevel3Code() {
            return categoryLevel3Code;
        }

        public void setCategoryLevel3Code(String categoryLevel3Code) {
            this.categoryLevel3Code = categoryLevel3Code;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        @Override
        public String toString() {
            return "BaseCategoryEntity{" +
                    "categoryId=" + categoryId +
                    ", categoryName='" + categoryName + '\'' +
                    '}';
        }
    }


    public static class BaseSubCategoryEntity extends BaseCategoryEntity {
        private String categoryCode;

        public String getCategoryCode() {
            return categoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            this.categoryCode = categoryCode;
        }

        @Override
        public String toString() {
            return "BaseSubCategoryEntity{" +
                    "categoryCode='" + categoryCode + '\'' +
                    '}';
        }
    }


    /**
     * 广告对象
     */
    public static class CategoryAdvListEntity {

    }

}
