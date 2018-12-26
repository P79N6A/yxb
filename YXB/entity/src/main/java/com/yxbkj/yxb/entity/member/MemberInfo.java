package com.yxbkj.yxb.entity.member;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * <p>
 * 会员信息表
 * </p>
 *
 * @author 李明
 * @since 2018-07-30
 */
@TableName("yxb_member_info")
public class MemberInfo extends Model<MemberInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;
    /**
     * 会员ID
     */
	private String memberId;
    /**
     * 会员姓名
     */
	private String memberName;
    /**
     * 会员登陆名
     */
	private String loginId;
    /**
     * 登陆密码
     */
	private String loginPwd;
    /**
     * 昵称
     */
	private String nickname;
	/**
	 * 会员毕业学校
	 */
	private String university;
	/**
	 * 邮政编码
	 */
	private String postCode;
    /**
     * 注册时间
     */
	private String registerTime;
    /**
     * 注册IP
     */
	private String registerIp;
    /**
     * 邀请码
     */
	private String memberInviteCode;
    /**
     * 来源
     */
	private String memberSource;
    /**
     * 省
     */
	private String province;
    /**
     * 市
     */
	private String city;
    /**
     * 地区
     */
	private String area;
    /**
     * 地址
     */
	private String memberAddress;
    /**
     * qq
     */
	private String memberQq;
    /**
     * 微信
     */
	private String openId;
    /**
     * 邀请人ID
     */
	private String pid;
    /**
     * 性别
     */
	private String sex;
    /**
     * 头像
     */
	private String headimg;
    /**
     * 身份证号
     */
	private String idcard;
    /**
     * 手机号
     */
	private String phone;
	private String memberEthnic;
    /**
     * 出生年月
     */
	private String memberBirth;
    /**
     * 文化程度
     */
	private String memberEdu;
    /**
     * 政治面貌
     */
	private String policitalStatus;
    /**
     * 会员类型：已认证/未认证
     */
	private String memberType;
    /**
     * 会员等级：会员/合伙人/资深合伙人
     */
	private String memberlevel;
    /**
     * 会员到期时间
     */
	private String memberLimitTime;
    /**
     * 互联网从业认证状态 已从业认证/未从业认证
     */
	private String internetAuthStatus;
    /**
     * 会员归属机构编号
     */
	private String memberOrgcode;
    /**
     * 数据有效性
     */
	private String validity;
    /**
     * 创建人ID
     */
	private String creatorId;
    /**
     * 创建人
     */
	private String creator;
    /**
     * 创建时间
     */
	private String creatorTime;
    /**
     * 创建IP地址
     */
	private String creatorIp;
    /**
     * 修改人ID
     */
	private String modifierId;
    /**
     * 修改人
     */
	private String modifier;
    /**
     * 修改时间
     */
	private String modifierTime;
    /**
     * 修改IP地址
     */
	private String modifierIp;
    /**
     * 备注
     */
	private String remark;
    /**
     * EXT1
     */
	private String ext1;
    /**
     * EXT2
     */
	private Long ext2;
    /**
     * EXT3
     */
	private BigDecimal ext3;
    /**
     * EXT4
     */
	private String ext4;
	@TableField(exist = false)
	private MemberInfo parentMemberInfo;

	private String oldUserId;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public String getMemberInviteCode() {
		return memberInviteCode;
	}

	public void setMemberInviteCode(String memberInviteCode) {
		this.memberInviteCode = memberInviteCode;
	}

	public String getMemberSource() {
		return memberSource;
	}

	public void setMemberSource(String memberSource) {
		this.memberSource = memberSource;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberQq() {
		return memberQq;
	}

	public void setMemberQq(String memberQq) {
		this.memberQq = memberQq;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMemberEthnic() {
		return memberEthnic;
	}

	public void setMemberEthnic(String memberEthnic) {
		this.memberEthnic = memberEthnic;
	}

	public String getMemberBirth() {
		return memberBirth;
	}

	public void setMemberBirth(String memberBirth) {
		this.memberBirth = memberBirth;
	}

	public String getMemberEdu() {
		return memberEdu;
	}

	public void setMemberEdu(String memberEdu) {
		this.memberEdu = memberEdu;
	}

	public String getPolicitalStatus() {
		return policitalStatus;
	}

	public void setPolicitalStatus(String policitalStatus) {
		this.policitalStatus = policitalStatus;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberlevel() {
		return memberlevel;
	}

	public void setMemberlevel(String memberlevel) {
		this.memberlevel = memberlevel;
	}

	public String getMemberLimitTime() {
		return memberLimitTime;
	}

	public void setMemberLimitTime(String memberLimitTime) {
		this.memberLimitTime = memberLimitTime;
	}

	public String getInternetAuthStatus() {
		return internetAuthStatus;
	}

	public void setInternetAuthStatus(String internetAuthStatus) {
		this.internetAuthStatus = internetAuthStatus;
	}

	public String getMemberOrgcode() {
		return memberOrgcode;
	}

	public void setMemberOrgcode(String memberOrgcode) {
		this.memberOrgcode = memberOrgcode;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorTime() {
		return creatorTime;
	}

	public void setCreatorTime(String creatorTime) {
		this.creatorTime = creatorTime;
	}

	public String getCreatorIp() {
		return creatorIp;
	}

	public void setCreatorIp(String creatorIp) {
		this.creatorIp = creatorIp;
	}

	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(String modifierTime) {
		this.modifierTime = modifierTime;
	}

	public String getModifierIp() {
		return modifierIp;
	}

	public void setModifierIp(String modifierIp) {
		this.modifierIp = modifierIp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public Long getExt2() {
		return ext2;
	}

	public void setExt2(Long ext2) {
		this.ext2 = ext2;
	}

	public BigDecimal getExt3() {
		return ext3;
	}

	public void setExt3(BigDecimal ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}


	public MemberInfo getParentMemberInfo() {
		return parentMemberInfo;
	}

	public void setParentMemberInfo(MemberInfo parentMemberInfo) {
		this.parentMemberInfo = parentMemberInfo;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
