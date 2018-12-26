package com.yxbkj.yxb.entity.vo;
import io.swagger.annotations.ApiModelProperty;
public class ZrpxParam {
    @ApiModelProperty(value = "token",required = true)
    private String token;
    @ApiModelProperty(value = "姓名",required = true)
    private String userName;
    @ApiModelProperty(value = "性别。0值表示女性，非0值表示男性(建议用值1)。",required = true)
    private String userSex;
    @ApiModelProperty(value = "民族(传入对应码表值)。以下任一值（竖划线分割）：汉族|蒙古族|回族|藏族|朝鲜族|维吾尔族|苗族|壮族|满族|布依族|侗族|彝族|瑶族|白族|土家族|哈尼族|哈萨克族|傣族|黎族|傈僳族|佤族|高山族|畲族|拉祜族|水族|东乡族|纳西族|景颇族|柯尔克孜族|土族|达斡尔族|仫佬族|羌族|布朗族|撒拉族|毛南族|仡佬族|锡伯族|阿昌族|普米族|塔吉克族|怒族|乌孜别克族|京族|独龙族|德昂族保安族|鄂温克族|俄罗斯族|塔塔尔族|裕固族|鄂伦春族|赫哲族|门巴族|珞巴族|基诺族",required = true)
    private String userNation;
    @ApiModelProperty(value = "证件类型(传入对应码表值)。以下任一值（竖划线分割）:身份证|军官证|士兵证|警官证|护照|港澳通行证|台胞证|香港身份证",required = true)
    private String userIdType;
    @ApiModelProperty(value = "身份证件号码",required = true)
    private String userIdCode;
    @ApiModelProperty(value = "文化程度(传入对应码表值)。以下任一值（竖划线分割）：博士|硕士|本科|大专|高中及同等学历|初中及同等学历|初中及以下学历" ,required = true)
    private String educationalLeveL;
    @ApiModelProperty(value = "政治面貌(传入对应码表值)。以下任一值（竖划线分割）：群众|中共党员|中共预备党员|共青团员|民革会员|民盟盟员|民建会员|民进会员|农工党党员|致公党党员|九三学社社员|台盟盟员|无党派人士",required = true)
    private String political;
    @ApiModelProperty(value = "出生年月",required = true)
    private String userBirthday;
    @ApiModelProperty(value = "移动电话",required = true)
    private String userPhone;
    @ApiModelProperty(value = "现住址",required = true)
    private String address;
    @ApiModelProperty(value = "毕业学校",required = false)
    private String university;
    @ApiModelProperty(value = "邮政编码",required = false)
    private String postcode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserNation() {
        return userNation;
    }

    public void setUserNation(String userNation) {
        this.userNation = userNation;
    }

    public String getUserIdType() {
        return userIdType;
    }

    public void setUserIdType(String userIdType) {
        this.userIdType = userIdType;
    }

    public String getUserIdCode() {
        return userIdCode;
    }

    public void setUserIdCode(String userIdCode) {
        this.userIdCode = userIdCode;
    }

    public String getEducationalLeveL() {
        return educationalLeveL;
    }

    public void setEducationalLeveL(String educationalLeveL) {
        this.educationalLeveL = educationalLeveL;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
